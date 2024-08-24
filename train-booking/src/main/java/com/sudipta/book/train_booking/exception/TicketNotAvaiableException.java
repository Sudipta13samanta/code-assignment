package com.sudipta.book.train_booking.exception;

public class TicketNotAvaiableException extends RuntimeException {
    private String errorCode;
    public TicketNotAvaiableException(String errorCode){
        super();
        this.errorCode = errorCode;
    }

    public TicketNotAvaiableException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
