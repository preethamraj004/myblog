package com.myblog7.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)//404. This will give the 404 status code in the Response section of the postMan
public class ResourceNotFound extends RuntimeException{ // In order to build a custom exception firstly we have to make our class subclass of Exception

    public ResourceNotFound(String msg){ // Constructor which will take the String Message. So when we create the object of ResourceNotFound we will the msg to this constructor
        super(msg); // The super keyword will automatically display that msg in PostMan Response

    }
}
