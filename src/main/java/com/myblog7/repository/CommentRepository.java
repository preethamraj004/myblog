package com.myblog7.repository;

import com.myblog7.entity.Comment;
import com.myblog7.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

   //Building custom method to search the data based on the particular column of the table. If it is only Id we need not do that because there is a built in method findById
    List<Comment> findByPostId(long postId);// custom method where we are supplying the long PostId and it will return back List of Comment.It is entity Object because we are getting it from the Database.
                                                //In hibernate when we write any method in Repository Layer using findBy with Column name this will automatically build the sql querry
                                                //select * from comment where PostId=.// Ic we want to find the Record based on mobile number then we can use findByMobilenumber.

}
