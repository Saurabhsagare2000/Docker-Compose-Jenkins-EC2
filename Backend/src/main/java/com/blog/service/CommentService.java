package com.blog.service;

import com.blog.payloads.CommentDto;

public interface CommentService {

    // Create Comment
    CommentDto createComment(
            CommentDto commentDto,
            Integer postId
    );

    // Delete Comment
    void deleteComment(
            Integer commentId
    );
}