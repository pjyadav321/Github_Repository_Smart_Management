package com.login.controller;

import com.login.DTO.LoginRequest;
import com.login.DTO.LoginResponse;
import com.login.entity.User;
import com.login.entity.UserType;
import com.login.exception.InvalidCredentialsException;
import com.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    // Login API
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
    {
        try
        {
            // Get the response from the login service (LoginResponse object)
            LoginResponse loginResponse = userService.login(loginRequest);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }
        catch (InvalidCredentialsException e)
        {
            // If invalid credentials, return Unauthorized with the message
            return new ResponseEntity<>(new LoginResponse("failure", e.getMessage(), null, null, null), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e)
        {
            // General error response
            return new ResponseEntity<>(new LoginResponse("failure", "Error during login: " + e.getMessage(), null, null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Register API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            String response = userService.register(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error during registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch list of all students
    @GetMapping("/students")
    public ResponseEntity<List<User>> getAllStudents() {
        try {
            List<User> students = userService.getStudents(); // Service to fetch students
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch repositories of a user by userId
    @GetMapping("/repositories/{userId}")
    public ResponseEntity<?> getRepositories(@PathVariable String userId) {
        try {
            // Fetch user from the database using userId
            User user = userService.getUserById(userId);

            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            // Get GitHub token for the user
            String githubToken = user.getGithubAPIToken(); // Assuming you store the token

            // Fetch repositories using the GitHub token
            List<String> repositories = userService.fetchGitHubRepositories(githubToken);

            return new ResponseEntity<>(repositories, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Error fetching repositories", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
