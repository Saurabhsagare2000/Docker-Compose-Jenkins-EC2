package com.blog.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.service.FileService;
import com.blog.service.PostService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // Create Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        PostDto createdPost =
                this.postService.createPost(
                        postDto,
                        userId,
                        categoryId
                );

        return new ResponseEntity<>(
                createdPost,
                HttpStatus.CREATED
        );
    }

    // Get Posts By User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(
            @PathVariable Integer userId) {

        List<PostDto> userPosts =
                this.postService.getPostByUser(userId);

        return new ResponseEntity<>(
                userPosts,
                HttpStatus.OK
        );
    }

    // Get Posts By Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(
            @PathVariable Integer categoryId) {

        List<PostDto> categoryPosts =
                this.postService.getPostByCategory(
                        categoryId
                );

        return new ResponseEntity<>(
                categoryPosts,
                HttpStatus.OK
        );
    }

    // Get All Posts with Pagination
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(
                    value = "pageNumber",
                    defaultValue = "0",
                    required = false
            ) Integer pageNumber,

            @RequestParam(
                    value = "pageSize",
                    defaultValue = "2",
                    required = false
            ) Integer pageSize,

            @RequestParam(
                    value = "sortBy",
                    defaultValue = "postId",
                    required = false
            ) String sortBy,

            @RequestParam(
                    value = "sortDir",
                    defaultValue = "asc",
                    required = false
            ) String sortDir) {

        PostResponse allPosts =
                this.postService.getAllPost(
                        pageNumber,
                        pageSize,
                        sortBy,
                        sortDir
                );

        return new ResponseEntity<>(
                allPosts,
                HttpStatus.OK
        );
    }

    // Get Single Post
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(
            @PathVariable Integer postId) {

        PostDto post =
                this.postService.getPostById(postId);

        return new ResponseEntity<>(
                post,
                HttpStatus.OK
        );
    }

    // Delete Post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(
            @PathVariable Integer postId) {

        this.postService.deletePost(postId);

        return new ResponseEntity<>(
                new ApiResponse(
                        "Post deleted successfully !!",
                        true
                ),
                HttpStatus.OK
        );
    }

    // Update Post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePostById(
            @RequestBody PostDto postDto,
            @PathVariable Integer postId) {

        PostDto updatedPost =
                this.postService.updatePost(
                        postDto,
                        postId
                );

        return new ResponseEntity<>(
                updatedPost,
                HttpStatus.OK
        );
    }

    // Search Post By Title
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostTitle(
            @PathVariable("keywords") String keywords) {

        List<PostDto> result =
                this.postService.searchPosts(
                        keywords
                );

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    // Upload Image
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId)
            throws IOException {

        String fileName =
                this.fileService.uploadImage(
                        path,
                        image
                );

        PostDto postDto =
                this.postService.getPostById(postId);

        postDto.setImageName(fileName);

        PostDto updatedPost =
                this.postService.updatePost(
                        postDto,
                        postId
                );

        return new ResponseEntity<>(
                updatedPost,
                HttpStatus.OK
        );
    }

    // Download Image
    @GetMapping(
            value = "/post/image/{imageName}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response)
            throws IOException {

        InputStream resource =
                this.fileService.getResource(
                        path,
                        imageName
                );

        response.setContentType(
                MediaType.IMAGE_JPEG_VALUE
        );

        StreamUtils.copy(
                resource,
                response.getOutputStream()
        );
    }
}