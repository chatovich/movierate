package com.movierate.movie.exception;

/**
 * exception thrown due to the problems on DAO layer
 */
public class DAOFailedException extends Exception {

    public DAOFailedException(String message) {
        super(message);
    }

    public DAOFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOFailedException(Throwable cause) {
        super(cause);
    }
}
