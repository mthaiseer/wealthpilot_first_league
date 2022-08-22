package com.wealthpilot.exception;


public class GameScheduleException extends Exception {

    private String message =  null;

    public GameScheduleException(String message) {
        super(message);
        this.message = message;
    }


    @Override
    public String getMessage() {
        return this.message;
    }
}

