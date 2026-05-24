package com.blog.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRepo
        extends JpaRepository<Post, Integer> {

    // Find posts by user
    List<Post> findByUser(
            User user
    );

    // Find posts by category
    List<Post> findByCategory(
            Category category
    );

    // Search posts by title
    List<Post> findByTitleContaining(
            String title
    );
}