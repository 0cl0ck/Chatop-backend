package com.chatop.chatop.controller;

import com.chatop.chatop.dtos.CreateRentalDto;
import com.chatop.chatop.dtos.UpdateRentalDto;
import com.chatop.chatop.dtos.RentalDto;
import com.chatop.chatop.model.User;
import com.chatop.chatop.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
public class RentalController {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRentals() {
        try {
            List<RentalDto> rentals = rentalService.getAllRentals();
            Map<String, List<RentalDto>> response = new HashMap<>();
            response.put("rentals", rentals);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> createRental(
            @ModelAttribute CreateRentalDto rentalDto,
            Authentication authentication) throws IOException {
        User currentUser = (User) authentication.getPrincipal();
        rentalService.createRental(rentalDto, currentUser);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental created !");
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRental(@PathVariable Integer id,
            @ModelAttribute UpdateRentalDto rentalDto,
            Authentication authentication) throws IOException {
        
        User currentUser = (User) authentication.getPrincipal();
        logger.info("Updating rental with id: {} by user: {}", id, currentUser.getEmail());
        
        rentalService.updateRental(id, rentalDto, currentUser);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental updated !");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRental(@PathVariable Integer id) {
        try {
            RentalDto rental = rentalService.getRentalById(id);
            return ResponseEntity.ok(rental);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}