package com.chatop.chatop.service;

import com.chatop.chatop.dtos.CreateRentalDto;
import com.chatop.chatop.dtos.UpdateRentalDto;
import com.chatop.chatop.model.Rental;
import com.chatop.chatop.model.User;
import com.chatop.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<Rental> getAllRentals() throws IOException {
        return rentalRepository.findAll();
    }

    public Rental updateRental(Long id, UpdateRentalDto dto, User currentUser) throws IOException {
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
}