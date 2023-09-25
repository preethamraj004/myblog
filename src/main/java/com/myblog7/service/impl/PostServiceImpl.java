package com.myblog7.service.impl;

import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;


    private ModelMapper modelMapper;// This modelMapper library is External library. There are two types of library.1.Built in 2.External
    // Repository is Built in library so that Spring boot knows which object to create. But Model Mapper is External library, so that we have to create it's bean by dependency injection
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) { // We are doing Constructor Injection
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

//    public PostServiceImpl(PostRepository postRepository) { // This will give us the postRepository object
//
//        this.postRepository = postRepository;
//    }

    @Override
    public PostDto savePost(PostDto postDto) {

       Post post= mapToEntity(postDto);// Calling a mapToEntity method by supplying the postdto value. And it will return back Entity object that is Post


//        Post post = new Post();//As we can't save the Dto. Because Dto will never go to the Database.So here we have to convert Dto to Entity Object
        // Therefore we are creating Entity Object
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle()); // copying the data from Dto to Entity
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        Post savedPost = postRepository.save(post);// And we are saving the Entity object data.
        //Whatever it is saving the database it will put that into a new object savedPost. So return type is again Entity and again we have to convert it to Dto
        PostDto dto=mapToDto(savedPost); // This will return back dto Object we are caling the mapToDto by suppling the savedPost it will convert into dto and return it back
        return dto;

//        PostDto dto = new PostDto(); // We are creating Dto object to copy the data from Entity object. Because Service layer will always return back the Dto ,it will never return back the entity
//        // Now service layer is giving Dto back  to Controller and Controller will give Dto back to PostMan
//        dto.setId(savedPost.getId());
//        dto.setTitle(savedPost.getTitle());
//        dto.setDescription(savedPost.getDescription());
//        dto.setContent(savedPost.getContent());
//
//        return dto;
    }

    @Override
    public void deletePost(long id) { // this is void because we don't want anything to be return back. We just want a message Record is deleted with status. We don't want the dto
        postRepository.deleteById(id); // we are deleting the id from the database


    }

//    @Override
//    public void updatePost(long id, PostDto postDto) {
//        Post post =postRepository.findById(id).orElseThrow(
//                ()->new ResourceNotFound("Post not found with id:" +id)
//
//        );
//
//    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {// in order to update the data it has to first check the id number is present in the database, then update
        // Two things can happen when we supply the id. 1-> Id can be found. 2-> Id can't be found
        Post post = postRepository.findById(id).orElseThrow(
                // if id found it will give us the Post Object. If id is not found It should throw the Exception. Here orElse is the java 8 feature

                () -> new ResourceNotFound("Post not found with id:" + id) // we are creating a object of ResourceNotFound by supplying the msg which will call the constructor and super keyword will
                //display the msg in the Response section along with id

        );
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle()); // we are getting the content from dto and storing it in post because only entity object will go to the database and save
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post); // saved content will be get stored in the another entity object  updatedPost so that return type is again entity

        PostDto dto = mapToDto(updatedPost); // calling mapToDto method by supplying the updatedPost to convert it to dto
        return dto;
    }

    @Override
    public PostDto getPostById(long id) { // The return type of this method is PostDto
        Post post = postRepository.findById(id).orElseThrow( // This will return Back The Post Object, So we have to convert it to PostDto as this method returns PostDto
                                        // So it will find first does the post exists with the id if yes it will give the post object , if not create exception object
                () -> new ResourceNotFound("post not found with id" + id)
        );// we are applying get method because it will convert it into Entity Class

        PostDto dto=mapToDto(post); // This will return back dto Object we are calling the mapToDto by supplying the post it will convert into dto and return it back
        return dto; // so this dto will go back to the Controller layer
    }

    @Override
    public PostResponse getPosts(int pageNO, int pageSize, String sortBy, String sortDir) { // It means we are performing findAll based on the pageNo and pageSize

       Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?// Turnery Operator it is just like if else statement. This is a String Comparison.String Comparison happens with equals. Ignorecase means lowercase or uppercase doesn't matter
        // Here sortDir has asc value and we are comparing with sort.Direction.ASC.name which also gives ascending.So Ascending equals Ascending becomes True.
       //If true Operation will be performed. If false Operation 2 will be performed And taking that value into Sort Object

           //OPARATION 1:
               Sort.by(sortBy).ascending():// Which means of the above line is true i.e asc =asc then the sort object will be sorted by Ascending order. sortBy can be anything it can be title or description or content
        //OPARATION 2:
               Sort.by(sortBy).descending();// If it is false sortBy descending

        Pageable pageable = PageRequest.of(pageNO, pageSize, sort);//PageRequest is built in Class which has lot of overloadded methods which takes the values and the return type is pageable
                                                                            // sortBy in our program is a String but in this method it only takes sort object ,inorder to convert String to sort we are using sortBy
        Page<Post> pagePosts = postRepository.findAll(pageable); // here it is returning Page  of Post but we have to convert it into list of PostDto
        List<Post> posts = pagePosts.getContent(); // This get content will convert page of posts to list of posts

        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());// Using Stream API we are converting list of Post to list os PostDtos


        PostResponse postResponse =new PostResponse();
        postResponse.setPostDto(postDtos);//postDtos are the Response we are setting
        postResponse.setPageNo(pagePosts.getNumber());// Page has a built in method getNumber It will take care of getting the current page number.We are putting all the details in Post Response and we will return that PostResponse
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLast(pagePosts.isLast());// A method when returns boolean values we should write isLast not getLast
        postResponse.setTotalPages(pagePosts.getTotalPages());
        return postResponse; // so this we are returning back to the controller
    }


    // we are creating this method just to reuse it, otherwise everytime when we convert Entity Object to Dto Object we should write the below code
     PostDto mapToDto(Post post){ // We are creating a method and to this we will supply Post Object which will convert Entity Object to Dto  and it will return back PostDto
      PostDto dto= modelMapper.map(post, PostDto.class);//modelMapper has method called as map. WE are taking the Post and mapping with PostDto.class and it will return back the PostDto dto. So all the content from the Post object is going to dto object
                                                        //  PostDto.class will automatically create the object of PostDto and copies the data from post to postdto

//        PostDto dto =new PostDto(); // We are creating Dto object to copy the data from Entity object. Because Service layer will always return back the Dto ,it will never return back the entity
//        // Now service layer is giving Dto back  to Controller and Controller will give Dto back to PostMan
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }

    // Method which will convert dto to entity
      Post mapToEntity(PostDto postDto){ // This method will take the postdto and copy the dto to post and return back the Post which is Entity
          Post post= modelMapper.map(postDto, Post.class);// Taking the PostDto coontent and copying it to the Post object and returning it

//        Post post = new Post();//As we can't save the Dto. Because Dto will never go to the Database.So here we have to convert Dto to Entity Object
//        // Therefore we are creating Entity Object
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle()); // copying the data from Dto to Entity
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}



