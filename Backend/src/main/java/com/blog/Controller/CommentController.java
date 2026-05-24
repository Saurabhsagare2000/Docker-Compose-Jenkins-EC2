package com.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.service.CommentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Create Comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId) {

        CommentDto createdComment =
                this.commentService.createComment(
                        commentDto,
                        postId
                );

        return new ResponseEntity<>(
                createdComment,
                HttpStatus.CREATED
        );
    }

    // Delete Comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Integer commentId) {

        this.commentService.deleteComment(commentId);

        return new ResponseEntity<>(
                new ApiResponse(
                        "Comment deleted successfully !!",
                        true
                ),
                HttpStatus.OK
        );
    }
}