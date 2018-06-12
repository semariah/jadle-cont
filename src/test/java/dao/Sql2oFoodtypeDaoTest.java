package dao;

import models.Foodtype;
import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;

import org.sql2o.Connection;

import static org.junit.Assert.*;

public class Sql2oFoodtypeDaoTest {

    private Sql2oFoodtypeDao foodtypeDao;
    private Sql2oRestaurantDao restaurantDao;
    private Connection conn;
    
    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        conn = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {

    }

//    @Test
//    public void findById() {
//        Foodtype foodtype = setupNewFoodtype();
//
//        assertEquals();
//    }

    @Test
    public void addFoodTypeToRestaurantAddsTypeCorrectly() {
        Restaurant restaurant = setupNewRestaurant();
        Restaurant altRestaurant = setupAltRestaurant();

        restaurantDao.add(restaurant);
        restaurantDao.add(altRestaurant);

        Foodtype foodtype = setupNewFoodtype();

        foodtypeDao.add(foodtype);

        foodtypeDao.addFoodtypeToRestaurant(foodtype, restaurant);
        foodtypeDao.addFoodtypeToRestaurant(foodtype, altRestaurant);

        assertEquals(2, foodtypeDao.getAllRestaurantsForAFoodtype(foodtype.getId()).size());
    }

    @Test
    public void deleteingRestaurantAlsoUpdatesJoinTable() throws Exception {  //Why do we delete restaurant again?
        Foodtype foodtype  = new Foodtype("Seafood");
        foodtypeDao.add(foodtype);

        Restaurant restaurant = setupNewRestaurant();
        restaurantDao.add(restaurant);

        Restaurant altRestaurant = setupAltRestaurant();
        restaurantDao.add(altRestaurant);

        restaurantDao.addRestaurantToFoodtype(restaurant,foodtype);
        restaurantDao.addRestaurantToFoodtype(altRestaurant, foodtype);

        restaurantDao.deleteById(restaurant.getId());
        assertEquals(0, restaurantDao.getAllFoodtypesByRestaurant(restaurant.getId()).size());
    }

    public Foodtype setupNewFoodtype() {
        return new Foodtype ("fish");
    }

    public Restaurant setupAltRestaurant() {
        return new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874");
    }

    public Restaurant setupNewRestaurant() {
        return new Restaurant("sems", "1234 lane", "97230", "503-333-3333", "http://steakhouse.com", "semaria.eyzac@gmail.com");
    }



}
