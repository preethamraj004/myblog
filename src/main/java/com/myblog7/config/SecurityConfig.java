package com.myblog7.config;

import com.myblog7.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;




@Configuration // whenever we are creating configuration class we have to use this annotation. Otherwise Spring boot framework will not scan this class and it will become ordinary java class
@EnableWebSecurity// This annotation is used to enable the Spring Security's web security features.It is automatically enabled but we can write it also. We have to use this in combination with @configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //This annotation is used to enable method-level security using Spring Security. Adding this annotation will help us to control which method can be accessed by whom in the Controller layer. example: Admin can Post the data ,Update the Data etc. Where as user can only read the data

public class SecurityConfig extends WebSecurityConfigurerAdapter {//In order to implement Basic Authentication we have to make it as a sub class of WebSecurityConfigurerAdapter.
    //WebSecurityConfigurerAdapter it is depricated and not being used now a days but in some old projects we get to see

    @Autowired
    private CustomUserDetailsService userDetailsService; // We are creating CustomUserDetailsService bean. CustomUserDetailsService class responsible for loading the user details from a database using a UserRepository. Because the data we enter in the form has to be verified with the database. Entering the data from the form we developed that is loginDto. The loginDto has the Data which user is entering, But it has to be verified with database. So inorder to get that data from the database for that user  we are using CustomUserDetailsService class.

    @Bean
    PasswordEncoder passwordEncoder() { // method which will return Password encoder
        return new BCryptPasswordEncoder();// There are two types of classes 1.Built in Class-> Classes which comes default from the Spring Boot  2. External Class->Classes which are not built in and comes from external libraries like model mapper where @Autowired can't be used create the Object.
        // So in External classes we can use @Bean. It can be used in two places 1. @Springboot config main class, 2.we can use in configuration class. so this is config class we can use @Bean here to create the Object
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    // Inside WebSecurityConfigurerAdapter class there is a method called configure we have to Override it here

    @Override
    protected void configure(HttpSecurity http) throws Exception { // Here httpSecurity Object will get automatically get created once we override this method
        http
                //These are all called Chaining Statements in Spring Security
                // here using http we are calling csrf and using csrf we are calling disable. Right now we are disabling the csrf because we are in development environment. When we go the protection environment we have to enable it
                .csrf().disable()
                // then we are calling authorizeRequests.// There are mainly 2 categories of pages we get to see here one we have to access without logging in and other with logging in
                // Here which ever url we give as a input that means anyRequest() will get Authorized and authenticated and that authentication we are doing it in a basic way not based on form. where we will get dailogue box not aform
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll() // antMatchers is a method where we specify which methods can be accsesed by whom. That means all the Getmapping method which begins with api and this GEt will work for every roles.
                .antMatchers("/api/auth/**").permitAll() // This line will permit all the uri starting with api/auth.Because sign up feature should be open for all
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();


    }

    //     This another built in class getting overrided here that is idealy called as UserDetailsService Class it is coming from Security Library
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        // here we are creating two Objects which has the username password and roles. So that when I am testing we are not using the database. We are using the content of these Object for testing . These content will automatically fethced by Spring Security framework and testing will happen
//
//        UserDetails user = User.builder().username("preetham").password(passwordEncoder() // The first Object we are creating is not with the new Keyword. we are using User.builder().username("preetham") and the Pass word is Password and we ahve to encode that password. To encode that password we are calling the method getEncoder. getEncoder will return the BCrypt Object and .encode will encrypt the password and the role is User
//                .encode("password")).roles("USER").build(); // So  this is the User Object which will have user name Preetham and Password as password which is encrypted and kept in the Object and User is the Role.
//        // It is In memory AUTHENTICATION BECAUSE this Object has the username and password from where we will use that data and perform testing
//
//        // here we are craeting a Object for Admin
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder()
//                .encode("admin")).roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }


    @Override
    // This configure method is going to refer to the context that has been set in our Auth Controller.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //What this method does is it takes the userDetailsService.
        auth.userDetailsService(userDetailsService) // Here we are calling the userDetailsService method and supplying the userDetailsService Object to it. This is a Object of CustomUserService Class
                .passwordEncoder(passwordEncoder());
    }

}
