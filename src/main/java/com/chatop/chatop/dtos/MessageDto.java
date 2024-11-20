package com.chatop.chatop.dtos;

public class MessageDto {
    private Integer user_id;
    private Integer rental_id;
    private String message;

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
