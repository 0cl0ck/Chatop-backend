package com.chatop.chatop.service;

import com.chatop.chatop.dtos.CreateRentalDto;
import com.chatop.chatop.dtos.RentalDto;
import com.chatop.chatop.dtos.UpdateRentalDto;
import com.chatop.chatop.exceptions.ResourceNotFoundException;
import com.chatop.chatop.exceptions.UnauthorizedException;
import com.chatop.chatop.model.Rental;
import com.chatop.chatop.model.User;
import com.chatop.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.chatop.chatop.exceptions.BadRequestException;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final FileStorageService fileStorageService;
    
    @Value("${server.port:3001}")
    private String serverPort;

    private String generateImageUrl(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "default_picture_url";
        }
        return String.format("http://localhost:%s/api/images/proxy/%s", serverPort, fileName);
    }

    public RentalService(RentalRepository rentalRepository, FileStorageService fileStorageService) {
        this.rentalRepository = rentalRepository;
        this.fileStorageService = fileStorageService;
    }

    private void validatePicture(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new BadRequestException("Le fichier doit être une image");
            }
            
            // 5MB max
            if (file.getSize() > 5 * 1024 * 1024) {
                throw new BadRequestException("La taille de l'image ne doit pas dépasser 5MB");
            }
        }
    }

    public Rental createRental(CreateRentalDto dto, User owner) throws IOException {
        validatePicture(dto.getPicture());
        
        Rental rental = new Rental();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        rental.setOwner(owner);
        
        if (dto.getPicture() != null && !dto.getPicture().isEmpty()) {
            String fileName = fileStorageService.storeFile(dto.getPicture());
            rental.setPicture(fileName);
        } else {
            rental.setPicture("default_picture_url");
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
                dto.setPicture(generateImageUrl(rental.getPicture()));
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
                .orElseThrow(() -> new ResourceNotFoundException("Location non trouvée"));
                
        if (!rental.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier cette location");
        }
        
        if (dto.getName() != null) rental.setName(dto.getName());
        if (dto.getSurface() != null) rental.setSurface(dto.getSurface());
        if (dto.getPrice() != null) rental.setPrice(dto.getPrice());
        if (dto.getDescription() != null) rental.setDescription(dto.getDescription());
        
        if (dto.getPicture() != null && !dto.getPicture().isEmpty()) {
            String fileName = fileStorageService.storeFile(dto.getPicture());
            rental.setPicture(fileName);
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
        dto.setPicture(generateImageUrl(rental.getPicture()));
        dto.setDescription(rental.getDescription());
        dto.setOwner_id(rental.getOwner().getId());
        dto.setCreated_at(rental.getCreatedAt());
        dto.setUpdated_at(rental.getUpdatedAt());
        
        return dto;
    }
}