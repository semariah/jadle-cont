import com.google.gson.Gson;
import dao.Sql2oFoodtypeDao;
import dao.Sql2oRestaurantDao;
import dao.Sql2oReviewDao;
import models.Foodtype;
import models.Restaurant;
import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static spark.Spark.*;
import exceptions.ApiException;

import java.util.List;


public class App {

    public static void main(String[] args) {
        Sql2oFoodtypeDao foodtypeDao;
        Sql2oRestaurantDao restaurantDao;
        Sql2oReviewDao reviewDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();


        post("/restaurants/new", "application/json", (req, res) -> {
            Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);
            restaurantDao.add(restaurant);
            res.status(201);
            res.type("application/json");
            return gson.toJson(restaurant);
        });

        get("/restaurants", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            return gson.toJson(restaurantDao.getAll());//send it back to be displayed
        });

        get("/restaurants/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int restaurantId = Integer.parseInt(req.params("id"));
            res.type("application/json");
            return gson.toJson(restaurantDao.findById(restaurantId));
        });

        post("/foodtypes/new", "application/json", (req, res) -> {
            Foodtype foodtype = gson.fromJson(req.body(), Foodtype.class);
            foodtypeDao.add(foodtype);
            res.status(201);
            res.type("application/json");
            return gson.toJson(foodtype);
        });

        get("/foodtypes", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            return gson.toJson(foodtypeDao.getAll());//send it back to be displayed
        });

        //posting info but error of ApiException = I sorted.

        post("/restaurants/:restaurantId/foodtype/:foodtypeId", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("restaurantId"));
            int foodtypeId = Integer.parseInt(req.params("foodtypeId"));
            Restaurant restaurant = restaurantDao.findById(restaurantId);
            Foodtype foodtype = foodtypeDao.findById(foodtypeId);
            if (restaurant != null && foodtype != null){
                //both exist and can be associated - we should probably not connect things that are not here.
                foodtypeDao.addFoodtypeToRestaurant(foodtype, restaurant);
                res.status(201);
                return gson.toJson(String.format("Restaurant '%s' and Foodtype '%s' have been associated",foodtype.getName(), restaurant.getName()));
            }
            else {
                throw new ApiException(404, String.format("Restaurant or Foodtype does not exist"));
            }
        });

    // done just type it on post man with out the Reviews word, write the contents of the reviews object contents as in cheatsheet
        post("/restaurants/:restaurantId/reviews/new", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("restaurantId"));
            Review review = gson.fromJson(req.body(), Review.class);
            review.setRestaurantId(restaurantId); //we need to set this separately because it comes from our route, not our JSON input.
            reviewDao.add(review);
            res.status(201);
            return gson.toJson(review);
        });



        get("/foodtypes/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int foodtypeId = Integer.parseInt(req.params("id"));
            res.type("application/json");
            return gson.toJson(foodtypeDao.findById(foodtypeId));
        });

    //done!
        get("/restaurants/:id/reviews", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("id"));
            Restaurant restaurantToFind = restaurantDao.findById(restaurantId);
            List<Review> allReviews;
            if (restaurantToFind == null){
                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
            }
            allReviews = reviewDao.getAllReviewsByRestaurant(restaurantId);
            return gson.toJson(allReviews);
            });


        get("/restaurants/:id/foodtypes", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("id"));
            Restaurant restaurantToFind = restaurantDao.findById(restaurantId);
            if (restaurantToFind == null){
                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
            }
            else if (restaurantDao.getAllFoodtypesByRestaurant(restaurantId).size()==0){
                return "{\"message\":\"I'm sorry, but no foodtypes are listed for this restaurant.\"}";
            }
            else {
                return gson.toJson(restaurantDao.getAllFoodtypesByRestaurant(restaurantId));
            }
        });


        get("/foodtypes/:id/restaurants", "application/json", (req, res) -> {
            int foodtypeId = Integer.parseInt(req.params("id"));
            Foodtype foodtypeToFind = foodtypeDao.findById(foodtypeId);
            if (foodtypeToFind == null){
                throw new ApiException(404, String.format("No foodtype with the id: \"%s\" exists", req.params("id")));
            }
            else if (foodtypeDao.getAllRestaurantsForAFoodtype(foodtypeId).size()==0){
                return "{\"message\":\"I'm sorry, but no restaurants are listed for this foodtype.\"}";
            }
            else {
                return gson.toJson(foodtypeDao.getAllRestaurantsForAFoodtype(foodtypeId));
            }
        });



        after((req, res) ->{
            res.type("application/json");
        });
    }

}
