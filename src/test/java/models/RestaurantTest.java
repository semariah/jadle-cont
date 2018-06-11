package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RestaurantTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName_returnsCorrectName() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        assertEquals("sems", restaurant.getName());
    }

    @Test
    public void getAddress_returnsCorrectAddress() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        assertEquals("1234 lane", restaurant.getAddress());
    }

    @Test
    public void getZipcode_returnsCorrectZipcode() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        assertEquals("97230", restaurant.getZipcode());
    }

    @Test
    public void getPhone_returnsCorrectPhone() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        assertEquals("503-333-3333", restaurant.getPhone());
    }

    @Test
    public void getWebsite_returnsCorrectWebsite() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        assertEquals("no website listed", restaurant.getWebsite());
    }

    @Test
    public void getEmail_returnsCorrectEmail() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        assertEquals("no email available", restaurant.getEmail());
    }

    @Test
    public void setName_setsCorrectName() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        restaurant.setName("Steak house");
        assertNotEquals("no email available", restaurant.getName());
    }


    @Test
    public void setAddress_setsCorrectAddress() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        restaurant.setAddress("15715 beech st");
        assertNotEquals("1234 lane", restaurant.getAddress());
    }


    @Test
    public void setZipcode_setsCorrectZipcode() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        restaurant.setZipcode("97845");
        assertNotEquals("97230", restaurant.getZipcode());
    }


    @Test
    public void setPhone_setsCorrectphone() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        restaurant.setPhone("405-678-1234");
        assertNotEquals("503-333-3333", restaurant.getPhone());
    }


    @Test
    public void setWebsite_setsCorrectWebsite() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        restaurant.setWebsite("http://steakhouse.com");
        assertNotEquals("no website listed", restaurant.getWebsite());
    }

    @Test
    public void setEmail_setsCorrectEmail() throws Exception{
        Restaurant restaurant = setupNewRestaurant();
        restaurant.setEmail("semaria.eyzac@gmail.com");
        assertNotEquals("no website listed", restaurant.getEmail());
    }



    private Restaurant setupNewRestaurant() {
        return new Restaurant("sems", "1234 lane", "97230", "503-333-3333", "http://steakhouse.com", "semaria.eyzac@gmail.com");
    }

    public Restaurant setupAltRestaurant (){
        return new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874");

    }
}