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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "API d'authentification")
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

    @Operation(summary = "Inscription d'un nouvel utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inscription réussie",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto input) {
        User user = authenticationService.signup(input);
        String token = jwtService.generateToken(user);
        
        LoginResponse response = new LoginResponse()
                .setToken(token)
                .setTokenType("Bearer")
                .setExpiresIn(jwtService.getExpirationTime());
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Connexion d'un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Connexion réussie",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto input) {
        User user = authenticationService.authenticate(input);
        String token = jwtService.generateToken(user);
        
        LoginResponse response = new LoginResponse()
                .setToken(token)
                .setTokenType("Bearer")
                .setExpiresIn(jwtService.getExpirationTime());
        
        return ResponseEntity.ok(response);
    }
}