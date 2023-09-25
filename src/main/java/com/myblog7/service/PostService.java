package com.myblog7.service;

import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto); // method which will return back the PostDto

    void deletePost(long id);

    PostDto updatePost(long id, PostDto postDto);

    PostDto getPostById(long id);

    PostResponse getPosts(int pageNO, int pageSize, String sortBy, String sortDir);


//    void updatePost(long id, PostDto postDto);
}

