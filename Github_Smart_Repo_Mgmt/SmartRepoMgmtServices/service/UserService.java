/*package com.login.service;

import com.login.DTO.LoginRequest;
import com.login.entity.User;
import com.login.exception.InvalidCredentialsException;
import com.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Login method
    public String login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUserId(loginRequest.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Validate password and userType
            if (user.getPassword().equals(loginRequest.getPassword()) && user.getUserType().equals(loginRequest.getUserType())) {
                return "Login successful";
            } else {
                throw new InvalidCredentialsException("Invalid credentials");
            }
        } else {
            throw new InvalidCredentialsException("User not found");
        }
    }

    // Register method
    public String register(User user) {
        // Check if user already exists
        Optional<User> existingUser = userRepository.findByUserId(user.getUserId());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists with userId: " + user.getUserId());
        }

        // Save the new user to the database
        userRepository.save(user);
        return "User registered successfully";
    }

    // Retrieve all students
    public List<User> getStudents() {
        return userRepository.findByUserType("student");
    }

    // Fetch GitHub repositories using the provided GitHub Token
    public List<Repository> fetchGitHubRepositories(String githubToken) {
        String url = "https://api.github.com/user/repos";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Making the request to GitHub API
            ResponseEntity<Repository[]> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, Repository[].class);

            // If the response is successful, return the list of repositories
            if (response.getStatusCode().is2xxSuccessful()) {
                return Arrays.asList(response.getBody());
            } else {
                throw new RuntimeException("Failed to fetch repositories from GitHub: " + response.getStatusCode());
            }

        } catch (Exception e) {
            // Handle the error gracefully
            throw new RuntimeException("Failed to fetch repositories from GitHub", e);
        }
    }

    // Fetch user by userId
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));
        // Throw exception if user not found, or return the user
    }
}
*/package com.login.service;

import com.login.DTO.LoginRequest;
import com.login.DTO.LoginResponse;
import com.login.entity.GitHubRepository;
import com.login.entity.User;
import com.login.entity.UserType;
import com.login.exception.InvalidCredentialsException;
//import com.login.exception.UserNotFoundException;
import com.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Login method
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUserId(loginRequest.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            try {
                // Log the userType being passed from the request for debugging
                System.out.println("Requested userType: " + loginRequest.getUserType());

                // Convert the userType to uppercase and compare it with the enum constant
                UserType requestedUserType = UserType.valueOf(loginRequest.getUserType().toUpperCase());

                // Validate password and userType
                if (user.getPassword().equals(loginRequest.getPassword()) && user.getUserType().equals(requestedUserType)) {
                    return new LoginResponse(
                            "success",
                            "Login successful",
                            user.getGithubUserId(),
                            user.getGithubRepoName(),
                            user.getGithubAPIToken()
                    );
                } else {
                    throw new InvalidCredentialsException("Invalid credentials");
                }
            } catch (IllegalArgumentException e) {
                throw new InvalidCredentialsException("Invalid user type");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }




    // Register method
    public String register(User user) {
        // Check if user already exists
        Optional<User> existingUser = userRepository.findByUserId(user.getUserId());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists with userId: " + user.getUserId());
        }

        // Save the new user to the database
        userRepository.save(user);
        return "User registered successfully";
    }

    // Retrieve all students
    public List<User> getStudents() {
        return userRepository.findByUserType(UserType.STUDENT);
    }

    // Fetch GitHub repositories using the provided GitHub Token
    public List<String> fetchGitHubRepositories(String githubToken) {
        String url = "https://api.github.com/user/repos";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Making the request to GitHub API
            ResponseEntity<GitHubRepository[]> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, GitHubRepository[].class);

            // If the response is successful, return the list of repositories
            if (response.getStatusCode().is2xxSuccessful()) {
                return Arrays.stream(response.getBody())
                        .map(GitHubRepository::getName) // Extracts the name of each repository
                        .collect(Collectors.toList());
            } else {
                throw new RuntimeException("Failed to fetch repositories from GitHub: " + response.getStatusCode());
            }

        } catch (Exception e) {
            // Handle the error gracefully
            throw new RuntimeException("Failed to fetch repositories from GitHub", e);
        }
    }

    // Fetch user by userId
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.orElseThrow(() -> new UserNotFoundException("User not found with userId: " + userId));
    }
}
