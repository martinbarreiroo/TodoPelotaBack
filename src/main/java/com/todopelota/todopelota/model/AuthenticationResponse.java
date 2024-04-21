package com.todopelota.todopelota.model;

public class AuthenticationResponse {

    private String token;
    private Long userId;
    private String message;

    public AuthenticationResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public AuthenticationResponse(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
