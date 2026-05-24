package com.blog.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entities.User;

public interface UserRepo
        extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);

    // For AuthController login
    
}