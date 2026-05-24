package com.blog.service;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {

    // Register New User
    UserDto registerNewUser(
            UserDto userDto
    );

    // Create User
    UserDto createUser(
            UserDto userDto
    );

    // Update User
    UserDto updateUser(
            UserDto userDto,
            Integer userId
    );

    // Get Single User
    UserDto getUserById(
            Integer userId
    );

    // Get All Users
    List<UserDto> getAllUsers();

    // Delete User
    void deleteUser(
            Integer userId
    );
}