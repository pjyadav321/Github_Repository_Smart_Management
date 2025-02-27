/*package com.login.config;  // You can put this in the 'config' package or wherever you find appropriate

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Allow CORS for any URL starting with "/api/"
                .allowedOrigins("http://localhost:3000") // Adjust frontend domain if needed
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow only specific HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true);  // Allow credentials (cookies, headers, etc.)
    }
}
*/