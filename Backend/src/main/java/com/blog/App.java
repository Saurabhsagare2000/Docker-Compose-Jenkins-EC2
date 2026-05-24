package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blog.entities.Role;
import com.blog.Repositories.RoleRepo;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            System.out.println("Application Started...");

            Role role = new Role();
            role.setId(501);
            role.setName("ROLE_NORMAL");

            Role role1 = new Role();
            role1.setId(502);
            role1.setName("ROLE_ADMIN");

            List<Role> roles = List.of(role, role1);

            List<Role> result = this.roleRepo.saveAll(roles);

            result.forEach(r -> System.out.println(r.getName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}