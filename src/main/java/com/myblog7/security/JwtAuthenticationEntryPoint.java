package com.myblog7.security;
// This class is basically built because when we are accessing the url or api which is not accessible for us.Example user trying to create Post the Record which can only done by admin.
//In those times it takes the Request and if it is not accessible request then it will give unauthorized exception


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,   // HttpServletRequest represents the incoming Http request that triggerd the authentication failure
                         AuthenticationException authException) throws IOException, ServletException {  // HttpServletResponse represents the response that will be sent back to the client. To send the Response back we are using Exception Object
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                authException.getMessage());
    }
}
