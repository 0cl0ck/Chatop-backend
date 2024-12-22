package com.chatop.chatop.dtos;

import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO pour la création d'une location")
public class CreateRentalDto {
    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom de la location", example = "Appartement Paris 15")
    private String name;

    @NotNull(message = "La surface est obligatoire")
    @Min(value = 1, message = "La surface doit être supérieure à 0")
    @Schema(description = "Surface en m²", example = "75")
    private Integer surface;

    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 1, message = "Le prix doit être supérieur à 0")
    @Schema(description = "Prix mensuel en euros", example = "1200")
    private Integer price;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 2000, message = "La description ne doit pas dépasser 2000 caractères")
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