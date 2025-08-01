package com.example.Springbootproject.controller;

import com.example.Springbootproject.entity.User;
import com.example.Springbootproject.service.UserDetailsServiceImpl;
import com.example.Springbootproject.service.UserEntryService;
import com.example.Springbootproject.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserEntryService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody User user) {
        try {
            userService.saveNewUser(user); // password encoding must happen inside this method
            return ResponseEntity.status(201).body(Collections.singletonMap("message", "User created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Failed to create user: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("token", jwt));
        } catch (AuthenticationException e) {
            log.error("Authentication failed:", e);
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Incorrect Username or Password"));
        }
    }
}
