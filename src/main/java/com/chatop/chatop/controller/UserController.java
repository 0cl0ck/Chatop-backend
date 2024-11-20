package com.chatop.chatop.controller;

import com.chatop.chatop.model.User;
import com.chatop.chatop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chatop.chatop.dtos.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@Tag(name = "Users", description = "API de gestion des utilisateurs")
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obtenir les informations de l'utilisateur connecté",
        security = { @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informations récupérées"),
        @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @GetMapping("/auth/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
    @Operation(
        summary = "Obtenir la liste des utilisateurs",
        security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Obtenir les informations d'un utilisateur par son ID",
        security = { @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Integer id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}   