package com.management.userManagement.controller;

import com.management.userManagement.dto.UserRegisterDTO;
import com.management.userManagement.dto.UserUpdateDTO;
import com.management.userManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {
    private final UserService userService;

    public UserAPIController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            long userId = Long.parseLong(id);
            if (userService.listId(userId).isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User id: " + id + " does not exist");
            }
            userService.delete(Long.valueOf((id)));
            return ResponseEntity.status(HttpStatus.OK).body("User id: " + userId + " was deleted successfully");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format for user id: " + id);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<String> getUser(@PathVariable String id) {
        try {
            long userId = Long.parseLong(id);
            if (userService.listId(userId).isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User id: " + id + " does not exist");
            }
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserText(userId).toString());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format for user id: " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<String> searchUsers() {
        return userService.responseListAll();
    }

    @GetMapping("/list/{keyword}")
    public ResponseEntity<String> searchUsersByKeyword(@PathVariable String keyword) {
        return userService.responseListAllKeyword(keyword);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @PathVariable String id) {
        try {
            long userId = Long.parseLong(id);
            if (userService.listId(userId).isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User id: " + id + " does not exist");
            }
            userService.update(Long.parseLong(id), userUpdateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User " + id + " updated successfully");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format for user id: " + id);
        }
    }
}