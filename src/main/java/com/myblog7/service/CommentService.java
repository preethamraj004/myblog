package com.myblog7.service;

import com.myblog7.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);// To save the Comment we are supplying Dto to it and the postId. Because we have to mention for that post save the Comment

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentsById(Long postId,Long commentId);

    List<CommentDto> getAllCommentsById();

    void deleteCommentById(Long postId, Long commentId);
}
