package com.myblog7.controller;

import com.myblog7.payload.CommentDto;
import com.myblog7.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments") //http://localhost:8080/api/posts/1/comments   // From this url Id is Picked. 1 is postId using PathVariable beacause there is no question mark here
    public ResponseEntity<CommentDto> createComment(@PathVariable(value ="postId") long postId,@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }


    //getting the comment data based on the postId
    @GetMapping("/posts/{postId}/comments")   //http://localhost:8080/api/posts/1/comments  // Here it is taking the postId
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId); // That PostId will then go the ServiceLayer. In service Layer based on the PostId will get the List of CommentDTo
    }


    //getting the comment data based on the PostId
    @GetMapping("/posts/{postId}/comments/{commentId}")   //http://localhost:8080/api/posts/1/comments/2     // Here we are supplying postId and commentId
    public CommentDto getCommentsById(@PathVariable(value = "postId") Long postId,@PathVariable(value="commentId")Long commentId) {
        return commentService.getCommentsById(postId,commentId);
    }

    @GetMapping("/comments")   //http://localhost:8080/api/comments    // we are no supplying anything because we want all the comments in the table
    public List<CommentDto> getAllCommentsById() {
        return commentService.getAllCommentsById();
    }

    
    //http://localhost:8080/api/posts/1/comments/2
    @DeleteMapping("/posts/{postId}/comments/{commentId}") // From the Url two things we have to read postId and commentId
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") Long postId,@PathVariable(value="commentId")Long commentId){
        commentService.deleteCommentById(postId,commentId);

        return new ResponseEntity<>("Comment is Deleted", HttpStatus.OK);

    }

    }
