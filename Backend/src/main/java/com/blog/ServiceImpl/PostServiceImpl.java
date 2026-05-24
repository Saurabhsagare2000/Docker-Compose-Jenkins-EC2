package com.blog.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.Repositories.*;

import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(
            PostDto postDto,
            Integer userId,
            Integer categoryId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "id",
                                userId
                        )
                );

        Category category =
                this.categoryRepo.findById(categoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category",
                                        "id",
                                        categoryId
                                )
                        );

        Post post =
                this.modelMapper.map(
                        postDto,
                        Post.class
                );

        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost =
                this.postRepo.save(post);

        return this.modelMapper.map(
                savedPost,
                PostDto.class
        );
    }

    @Override
    public PostDto updatePost(
            PostDto postDto,
            Integer postId) {

        Post post =
                this.postRepo.findById(postId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Post",
                                        "postId",
                                        postId
                                )
                        );

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost =
                this.postRepo.save(post);

        return this.modelMapper.map(
                updatedPost,
                PostDto.class
        );
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Post",
                                "postId",
                                postId
                        )
                );

        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        sort
                );

        Page<Post> pagePost =
                this.postRepo.findAll(pageable);

        List<PostDto> postDtos =
                pagePost.getContent()
                        .stream()
                        .map(post ->
                                this.modelMapper.map(
                                        post,
                                        PostDto.class
                                )
                        )
                        .collect(Collectors.toList());

        PostResponse response =
                new PostResponse();

        response.setContent(postDtos);
        response.setPageNumber(pagePost.getNumber());
        response.setPageSize(pagePost.getSize());
        response.setTotalElements(pagePost.getTotalElements());
        response.setTotalPages(pagePost.getTotalPages());
        response.setLastPage(pagePost.isLast());

        return response;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Post",
                                "postId",
                                postId
                        )
                );

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category",
                                "id",
                                categoryId
                        )
                );

        List<Post> posts =
                this.postRepo.findByCategory(category);

        return posts.stream()
                .map(post ->
                        this.modelMapper.map(
                                post,
                                PostDto.class
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "id",
                                userId
                        )
                );

        List<Post> posts =
                this.postRepo.findByUser(user);

        return posts.stream()
                .map(post ->
                        this.modelMapper.map(
                                post,
                                PostDto.class
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts =
                this.postRepo.findByTitleContaining(keyword);

        return posts.stream()
                .map(post ->
                        this.modelMapper.map(
                                post,
                                PostDto.class
                        )
                )
                .collect(Collectors.toList());
    }
}