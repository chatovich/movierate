package com.movierate.movie.type;

/**
 * Class that represents status that a movie may have
 */
public enum FeedbackStatus {
    NEW ("new"),
    PUBLISHED ("published"),
    REJECTED ("rejected");

    private String status;

    FeedbackStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
