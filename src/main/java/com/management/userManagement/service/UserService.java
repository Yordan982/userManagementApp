package com.management.userManagement.service;

import com.management.userManagement.dto.UserRegistrationDTO;
import com.management.userManagement.model.UserEntity;
import com.management.userManagement.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public boolean register(UserRegistrationDTO user) {
        if (isEmailTaken(user.getEmail())) {
            return false;
        } else {
            UserEntity userEntity = modelMapper.map(user, UserEntity.class);
            userEntity = userRepository.save(userEntity);
            return true;
        }
    }

    private boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}