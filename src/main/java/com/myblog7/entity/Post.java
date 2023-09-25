package com.myblog7.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

//@Data,@NoArgsConstructor, @AllArgsConstructor these belong to lombok library

@Data // This annotation will automatically generate setters and getters in our project.By applying this @Data annotation Encapsulation is achieved
//@Setters and @Getters can also be used to generate setters and getters
@NoArgsConstructor // These two Annotation s will add constructors as well in our code. This will be default constructors
@AllArgsConstructor // This will add constructors with arguments
@Entity // To make the class Entity class
@Table(
        name="posts",
        uniqueConstraints = {@UniqueConstraint(columnNames={"title"})} // this is a alternative way to make column names unique.Instead of using unique =true.
)                                                                       // Here we are making title column unique
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 45)
// It creates a column in database and column name is title. And we are using nullable because we can't use null value in that column. We can also use unique if we don't want the names to be duplicate.
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)// Here One is Post and Many is comments. Beacuse it is Many we write it List of Comment.Because List can store multiple comments
                                                //CascadeType.ALL means that any operation performed on the Parent class alo be applied on the Child Class
    private List<Comment> comments; // As there is no import is available for comment we have to create separate class for Comment to import it

}
