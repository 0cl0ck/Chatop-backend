package com.chatop.chatop.service;

import com.chatop.chatop.dtos.RentalDto;
import com.chatop.chatop.model.Rental;
import com.chatop.chatop.repository.RentalRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<RentalDto> getAllRentals() {
        return rentalRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private RentalDto convertToDto(Rental rental) {
        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwner_id(rental.getOwner().getId().longValue());
        dto.setCreated_at(rental.getCreatedAt());
        dto.setUpdated_at(rental.getUpdatedAt());
        return dto;
    }
}