package com.chatop.chatop.controller;

import com.chatop.chatop.model.User;
import com.chatop.chatop.dtos.LoginUserDto;
import com.chatop.chatop.dtos.RegisterUserDto;
import com.chatop.chatop.responses.LoginResponse;
import com.chatop.chatop.service.AuthenticationService;
import com.chatop.chatop.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthenticationController(
            AuthenticationService authenticationService,
            JwtService jwtService
    ) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto input) {
        User user = authenticationService.signup(input);
        String token = jwtService.generateToken(user);
        
        LoginResponse response = new LoginResponse()
                .setToken(token)
                .setExpiresIn(jwtService.getExpirationTime());
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto input) {
        User user = authenticationService.authenticate(input);
        String token = jwtService.generateToken(user);
        
        LoginResponse response = new LoginResponse()
                .setToken(token)
                .setExpiresIn(jwtService.getExpirationTime());
        
        return ResponseEntity.ok(response);
    }
}