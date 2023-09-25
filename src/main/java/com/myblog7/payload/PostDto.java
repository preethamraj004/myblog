package com.myblog7.payload;

import lombok.Data;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data // This will give the setters and getters
public class PostDto { // whatever fields we have in the entity class we have to paste the exactly same fields here without any annotations except @Data

    private Long id;

    @NotEmpty // It ensures that title shoul not be empty.If empty it will give some error We are Importing it from validation library
    @Size(min=2, message="Post title should be atleast 2 characters")//It ensures that title should be atleast of 2 characters otherwise shows this message
    private String title;

    @NotEmpty
    @Size(min=4, message="Post description should be atleast 4 characters")
    private String description;

    @NotEmpty
    @Size(min=5, message="Post content should be atleast 4 characters")
    private String content;

}
