****GitHub Repository Smart Management***

**Overview**

GitHub Repository Smart Management is a Spring Boot-based application designed to manage user authentication and GitHub repository retrieval efficiently. 
The application provides secure login and registration functionalities while integrating with the GitHub API to fetch repository details.

**Features**
- User authentication (Login & Registration)
- Role-based user access (Students, Admins, etc.)
- Secure credential validation
- Fetch GitHub repositories using stored API tokens
- RESTful APIs for seamless integration
  
**Tech Stack**

Backend: Java, Spring Boot
Database: MySQL 
API Testing Tool: Postman
Version Control: GitHub
API Integration: GitHub API

**1. Login**

Endpoint: POST /api/users/login

**Request Body:**{
  "userId": "exampleUser",
  "password": "securepassword",
  "userType": "student"
}

**Response:**{
  "status": "success",
  "message": "Login successful",
  "githubUserId": "user123",
  "githubRepoName": "repoName",
  "githubAPIToken": "token"
}

**2. Register**

Endpoint: POST /api/users/register

**Request Body:**{
  "userId": "newUser",
  "password": "newPassword",
  "userType": "student",
  "GitHub_userId": "GitHubUser",
  "githubAPIToken": "token"
}
**Response:**"User registered successfully"

**Project Structure**
/src/main/java/com/login/
    ├── controller/        # Handles API requests (UserController, GitHubController)
    ├── service/           # Business logic (UserService)
    ├── entity/            # Data models (User, GitHubRepository, UserType)
    ├── repository/        # Database operations (UserRepository)
    ├── DTO/               # Data Transfer Objects (LoginRequest, LoginResponse)
    ├── exception/         # Custom exception handling
/src/main/resources/
    ├── application.properties  # Configuration for database and server
