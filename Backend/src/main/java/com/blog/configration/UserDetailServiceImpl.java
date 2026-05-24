package com.blog.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.Repositories.UserRepo;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepo.getUserByUserName(username);
        if(user==null)
        	
        	throw new UsernameNotFoundException("Could not found User!!");

        CustomUserDetails customUserDetails=new CustomUserDetails(user);
        return  customUserDetails;
    }
}