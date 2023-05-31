package com.lumberjack.quizappbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumberjack.quizappbackend.config.JwtTokenUtil;
import com.lumberjack.quizappbackend.model.JwtRequest;
import com.lumberjack.quizappbackend.model.JwtResponse;
import com.lumberjack.quizappbackend.model.UserRole;
import com.lumberjack.quizappbackend.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) throws Exception {
        log.info("Invoked /authenticate {}", authenticationRequest);

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final User user = (User) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(request, user);
        Optional<com.lumberjack.quizappbackend.model.User> userOptional = userService.findUserByUsername(user.getUsername());
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("accessToken",token);
        responseMap.put("username", user.getUsername());
//        responseMap.put("roles", user.getAuthorities());
        responseMap.put("email", userOptional.get().getEmail());
        responseMap.put("firstName", userOptional.get().getFirstName());
        responseMap.put("lastName", userOptional.get().getLastName());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(), responseMap);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> registration(@RequestBody com.lumberjack.quizappbackend.model.User user,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) throws Exception {

        Optional<com.lumberjack.quizappbackend.model.User> userByUsername = userService.findUserByUsername(user.getUsername());
        Optional<com.lumberjack.quizappbackend.model.User> userByEmail = userService.findUserByEmail(user.getEmail());

        Map<String, Object> responseMap = new HashMap<>();
        com.lumberjack.quizappbackend.model.User newUser = null;
        if (userByUsername.isPresent() || userByEmail.isPresent()) {
            responseMap.put("_message", "User with such a name or email already exists");
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(), responseMap);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Set.of(UserRole.USER.getRole()));
            newUser = userService.saveUser(user);
        }
        return ResponseEntity.ok(newUser);
    }

    @RequestMapping(value = "/isAuthenticated", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        boolean isAuthenticated;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            isAuthenticated = false;
        } else {
            String token = authHeader.substring("Bearer ".length());
            isAuthenticated = jwtTokenUtil.validateAndVerifyToken(token);
        }
        return new ResponseEntity<>(isAuthenticated, HttpStatus.OK);
    }

}
