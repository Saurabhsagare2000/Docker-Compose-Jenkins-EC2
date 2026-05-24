package com.blog.service;

import java.util.List;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {

    // Create Post
    PostDto createPost(
            PostDto postDto,
            Integer userId,
            Integer categoryId
    );

    // Update Post
    PostDto updatePost(
            PostDto postDto,
            Integer postId
    );

    // Delete Post
    void deletePost(
            Integer postId
    );

    // Get All Posts (Pagination + Sorting)
    PostResponse getAllPost(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir
    );

    // Get Single Post
    PostDto getPostById(
            Integer postId
    );

    // Get Posts By Category
    List<PostDto> getPostByCategory(
            Integer categoryId
    );

    // Get Posts By User
    List<PostDto> getPostByUser(
            Integer userId
    );

    // Search Posts
    List<PostDto> searchPosts(
            String keyword
    );
}