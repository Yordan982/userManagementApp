package com.management.userManagement.controller;

import org.springframework.ui.Model;
import com.management.userManagement.dto.UserRegistrationDTO;
import com.management.userManagement.model.UserEntity;
import com.management.userManagement.repository.UserRepository;
import com.management.userManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller("/")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            boolean isSuccess = userService.register(registrationDTO);
            if (isSuccess) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A user with the same email exists.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<String> viewUser(@PathVariable String id) {
        try {
            Long userId = Long.parseLong(id);
            Optional<UserEntity> user = userRepository.findById(userId);
            return user.map(userEntity -> ResponseEntity.status(HttpStatus.OK).body(userEntity.toString())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id " + userId + " does not exist."));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format for user id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}