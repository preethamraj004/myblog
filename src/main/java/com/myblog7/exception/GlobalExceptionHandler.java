package com.myblog7.exception;

import com.myblog7.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// So whenever the Exception occurs now the SpringBoot will firstly licate the class on which @ControllerADvice annotation is present. In that Class then it searches a method, Which method will handle that exception. Then that method will give the Object address. Then @ExceptionHandler will handle that exception. After handling the exception one custom message it will return back

@ControllerAdvice // by writing this annotation we are telling SpringBoot that if any Exception occurs in the Project take that Exception and give it to this class so that it will handle the exception
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {//we are building custom exception class firstly we have to make that class as sub class of ResponseEntityExceptionHandler class. Then this becomes class to handle the custom exception



    //method to handle the specific exception
    @ExceptionHandler(ResourceNotFound.class)// by using @ExceptionHandler annotation and give a Specific Class name that is ResourceNotFound. Then this method will handle only specific Exception
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFound exception, WebRequest webRequest){ // We know that response entity will return the response in Postman.So here we are returning the error Object that we are created. That Error Object has 3 things timestamp message and details. so we are returning the ErrorDetails
                                                                        // we are writing here ResourceNotFound exception because when the exception occurs that exceptoin object address will come here i.e in exception.This is like catch block exception e
                                                                        // whenever we handle the exception we have to use this webRequest. Because it has lot of information like below.

        // It will craete the errordetails object and firstly give Date and it will go to timestamp. Then exception.message This will get our exception message
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));//webRequest has getDescription it will give us the information that in  which url exception occured. this line will give that idea and we are returning back the status  Not Found
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }



    // global exceptions. If any global or generic exception occurs other than than the Resource not Found  Exception that will be handled here
    @ExceptionHandler(Exception.class) // it will handle all the generic exception
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
