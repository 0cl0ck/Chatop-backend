package com.chatop.chatop.dtos;

public class RegisterUserDto {
    private String email;
    private String password;
    private String fullName;

    // Constructeurs
    public RegisterUserDto() {}

    public RegisterUserDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.fullName = name;
    }

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



    public String getFullName() {
        return fullName;
    }
}
