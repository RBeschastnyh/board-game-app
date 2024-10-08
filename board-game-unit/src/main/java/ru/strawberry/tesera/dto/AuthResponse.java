package ru.strawberry.tesera.dto;

public class AuthResponse {

    private String id;
    private transient String token;
    private String expiresMin;

    public AuthResponse(String id, String token, String expiresMin) {
        this.id = id;
        this.token = token;
        this.expiresMin = expiresMin;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getExpiresMin() {
        return expiresMin;
    }
}
