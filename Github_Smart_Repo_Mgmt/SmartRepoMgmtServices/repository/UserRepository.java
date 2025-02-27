package com.login.repository;

import com.login.entity.User;
import com.login.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    List<User> findByUserType(String userType);
    List<User> findByUserType(UserType userType);

}
