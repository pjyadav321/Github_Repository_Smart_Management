package com.login.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;  // Consider hashing the password before storing it.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;  // Enum for better type safety

    @Column(nullable = false)
    private String githubUserId;

    @Column(nullable = false)
    private String githubRepoName;

    @Column(nullable = false)
    private String githubAPIToken;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(Long id, String userName, String userId, String password, UserType userType, String githubUserId, String githubRepoName, String githubAPIToken) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.password = password;
        this.userType = userType;
        this.githubUserId = githubUserId;
        this.githubRepoName = githubRepoName;
        this.githubAPIToken = githubAPIToken;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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


