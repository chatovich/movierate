package com.chatovich.movie.util;

import com.chatovich.movie.entity.Feedback;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for rating calculation
 */
public class RatingCalculator {

    /**
     * calculates user rating based on the marks left to movies
     * @param userFeedbacks all feedbacks left by the user
     * @return user rating
     */
    public double calcUserRating (List<Feedback> userFeedbacks){
        List<Double> singleRatings = new ArrayList<>();
        double sum = 0.;
        for (Feedback feedback : userFeedbacks) {
            int mark = feedback.getMark();
            double movieRating = feedback.getMovie().getRating();
            double singleRating = calcSingleRating(mark, movieRating);
            singleRatings.add(singleRating);
            sum += singleRating;
        }
        if (!singleRatings.isEmpty()){
            return new BigDecimal(sum / singleRatings.size()).setScale(2, RoundingMode.UP).doubleValue();
        } else {
            return 0.0;
        }
    }

    /**
     * calculates movie rating based on the marks left to movies
     * @param movieFeedbacks all feedbacks left to this movie
     * @return movie rating
     */
    public double calcMovieRating(List<Feedback> movieFeedbacks){
        int count = 0;
        int sum = 0;
        for (Feedback movieFeedback : movieFeedbacks) {
            sum+=movieFeedback.getMark();
            count++;
        }
        if (count!=0){
            return new BigDecimal((double)sum / (double)count).setScale(2, RoundingMode.UP).doubleValue();
        } else {
            return 0.0;
        }
    }

    /**
     * calculates user rating using his mark to one movie and this movie rating
     * @param mark left to movie by this user
     * @param movieRating actual rating of the movie
     * @return  user rating
     */
    public double calcSingleRating (double mark, double movieRating){
        double rating = 0.;
        double delta = Math.abs(mark-movieRating);
        if (delta>1){
            rating = 10/delta;
        }
        if (delta==0){
            rating = 10;
        }
        if (delta<=1){
            rating = 10 - delta;
        }
        return rating;
    }
}
