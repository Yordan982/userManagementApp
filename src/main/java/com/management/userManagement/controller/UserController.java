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
    public ResponseEntity<String> viewUser(@PathVariable Long id, Model model) {
        try {
            Optional<UserEntity> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                model.addAttribute("user", user);
                return ResponseEntity.status(HttpStatus.OK).body(user.toString());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The user with this id does not exist.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}