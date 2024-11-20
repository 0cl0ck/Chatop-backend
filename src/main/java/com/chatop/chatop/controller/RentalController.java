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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Rentals", description = "API de gestion des locations")
@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
public class RentalController {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Operation(summary = "Récupérer toutes les locations",
        security = { @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des locations récupérée"),
        @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
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

    @Operation(summary = "Créer une nouvelle location",
        security = { @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location créée"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> createRental(
            @Parameter(description = "Données de la location")
            @ModelAttribute CreateRentalDto rentalDto,
            Authentication authentication) throws IOException {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User currentUser = (User) authentication.getPrincipal();
        rentalService.createRental(rentalDto, currentUser);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental created !");
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Mettre à jour une location",
        security = { @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Location mise à jour"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "401", description = "Non authentifié"),
        @ApiResponse(responseCode = "403", description = "Non autorisé"),
        @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRental(
            @Parameter(description = "ID de la location") @PathVariable Integer id,
            @Parameter(description = "Données de mise à jour") @ModelAttribute UpdateRentalDto rentalDto,
            Authentication authentication) throws IOException {
        
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User currentUser = (User) authentication.getPrincipal();
        logger.info("Updating rental with id: {} by user: {}", id, currentUser.getEmail());
        
        rentalService.updateRental(id, rentalDto, currentUser);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental updated !");
        
        return ResponseEntity.ok(response);
    }
    @Operation(
        summary = "Obtenir une location par son ID",
        security = { @SecurityRequirement(name = "bearerAuth") }
    )
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