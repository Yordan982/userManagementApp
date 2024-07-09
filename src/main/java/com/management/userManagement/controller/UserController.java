package com.management.userManagement.controller;

import com.management.userManagement.dto.UserRegistrationDTO;
import com.management.userManagement.model.UserEntity;
import com.management.userManagement.repository.UserRepository;
import com.management.userManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @PutMapping("/user/{userId}/update")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult) {
        try {
            boolean isSuccess = userService.update(userId, userRegistrationDTO);

            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(String.join(", ", errors));
            }

            boolean isUpdated = userService.update(userId, userRegistrationDTO);
            if (isUpdated) {
                return ResponseEntity.ok("User updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id " + userId + " does not exist or the email is already taken.");
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

    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            Long userId = Long.parseLong(id);
            boolean isDeleted = userService.delete(userId);
            if (isDeleted) {
                userRepository.deleteById(Integer.valueOf(id));
                return ResponseEntity.status(HttpStatus.OK).body("User id: " + userId + " was deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id " + userId + " does not exist.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format for user id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}