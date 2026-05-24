package com.blog.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody UserDto userDto) {

        UserDto createdUser =
                this.userService.createUser(userDto);

        return new ResponseEntity<>(
                createdUser,
                HttpStatus.CREATED
        );
    }

    // Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @Valid @RequestBody UserDto userDto,
            @PathVariable Integer userId) {

        UserDto updatedUser =
                this.userService.updateUser(
                        userDto,
                        userId
                );

        return ResponseEntity.ok(updatedUser);
    }

    // Delete User
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(
            @PathVariable Integer userId) {

        this.userService.deleteUser(userId);

        return new ResponseEntity<>(
                new ApiResponse(
                        "User deleted successfully",
                        true
                ),
                HttpStatus.OK
        );
    }

    // Get All Users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return new ResponseEntity<>(
                this.userService.getAllUsers(),
                HttpStatus.OK
        );
    }

    // Get Single User
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(
            @PathVariable Integer userId) {

        return new ResponseEntity<>(
                this.userService.getUserById(userId),
                HttpStatus.OK
        );
    }
}