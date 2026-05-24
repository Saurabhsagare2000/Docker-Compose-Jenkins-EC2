package com.blog.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.Repositories.*;
import com.blog.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    // Create User
    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);

        user.setPassword(
                passwordEncoder.encode(
                        userDto.getPassword()
                )
        );

        User savedUser =
                this.userRepo.save(user);

        return this.userToDto(savedUser);
    }

    // Update User
    @Override
    public UserDto updateUser(
            UserDto userDto,
            Integer userId) {

        User user =
                this.userRepo.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User",
                                        "id",
                                        userId
                                )
                        );

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(
                passwordEncoder.encode(
                        userDto.getPassword()
                )
        );
        user.setAbout(userDto.getAbout());

        User updatedUser =
                this.userRepo.save(user);

        return this.userToDto(updatedUser);
    }

    // Get Single User
    @Override
    public UserDto getUserById(
            Integer userId) {

        User user =
                this.userRepo.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User",
                                        "id",
                                        userId
                                )
                        );

        return this.userToDto(user);
    }

    // Get All Users
    @Override
    public List<UserDto> getAllUsers() {

        List<User> users =
                this.userRepo.findAll();

        return users.stream()
                .map(this::userToDto)
                .collect(Collectors.toList());
    }

    // Delete User
    @Override
    public void deleteUser(
            Integer userId) {

        User user =
                this.userRepo.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User",
                                        "id",
                                        userId
                                )
                        );

        this.userRepo.delete(user);
    }

    // DTO -> Entity
    public User dtoToUser(
            UserDto userDto) {

        return this.modelMapper.map(
                userDto,
                User.class
        );
    }

    // Entity -> DTO
    public UserDto userToDto(
            User user) {

        return this.modelMapper.map(
                user,
                UserDto.class
        );
    }

    // Register New User
    @Override
    public UserDto registerNewUser(
            UserDto userDto) {

        User user =
                this.modelMapper.map(
                        userDto,
                        User.class
                );

        user.setPassword(
                passwordEncoder.encode(
                        userDto.getPassword()
                )
        );

        // Default role
        Role role =
                this.roleRepo.findById(502)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Role",
                                        "id",
                                        502
                                )
                        );

        user.getRoles().add(role);

        User newUser =
                this.userRepo.save(user);

        return this.userToDto(newUser);
    }
}