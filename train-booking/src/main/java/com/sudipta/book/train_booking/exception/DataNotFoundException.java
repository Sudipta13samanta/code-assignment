package com.sudipta.book.train_booking.exception;

public class DataNotFoundException extends RuntimeException{
    private String errorCode;
    public DataNotFoundException(String errorCode){
        super();
        this.errorCode = errorCode;
    }

    public DataNotFoundException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
