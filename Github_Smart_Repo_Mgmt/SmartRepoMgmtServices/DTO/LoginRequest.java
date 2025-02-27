package com.login.DTO;

public class LoginRequest {

    private String userId;
    private String password;
    private String userType; // "admin" or "student"

    public LoginRequest() {}

    public LoginRequest(String userId, String password, String userType) {
        this.userId = userId;
        this.password = password;
        this.userType = userType;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
