package com.sudipta.fleet.exception;


public class RequestDataMissingException extends RuntimeException {

    private String message;

    public RequestDataMissingException(String message) {
        super(message);
        this.message = message;

    }
}
