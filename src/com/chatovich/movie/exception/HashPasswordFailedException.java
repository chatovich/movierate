package com.chatovich.movie.exception;

/**
 * exception thrown due to the problems with password hashing
 */
public class HashPasswordFailedException extends Exception {

    public HashPasswordFailedException(String message) {
        super(message);
    }

    public HashPasswordFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public HashPasswordFailedException(Throwable cause) {
        super(cause);
    }
}
