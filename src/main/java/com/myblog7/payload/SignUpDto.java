package com.myblog7.payload;

// Now this class is a POJO class. Plain Old Java Object which means class dealing with Encapsulation, Getters Setters and Variables That is called as Pojo class
import lombok.Data;
@Data
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
