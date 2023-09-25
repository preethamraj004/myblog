package com.myblog7.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
   private List<PostDto> postDto; // Whatever content we are going to set here has to be a List because in the Response it should return back all the post content
    // we are using a data structure concept here which is Collection. Here we are using PostSto because whatever there in the Response of PostMan they all are Dto.So we are using Collection of PostDto
   private int pageNo;// Which is the current Page content we are showing for that pageNo is Required


    private int pageSize;

    private long totalElements;//How many Record are Present

    private int totalPages;

    private boolean last;
}
