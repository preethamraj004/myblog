package com.myblog7.entity;

import lombok.Data;


import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = { // Table name is users
        @UniqueConstraint(columnNames = {"username"}),// username field and email we are making it unique so that duplicate entries can't be possible
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String username;

    private String email;

    private String password;

    // Here the first word Many is for User(line no.15) and second word Many is forRole(line No.36)

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)//FetchType Eager means the moment we load the Project all these tables will load into the Cache memory.CascadeType All means If we make the changews in one table those changes will reflect in other table
    @JoinTable(name = "user_roles",//Join Table means we are mentioning the table name user roles and telling that join  these tables User abd Role with Third table user_roles
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName // To provide the link between Parent table and Child Table.We are using JoinColumns and inverseJoinColumns. In JoinColumn user id is the foreign key in the third table which is reference to primary key id of the Parent table user
                    = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",//In InverseJoinColumn role_id is the foreign key in the third table which is reference to primary key id of the role table
                    referencedColumnName = "id"))
    private Set<Role> roles; // When it is many to many we use Set because we don't want duplicate values. As Set can't have duplicate values we are using Set. If use List it can have duplicate values
}
