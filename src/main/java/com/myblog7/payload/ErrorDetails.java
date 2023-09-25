package com.myblog7.payload;

import java.util.Date;


// We are creating this class in Payload because payload informatuion will be given as a response in postman. That is the job of the payload
public class ErrorDetails {
    private Date timestamp;// These 3 informations we want to communicate back in the Postman. Whenever exceptions occurs these are things we are going to supply back.If want someother information we can add that variable here
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp; // Constructor based Injection. These will initialize the variables when we create the object of the ErrorDetails
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}


