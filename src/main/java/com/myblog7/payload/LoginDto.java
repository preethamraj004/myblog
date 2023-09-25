package com.myblog7.payload;

//firstly in order to login we are taking login data into this class
// When we supply the Json Object with username and password it will go the LoginDto.

import lombok.Data;

@Data
public class LoginDto { // What LoginDto does is it will take the data from json Object into the java Object
    private String usernameOrEmail;
    private String password;
}
