package com.myblog7.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
// No need to write many to many mapping again because we already wrote in the user entity class it is enough
@Setter // We are not writing @Data annotation here that;s why we are using @Setter and @Getter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    private String name;
}
