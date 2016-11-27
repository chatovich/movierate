package com.movierate.movie.exception;

/**
 * Created by Yultos_ on 27.11.2016
 */
public class NotValidOperationException extends Exception {

    public NotValidOperationException(String message) {
        super(message);
    }

    public NotValidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidOperationException(Throwable cause) {
        super(cause);
    }
}
