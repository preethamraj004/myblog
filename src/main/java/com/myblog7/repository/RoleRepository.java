package com.myblog7.repository;
// This repository will help us to find the Role on role id

import com.myblog7.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
