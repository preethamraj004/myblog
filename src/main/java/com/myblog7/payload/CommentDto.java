package com.myblog7.payload;

import lombok.Data;

// We are Creating Dto to copy the data from PostMan to Java Object

@Data
public class CommentDto {
    private Long id;


    private String name;


    private String email;


    private String body;


}
