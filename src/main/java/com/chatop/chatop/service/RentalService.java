package com.chatop.chatop.service;

import com.chatop.chatop.dtos.CreateRentalDto;
import com.chatop.chatop.dtos.RentalDto;
import com.chatop.chatop.dtos.UpdateRentalDto;
import com.chatop.chatop.model.Rental;
import com.chatop.chatop.model.User;
import com.chatop.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.io.IOException;
import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final FileStorageService fileStorageService;
    
    @Value("${app.base-url}")
    private String baseUrl;

    public RentalService(RentalRepository rentalRepository, FileStorageService fileStorageService) {
        this.rentalRepository = rentalRepository;
        this.fileStorageService = fileStorageService;
    }

    public Rental createRental(CreateRentalDto dto, User owner) throws IOException {
        Rental rental = new Rental();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        rental.setOwner(owner);
        
        // Gérer l'upload de l'image
        if (dto.getPicture() != null && !dto.getPicture().isEmpty()) {
            String fileName = fileStorageService.storeFile(dto.getPicture());
            rental.setPicture(baseUrl + "/api/images/" + fileName);
        } else {
            rental.setPicture(baseUrl + "/api/images/default.jpg");
        }
        
        return rentalRepository.save(rental);
    }

    public List<RentalDto> getAllRentals() throws IOException {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream()
            .map(rental -> {
                RentalDto dto = new RentalDto();
                dto.setId(rental.getId());
                dto.setName(rental.getName());
                dto.setSurface(rental.getSurface());
                dto.setPrice(rental.getPrice());
                dto.setPicture(rental.getPicture());
                dto.setDescription(rental.getDescription());
                dto.setOwner_id(rental.getOwner().getId());
                dto.setCreated_at(rental.getCreatedAt());
                dto.setUpdated_at(rental.getUpdatedAt());
                return dto;
            })
            .collect(Collectors.toList());
    }

    public Rental updateRental(Integer id, UpdateRentalDto dto, User currentUser) throws IOException {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
                
        // Vérification que l'utilisateur est le propriétaire
        if (!rental.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        
        if (dto.getName() != null) rental.setName(dto.getName());
        if (dto.getSurface() != null) rental.setSurface(dto.getSurface());
        if (dto.getPrice() != null) rental.setPrice(dto.getPrice());
        if (dto.getDescription() != null) rental.setDescription(dto.getDescription());
        
        if (dto.getPicture() != null && !dto.getPicture().isEmpty()) {
            String fileName = fileStorageService.storeFile(dto.getPicture());
            rental.setPicture(baseUrl + "/api/images/" + fileName);
        }
        
       
        
        return rentalRepository.save(rental);
    }

    public RentalDto getRentalById(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
                
        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwner_id(rental.getOwner().getId());
        dto.setCreated_at(rental.getCreatedAt());
        dto.setUpdated_at(rental.getUpdatedAt());
        
        return dto;
    }
}