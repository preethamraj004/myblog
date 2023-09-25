package com.myblog7;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.Bean;



@SpringBootApplication // This is the Starting point of the execution in Spring Boot Application. It's from here our project execution starts
// This Annotation also makes this as a Configuration Class. That means we can make configurations
public class Myblog7Application {

	public static void main(String[] args) {

		SpringApplication.run(Myblog7Application.class, args);
	}
	// Since ModelMapper is External Library IOC will not know which object to create there for we have to tell The Ioc to create a object for Model mapper
	// In the below steps we are telling the Ioc to create object for ModelMapper

	// When do we use @Bean?
 //	->When we add an External Library, Then the Spring IOC will unable to find  out which Object to Create. Then we have to create a method and apply @Bean Annotation on that method.


	//We can only use @Bean in Configuration file
	@Bean // The moment we write this Annotation. Because this is a configuration file. This information is loaded into the Spring IOC which was not there earlier
	//Now when this Bean Annotation method is loaded into the Spring IOc. Spring IOC knows here is the method which is returning the model Mapper object. Then Picks the Object and injects into the Reference Variable
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
