package com.myblog7.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//The rule in SpringBoot is we can't store the password or use the password without encryption. By default this is the rule, Password must be encrypted otherwise Spring security will noy work
public class MainClass {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Craeting the Object and taking that address into the variable
        System.out.println(passwordEncoder.encode("Preetham@4"));// the above class BCryptPasswordEncoder has method called encode. This encode method ,When we enter the Password Preetham@4 it will convert that password to some encoded stuff which we can see in console
    }
}
