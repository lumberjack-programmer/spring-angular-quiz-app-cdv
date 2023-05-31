package com.lumberjack.quizappbackend.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumberjack.quizappbackend.config.JwtTokenUtil;
import com.lumberjack.quizappbackend.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;

        if (request.getParameter("username") != null && request.getParameter("password") != null) {
            username = request.getParameter("username");
            password = request.getParameter("password");
            log.info("attemptAuthentication with username: {} password: {}", username, password);
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String token = jwtTokenUtil.generateToken(request, user);
        Optional<com.lumberjack.quizappbackend.model.User> userOptional = userService.findUserByUsername(user.getUsername());
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("accessToken",token);
        responseMap.put("username", user.getUsername());
        responseMap.put("email", userOptional.get().getEmail());
        responseMap.put("firstName", userOptional.get().getFirstName());
        responseMap.put("lastName", userOptional.get().getLastName());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(), responseMap);
    }
}
