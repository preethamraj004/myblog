package com.myblog7.controller;

import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/post")
public class PostController { // From here the flow of the project will start. Controller will call the Service. Service will call the Repository.
    // Repository will save the data to database

    private PostService postService;

    public PostController(PostService postService) { // Instead of adding @Autowired annotation we are using constructor based dependency injection.
        this.postService = postService; // By doing this The Object of Service layer is created
    }

    //Handler Method

    //@Valid annotation applied, because  PostDto checking will happen with Spring Validation and it will report the Error.
    //The Error reporting we use the class BindingResult.

    @PreAuthorize("hasRole('ADMIN')")// This means This Particular method will only accessable only by ADMIN and not USER. This is called as Authorization
    @PostMapping
    // http://localhost:8080/api/post // when we use this url and submit in Postman.The Jason content from Postman will go the PostDto object with the help of RequestBody which is Dto and not an Entity object

    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result) { // This method will return Response entity type. i.e. ResponseEntity will return the response back in PostMan amd that Response should go as a Dto.
     // before it was returning postdto in the generics. but as we are returning internal server error                //And we are taking the content into postDto.
     //message it shows error so changing the generics to question mark we can have                                 // RequestBody will copy the data from JSON to Dto
        //any kind of return Statement.
        if(result.hasErrors()){//The Binding Class is useful when there is an error in the postDto we can send the error to the PostMan using BindingResult
                                //so now if it has any errors we are sending that has a Response as below
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.savePost(postDto); // so this postService is returning back the dto
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//201  //We are Returning back the Dto. and Whenever we save the data back in database, HttpStatus should be CREATED that means httpStatus code should be 201
        // And This ResponseEntity is the one which will display the dto and Response status code in the Response section of the PostMan

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")//http://localhost:8080/api/post/1   // As it is PathParameter we have to use annotation PathVariable
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){   // This will return The String by taking the id from the postman,Baecause we want a message post is deleted
          postService.deletePost(id); // It will call the method in the service layer by supplying the value id
        return new ResponseEntity<>("Post is Deleted",HttpStatus.OK) ; //200         // once we delete the id we will be back here from the service layer.
        // The first part is String we are giving the msg Post is Deleted. Whenever we delete the Data we have to give HttpStatus as OK.
        //Response Entity will display the message in the response of PostMan and also the status Code 200
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}") // http://localhost:8080/api/post/1 // here we are supplying the id number via url and content to be updated via Json object PostDto in the below line.
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id,@RequestBody PostDto postDto){ // here we are returning back the PostDto object because what data we have updated we want that in the console
        PostDto dto = postService.updatePost(id, postDto);// we are calling the Service method by supplying the id number and the Json object i.e.postDto which will return the dto
        return new ResponseEntity<>(postDto, HttpStatus.OK);// Whenever we update the record the stutus should be OK//200
    }

    @GetMapping("/{id}") // http://localhost:8080/api/post/1
    public ResponseEntity<PostDto>  getPostById(@PathVariable("id") long id){ //
        PostDto dto = postService.getPostById(id); // now controller layer  get back the dto from serviceimpl
        return new ResponseEntity<>(dto, HttpStatus.OK); // Whenever we find the raecord  Http status has to be OK. //200
    }

    //To read all the data at once
//    @GetMapping  // http://localhost:8080/api/post/1
//    public List<PostDto> getPosts(){ // method which will return List of PostDto
//       List<PostDto> postDtos= postService.getPosts();// Calling the Service Layer And it will return back List of PostDto
//    return postDtos;
//    }


//    Pagination // http://localhost:8080/api/post?pageNO=0&pageSize=5&sortBy=title&sortDir=desc  // page number 0 means first Page.And it should has question mark because we ar using RequestParam so it is a query Parameter
                                                                                        // It will sort the record by title.//sort direction provides optiom to sort the record in ascending or decending order
   @GetMapping
    public PostResponse getPosts(
        @RequestParam(value="pageNO",defaultValue = "0",required = false) int pageNO,// the first thing we are reading here is page number default value is suppose if we dont mention the page number in the url by dafault it will that page
                                                                                      // Required is false means it is not neccessary to mention the page number in the url
                                                                                    // Amd we are copying that page number to int pageNO
        @RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize, // Reading the PageSize, If we don't mention the
                                                                                        //pagesize in the url it will default take it has a 5
        @RequestParam(value="sortBy", defaultValue ="id", required=false) String sortBy,  // Reading the sortBy.If we don't give that in the url it will sort by id as we set it as a default

        @RequestParam(value="sortDir", defaultValue ="asc", required=false) String sortDir  // Reading the sortDir. If don't mention in url it will sort in ascending order
   ){
        PostResponse postResponse= postService.getPosts(pageNO,pageSize,sortBy,sortDir);// Calling the Service Layer. And we are supplying pageNo and pageSize and sortBy and sortDir. And it will return back List of PostDto
        return postResponse;


            }
            }
