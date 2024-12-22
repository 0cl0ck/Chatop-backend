package com.chatop.chatop.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MessageDto {
    @NotNull(message = "L'ID de la location est obligatoire")
    private Integer rental_id;

    @NotBlank(message = "Le message ne peut pas être vide")
    private String message;

    private Integer user_id;

    // Constructeur par défaut
    public MessageDto() {}

    // Getters
    public Integer getUser_id() {
        return user_id;
    }

    public Integer getRental_id() {
        return rental_id;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setRental_id(Integer rental_id) {
        this.rental_id = rental_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
