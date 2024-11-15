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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<Map<String, String>> createMessage(
            @RequestBody MessageDto messageDto,
            Authentication authentication) {
        logger.info("=== Début de createMessage ===");
        logger.info("Authentication reçue: {}", authentication);
        if (authentication != null) {
            logger.info("Autorités: {}", authentication.getAuthorities());
        }
        logger.info("MessageDto reçu: rental_id={}, message={}", 
            messageDto.getRental_id(), 
            messageDto.getMessage());
        
        try {
            if (authentication == null) {
                logger.error("Authentication est null");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            logger.info("Authentication présente: {}", authentication);
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
            logger.info("Tentative de création du message avec user_id={}", currentUser.getId());
            messageService.createMessage(messageDto, currentUser);
            
            logger.info("Message créé avec succès");
            Map<String, String> response = new HashMap<>();
            response.put("message", "Message envoyé avec succès");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erreur lors de la création du message: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}