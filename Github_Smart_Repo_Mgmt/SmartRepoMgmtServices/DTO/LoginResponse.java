package com.login.DTO;

public class LoginResponse {
    private String status;
    private String message;
    private String githubUserId;
    private String githubRepoName;
    private String githubAPIToken;

    // Constructor
    public LoginResponse(String status, String message, String githubUserId, String githubRepoName, String githubAPIToken) {
        this.status = status;
        this.message = message;
        this.githubUserId = githubUserId;
        this.githubRepoName = githubRepoName;
        this.githubAPIToken = githubAPIToken;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGithubUserId() {
        return githubUserId;
    }

    public void setGithubUserId(String githubUserId) {
        this.githubUserId = githubUserId;
    }

    public String getGithubRepoName() {
        return githubRepoName;
    }

    public void setGithubRepoName(String githubRepoName) {
        this.githubRepoName = githubRepoName;
    }

    public String getGithubAPIToken() {
        return githubAPIToken;
    }

    public void setGithubAPIToken(String githubAPIToken) {
        this.githubAPIToken = githubAPIToken;
    }
}
