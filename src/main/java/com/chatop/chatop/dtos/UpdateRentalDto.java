package com.chatop.chatop.dtos;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;

public class UpdateRentalDto {
    private String name;
    private Integer surface;
    private Integer price;
    private String description;
    private MultipartFile picture;

    // Getters et Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getSurface() { return surface; }
    public void setSurface(Integer surface) { this.surface = surface; }
    
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public MultipartFile getPicture() { return picture; }
    public void setPicture(MultipartFile picture) { this.picture = picture; }
}