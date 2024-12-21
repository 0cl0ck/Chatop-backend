package com.chatop.chatop.responses;

public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public LoginResponse setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
} 