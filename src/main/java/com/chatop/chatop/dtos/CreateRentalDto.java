package com.chatop.chatop.dtos;

import org.springframework.web.multipart.MultipartFile;

public class CreateRentalDto {
    private String name;
    private Integer surface;
    private Integer price;
    private String description;
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