package com.seoplog.exception;

public abstract class seopLogException extends RuntimeException {


    public seopLogException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

}
