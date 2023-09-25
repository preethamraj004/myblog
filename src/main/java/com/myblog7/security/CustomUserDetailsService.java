package com.myblog7.security;
//This class will help us to retrieve the data from the database


import com.myblog7.entity.Role;
import com.myblog7.entity.User;
import com.myblog7.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailsService implements UserDetailsService {// So the CustomUserDetails class job is based on the user name get the user data and put that into the User Object

    private UserRepository userRepository; // This class uses a Repository layer
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    // The job of this method is to fetch the user details from the database based on the data
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {  // This is where the Username given which is given automatically by SpringSecurity to it.When we give data to our loginDto that username goes there is automatically coming here. So we needn't call this method . And based on that user we find the data in the database.

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail) // That data is stored into the User Object. So this User Object is now has the data for the record we are searching for. Two thing might happen. Record is Found. If not found it will give usernamenotfound Exception. Which is a built in class present in SpringSecurity
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));


        // Here we are getting the Email, Password and Roles from the Database
        return new org.springframework.security.core.userdetails.User(user.getEmail(), //Whatever the roles we are giving those roles should come from database and get assigned. So three things will happen here. 1.Email, 2.Password, 3.Roles
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));// As we have to get a particular role not a set of Roles, So here mapRolesToAuthorities(user.getRoles() will just fetch that particular role from it and initialize that particular variable. In order to get the Role this is the method that will help us to get the Role
    }


    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
