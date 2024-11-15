package com.chatop.chatop.dtos;

import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO pour la création d'une location")
public class CreateRentalDto {
    @Schema(description = "Nom de la location", example = "Appartement Paris 15")
    private String name;

    @Schema(description = "Surface en m²", example = "75")
    private Integer surface;

    @Schema(description = "Prix mensuel en euros", example = "1200")
    private Integer price;

    @Schema(description = "Description de la location")
    private String description;

    @Schema(description = "Photo de la location")
    private MultipartFile picture;

    // Constructeur par défaut
    public CreateRentalDto() {}

    // Getters
    public String getName() {
        return name;
    }

    public Integer getSurface() {
        return surface;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}