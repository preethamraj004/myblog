// To develop the Sign up feature We are developing separate Controiller called AuthController. Becuse logging itself is a different module
package com.myblog7.controller;


import com.myblog7.entity.User;
import com.myblog7.payload.LoginDto;
import com.myblog7.payload.SignUpDto;
import com.myblog7.repository.RoleRepository;
import com.myblog7.repository.UserRepository;
import com.myblog7.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired // Here we are using @Autowired because in config class we already written the @bean so automatically will create it's Object and inject into this
    private PasswordEncoder passwordEncoder; // Password must be encrypted that's why we are creating the passwordEncoder variable here

    @Autowired
    private UserRepository userRepo; // We are creating variable userRepo to save the Encrypted Password in the Database

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;


    // http://localhost:8080/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){//JWTAuthResponse is the one which is responsible for to send the Jwt token to Postman
        // here first step is verify the username and password and generate the token. Step 2 is set the Context By setting the context all the url of the application will start working
        //these below 3 lines is to verify the username and password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContextHolder.getContext().setAuthentication(authentication).This is also a built in class present in
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }
    //At first we are developing sign up feature. Sign up data will go the users table in database.Because first we have to sign up and then sign in. For that we have to build SignupDto
    @PostMapping("/signup") // http://localhost:8080/api/auth/signup
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {// When we write a question mark the return type can be anything it can be String, Integer,entity dto.
        // RequestBody will copy the data from Json

        Boolean emailExist = userRepo.existsByEmail(signUpDto.getEmail());//In order to avoid duplicate email entries we are checking ehether the email exists. The email we are taking from signUpDto. It will check and Return Boolean value True or False
        if(emailExist){// If email exist the it will return Internal Server Error
            return new ResponseEntity<>("Email Id Exist",HttpStatus.BAD_REQUEST);// Status Code 400

        }

        Boolean userNameExist= userRepo.existsByUsername(signUpDto.getUsername());
        if(userNameExist) {// If userName exist the it will return Internal Server Error
            return new ResponseEntity<>("UserName Exist", HttpStatus.BAD_REQUEST);// Status code 400
        }

// Now from the dto we are copying the data User Object
        User user =new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));// Now password will be encrypted. Then we have to save this Password for that we reqire user Repository

        userRepo.save(user);// Now it will save the user Object. and then return saved User Object
        return new ResponseEntity<>("User is registered", HttpStatus.CREATED);
    }
}