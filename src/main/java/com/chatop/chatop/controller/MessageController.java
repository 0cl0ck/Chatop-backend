package com.chatop.chatop.controller;

import com.chatop.chatop.dtos.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.chatop.chatop.model.User;
import com.chatop.chatop.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Messages", description = "API de gestion des messages")
@RestController
@RequestMapping("/api/messages")
@CrossOrigin
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Envoyer un message",
        security = { @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Message envoyé", 
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @PostMapping
    public ResponseEntity<Map<String, String>> createMessage(
            @Valid @RequestBody MessageDto messageDto,
            Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        User currentUser = (User) authentication.getPrincipal();
        logger.info("User authentifié: id={}, email={}", 
            currentUser.getId(), 
            currentUser.getEmail());
        
        if (messageDto.getRental_id() == null || messageDto.getMessage() == null) {
            logger.error("rental_id ou message manquant: rental_id={}, message={}", 
                messageDto.getRental_id(), 
                messageDto.getMessage());
            return ResponseEntity.badRequest().build();
        }
        
        messageDto.setUser_id(currentUser.getId());
        messageService.createMessage(messageDto, currentUser);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Message envoyé avec succès");
        return ResponseEntity.ok(response);
    }
}