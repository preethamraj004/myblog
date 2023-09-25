package com.myblog7.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)   // Many here is Comment and One is Post.FetchType.LAZY is used it says that don't load this Comment when the Post is Loaded. Load only Post
                                        // By default it will be FetchType.EAGER which will load the Comment along with Post
    @JoinColumn(name = "post_id", nullable = false) // we join two tables by using the foreign key @JoinColumn
    private Post post; // As it is Post that's why we are not using a List of Posts
}


    // Constructors, getters, setters, and other methods


