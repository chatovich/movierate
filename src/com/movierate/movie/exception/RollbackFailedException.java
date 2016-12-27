package com.movierate.movie.exception;

/**
 * exception thrown when connection rollback is not executed
 */
public class RollbackFailedException extends Exception {

    public RollbackFailedException(String message) {
        super(message);
    }

    public RollbackFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RollbackFailedException(Throwable cause) {
        super(cause);
    }
}
