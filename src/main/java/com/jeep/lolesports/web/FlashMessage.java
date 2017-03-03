package com.jeep.lolesports.web;

/**
 * Created by jeeps on 3/3/2017.
 */
public class FlashMessage {
    private String message;
    private Status status;

    public FlashMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status {
        SUCCESS, FAILURE
    }
}
