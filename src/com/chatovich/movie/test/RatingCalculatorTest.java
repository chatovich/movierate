package com.chatovich.movie.test;

import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.util.RatingCalculator;
import org.junit.*;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for the application methods
 */
public class RatingCalculatorTest {

    private  double mark;
    private  double movieRating;
    private  RatingCalculator ratingCalculator;
    private  List<Feedback> userFeedbacks;

    @Before
    public void init(){
        ratingCalculator = new RatingCalculator();
        userFeedbacks = new ArrayList<>();
        mark = 9.0;
        movieRating = 7.0;
    }

    @After
    public void clear(){
        ratingCalculator = null;
        userFeedbacks = null;
    }

    @Test
    public void calcMovieRatingTest(){
        Feedback mockFeedback = mock(Feedback.class);
        List<Feedback> movieFeedbacks = new ArrayList<>();
        movieFeedbacks.add(mockFeedback);
        when(mockFeedback.getMark() ).thenReturn(10);
        double actualRating = ratingCalculator.calcMovieRating(movieFeedbacks);
        Assert.assertEquals(10, actualRating, 0.1);
    }

    @Test
    public void calcSingleRatingTest(){
        double actualRating = ratingCalculator.calcSingleRating(mark,movieRating);
        Assert.assertEquals(5, actualRating, 0.1);
    }

    @Test
    public void calcUserRatingTest(){
        ratingCalculator = mock(RatingCalculator.class);
        ratingCalculator.calcUserRating(userFeedbacks);
        verify(ratingCalculator).calcUserRating(userFeedbacks);
    }
}
