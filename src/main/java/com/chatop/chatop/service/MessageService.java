package com.chatop.chatop.service;

import com.chatop.chatop.dtos.MessageDto;
import com.chatop.chatop.model.User;
import com.chatop.chatop.repository.MessageRepository;
import com.chatop.chatop.repository.RentalRepository;
import com.chatop.chatop.exceptions.ResourceNotFoundException;
import com.chatop.chatop.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.chatop.chatop.model.Message;

import java.time.LocalDateTime;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    
    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    
    public MessageService(MessageRepository messageRepository, RentalRepository rentalRepository) {
        this.messageRepository = messageRepository;
        this.rentalRepository = rentalRepository;
    }
    
    public void createMessage(MessageDto messageDto, User user) {
        logger.info("Création d'un message par l'utilisateur : {}", user.getEmail());
        
        if (messageDto.getMessage() == null || messageDto.getRental_id() == null) {
            logger.error("Message ou rental_id manquant");
            throw new RuntimeException("Message et rental_id sont requis");
        }
        
        if (!rentalRepository.existsById(messageDto.getRental_id())) {
            throw new ResourceNotFoundException("Location non trouvée");
        }
        
        Message message = new Message();
        message.setUser(user);
        message.setRental_id(messageDto.getRental_id());
        message.setMessage(messageDto.getMessage());
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        
        messageRepository.save(message);
        logger.info("Message créé avec succès");
    }
}
