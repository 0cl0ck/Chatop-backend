package com.chatop.chatop.controller;

import com.chatop.chatop.dtos.CreateRentalDto;
import com.chatop.chatop.dtos.RentalDto;
import com.chatop.chatop.model.Rental;
import com.chatop.chatop.model.User;
import com.chatop.chatop.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.chatop.chatop.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRentals() {
        try {
            //List<RentalDto> rentals = rentalService.getAllRentals();
            List<Rental> rentals = rentalService.getAllRentals();

            Map<String, List<Rental>> response = new HashMap<>();
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
}