package com.chatop.chatop.controller;

import com.chatop.chatop.dtos.RentalDto;
import com.chatop.chatop.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<RentalDto>>> getAllRentals() {
        List<RentalDto> rentals = rentalService.getAllRentals();
        Map<String, List<RentalDto>> response = new HashMap<>();
        response.put("rentals", rentals);
        return ResponseEntity.ok(response);
    }
}