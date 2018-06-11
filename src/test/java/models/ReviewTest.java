package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getContent_correctlyGetsContent() throws Exception{
        Review review = setupNewReview();
        assertEquals("good", review.getContent());
    }

    @Test
    public void getWrittenBy_returnsWrittenBy() throws Exception{
        Review review = setupNewReview();
        assertEquals("matt", review.getWrittenBy());
    }

    @Test
    public void getRating_returnsCorrectRating() throws Exception{
        Review review = setupNewReview();
        assertEquals(5, review.getRating());
    }

    @Test
    public void getRestaurantId_returnsCorrectRestaurantId() throws Exception{
        Review review = setupNewReview();
        assertEquals(1, review.getRestaurantId());
    }

    @Test
    public void setContent_correctlySetsContent() throws Exception{
        Review review = setupNewReview();
        review.setContent("bad");
        assertEquals("bad", review.getContent());
    }

    @Test
    public void setWrittenBy_setsWrittenBy() throws Exception{
        Review review = setupNewReview();
        review.setWrittenBy("sem");
        assertEquals("sem", review.getWrittenBy());
    }

    @Test
    public void setRating_setsCorrectRating() throws Exception{
        Review review = setupNewReview();
        review.setRating(3);
        assertEquals(3, review.getRating());
    }

    @Test
    public void setRestaurantId_setsCorrectRestaurantId() throws Exception{
        Review review = setupNewReview();
        review.setRestaurantId(2);
        assertEquals(2, review.getRestaurantId());
    }

    @Test
    public void setId_setsReviewId() throws Exception{
        Review review = setupNewReview();
        review.setId(1);
        assertEquals(1, review.getId());
    }

    private Review setupNewReview() {
        return new Review("good", "matt", 5, 1);
    }


}