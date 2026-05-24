package com.blog.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.blog.Repositories.UserRepo;
import com.blog.Security.*;
import com.blog.configration.UserDetailServiceImpl;
import com.blog.entities.User;
import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@Validated
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    // Login API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request) {

        // Authenticate user
        this.authenticate(
                request.getUsername(),
                request.getPassword()
        );

        // Load user details
        UserDetails userDetails =
                this.userDetailServiceImpl.loadUserByUsername(
                        request.getUsername()
                );

        // Generate token
        String token =
                this.jwtTokenHelper.generateToken(userDetails);

        // Prepare response
        JwtAuthResponse response =
                new JwtAuthResponse();

        response.setToken(token);

        User user =
                userRepo.getUserByUserName(
                        request.getUsername()
                );

        UserDto userDto =
                modelMapper.map(
                        user,
                        UserDto.class
                );

        response.setUser(userDto);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    // Authentication Method
    private void authenticate(
            String username,
            String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                );

        authenticationManager.authenticate(authenticationToken);
    }

    // Register API
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(
            @Valid @RequestBody UserDto userDto) {

        UserDto registeredUser =
                this.userService.registerNewUser(userDto);

        return new ResponseEntity<>(
                registeredUser,
                HttpStatus.CREATED
        );
    }
}