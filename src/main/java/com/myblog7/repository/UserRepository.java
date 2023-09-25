package com.myblog7.repository;
// We are creating the user Repository because we have to develop a logic to check whether user details are already present if not. Then register the user

import com.myblog7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); //findByEmail this method will the find the Record based on the email id and return back an Optional Object
    Optional<User> findByUsernameOrEmail(String username, String email); //findByUsernameOrEmail will find the Record based on both username and email. Because there will  be option login with username or email if either is true it will search the details. And it will return Optional Object
    // It is just like in sql query select * from where username = or email =
    Optional<User> findByUsername(String username);// findByUsername method is for finding the Record based on the username
    Boolean existsByUsername(String username); // These two methods will return boolean values true if the record exists and false if doesn't
    Boolean existsByEmail(String email);

}
