package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoodtypeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName_getsNameOfFoodtype() throws Exception{
        Foodtype foodtype = setupFoodtype();
        assertEquals("fish", foodtype.getName());
    }

    @Test
    public void setName_setsNameOfFoodtype() throws Exception{
        Foodtype foodtype = setupFoodtype();
        foodtype.setName("steak");
        assertEquals("steak", foodtype.getName());
    }

    @Test
    public void setId_setsReviewId() throws Exception{
        Foodtype foodtype = setupFoodtype();
        foodtype.setId(1);
        assertEquals(1, foodtype.getId());
    }

    private Foodtype setupFoodtype() {
        return new Foodtype("fish");
    }

}