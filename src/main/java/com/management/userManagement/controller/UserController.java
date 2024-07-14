package com.management.userManagement.controller;

import com.management.userManagement.dto.UserRegistrationDTO;
import com.management.userManagement.model.UserEntity;
import com.management.userManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.register(userRegistrationDTO);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "error.user", e.getMessage());
            return "register";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, Model model) {
        UserEntity userEntity = userService.listId(id).orElseThrow(() -> new NoSuchElementException("User ID not found"));
        UserRegistrationDTO userDto = new UserRegistrationDTO();
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setDateOfBirth(userEntity.getDateOfBirth());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());

        model.addAttribute("user", userDto);
        model.addAttribute("userId", id);
        return "edit-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") @Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", id);
            return "edit-user";
        }
        try {
            userService.update(id, userRegistrationDTO);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "error.user", e.getMessage());
            model.addAttribute("userId", id);
            return "edit-user";
        } catch (NoSuchElementException e) {
            bindingResult.rejectValue("id", "error.user", e.getMessage());
            model.addAttribute("userId", id);
            return "edit-user";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/id")
    public String viewUser(@RequestParam(name = "id", required = false) Long id, Model model) {
        Optional<UserEntity> userOptional = userService.listId(id);
        if (userOptional.isPresent()) {
            model.addAttribute("users", userOptional.get());
        } else {
            model.addAttribute("users", null);
        }
        return "list-users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String searchUsers(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<UserEntity> users = userService.listAll(keyword);
        model.addAttribute("users", users);
        model.addAttribute("search", keyword);
        return "list-users";
    }
}