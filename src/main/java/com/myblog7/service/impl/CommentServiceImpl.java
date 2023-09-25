package com.myblog7.service.impl;

import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.CommentDto;
import com.myblog7.repository.CommentRepository;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    //Constructor based injection to create Object of CommentRepository, PostRepository, ModelMapper
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }




    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment =mapToEntity(commentDto);// taking the Comment dto and it has to be converted into Comment object as it returns Comment object
// We need to save this comment for this post
        Post post = postRepository.findById(postId).orElseThrow(    //We are applying postId
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );

        comment.setPost(post);// It means that we are setting this comment for this Post
        Comment savedComment = commentRepository.save(comment);// It will save the Comment and we will get savedComment Object then we have to convert savedComment object to Dto

        CommentDto dto =mapToDto(savedComment); //Converting to dto object by taking savedComment Object
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(    //We are checking whether the post is present or not
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );
        List<Comment> comments  = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());//we are using Stream Api to convert comment Object to CommentDTo Object. Here Comment.maptoDto is not used in this line because it will automatically uses this keyword to call the mapToDto method
                                                                                                                    // It will return commentDtos
        return commentDtos; // And now these CommentDtos will go to the Controller layer
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(    //We are checking whether the post is present or not
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(    //We are checking whether the comment is present or not
                () -> new ResourceNotFound("Comment not found with id:" + commentId)
        );

        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(    //We are checking whether the post is present or not
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(    //We are checking whether the comment is present or not
                () -> new ResourceNotFound("Comment not found with id:" + commentId)
        );

        commentRepository.deleteById(commentId);


    }

    private CommentDto mapToDto(Comment savedComment) {
        CommentDto dto = mapper.map(savedComment, CommentDto.class);//It will map the  SavedComment Class to Dto which will give us dto Object
        return dto;

    }

    private Comment mapToEntity(CommentDto commentDto) {// Taking the CommentDto Object
        Comment comment = mapper.map(commentDto, Comment.class); //It will map the Dto to Comment Class which will give us comment Object
        return comment;
    }
}
