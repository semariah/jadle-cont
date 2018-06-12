package dao;

import models.Foodtype;
import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

public class Sql2oRestaurantDaoTest {
    private Connection conn;
    private Sql2oRestaurantDao restaurantDao;
    private Sql2oFoodtypeDao foodtypeDao;

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
        conn.close();
    }

    @Test
    public void getAll() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        assertEquals(1, restaurantDao.getAll().size());
    }

    @Test
    public void RestaurantReturnsFoodtypesCorrectly() throws Exception {
        Foodtype foodtype = new Foodtype("Seafood");
        foodtypeDao.add(foodtype);

        Foodtype otherFoodtype = new Foodtype("Bar Food");
        foodtypeDao.add(otherFoodtype);

        Restaurant restaurant = setupNewRestaurant();
        restaurantDao.add(restaurant);
        restaurantDao.addRestaurantToFoodtype(restaurant, foodtype);
        restaurantDao.addRestaurantToFoodtype(restaurant, otherFoodtype);

        Foodtype[] foodtypes = {foodtype, otherFoodtype};
        assertEquals(Arrays.asList(foodtypes), restaurantDao.getAllFoodtypesByRestaurant(restaurant.getId()));
    }

    @Test
    public void deleteingRestaurantAlsoUpdatesJoinTable() throws Exception{
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

    public Restaurant setupNewRestaurant() {
        Restaurant restaurant = new Restaurant("sems", "1234 lane", "97230", "503-333-3333", "http://steakhouse.com", "semaria.eyzac@gmail.com");
        restaurantDao.add(restaurant); //Why do we add restaurant to the dao twice?
        return restaurant;
    }

    public Restaurant setupAltRestaurant() {
        Restaurant restaurant = new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874");
        restaurantDao.add(restaurant);
        return restaurant;
    }
}