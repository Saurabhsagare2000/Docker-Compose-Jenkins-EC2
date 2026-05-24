package com.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserDto {

    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be minimum 4 characters")
    private String name;

    @Email(message = "Email address is not valid")
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be min 3 and max 10 characters")
    private String password;

    @NotEmpty
    private String about;

    private List<RoleDto> roles = new ArrayList<>();

    public UserDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

	public UserDto(int id, @NotEmpty @Size(min = 4, message = "Username must be minimum 4 characters") String name,
			@Email(message = "Email address is not valid") @NotEmpty String email,
			@NotEmpty @Size(min = 3, max = 10, message = "Password must be min 3 and max 10 characters") String password,
			@NotEmpty String about, List<RoleDto> roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.roles = roles;
	}
    
}