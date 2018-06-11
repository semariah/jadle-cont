package dao;

import models.Restaurant;
import models.Review;
import org.junit.After;
import org.junit.Before;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.junit.Test;
import static org.junit.Assert.*;


public class Sql2oReviewDaoTest {
    private Connection conn;
    private Sql2oReviewDao reviewDao;
    private Sql2oRestaurantDao restaurantDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o (connectionString, "", "");
        reviewDao = new Sql2oReviewDao(sql2o);
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        conn = sql2o.open();


    }

    @After
    public void tearDown() throws Exception {
        conn.close();

    }

    @Test
    public void addingReviewSetsId() throws Exception{
        Review review = setupReview();
        assertEquals(1, review.getId());
    }

    @Test
    public void getAll_getsAllReviews() throws Exception{
        Review review1 = setupReview();
        Review review2 = setupReview();
        assertEquals(2, reviewDao.getAll().size());
    }

    @Test
    public void getAllReviewsByRestaurant_getsAllReviewsByRestaurant() throws Exception{
        Restaurant restaurant = setupRestaurant();
        Restaurant testRestaurant = setupRestaurant();
        Review review1 = setupReviewForRestaurant(restaurant);
        Review review2 = setupReviewForRestaurant(restaurant);
        Review review3 = setupReviewForRestaurant(testRestaurant);
        assertEquals(2, reviewDao.getAllReviewsByRestaurant(restaurant.getId()).size());
    }

    @Test
    public void deleteById_deletesByCorrectId() throws Exception{
        Review review = setupReview();
        Review testReview = setupReview();
        assertEquals(2, reviewDao.getAll().size());
        reviewDao.deleteById(review.getId());
        assertEquals(1, reviewDao.getAll().size());
    }

    @Test
    public void clearAll_clearsAllReviews() throws Exception{
        Review review = setupReview();
        Review testReview = setupReview();
        reviewDao.clearAll();
        assertEquals(0, reviewDao.getAll().size());
    }

    private Review setupReview() {
        Review review = new Review("great", "Kim", 4, 555);
        reviewDao.add(review);
        return review;
    }

    public Review setupReviewForRestaurant(Restaurant restaurant) {
        Review review = new Review("great", "Kim", 4, restaurant.getId());
        reviewDao.add(review);
        return review;
    }

    public Restaurant setupRestaurant() {
        Restaurant restaurant = new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        restaurantDao.add(restaurant);
        return restaurant;
    }

}
