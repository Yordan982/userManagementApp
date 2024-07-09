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

    public boolean delete(Long id) {
        if (isIdPresent(id)) {
            userRepository.delete(userRepository.findById(id).get());
            return true;
        } else {
            return false;
        }
    }

    public boolean update(Long id, UserRegistrationDTO user) {
        if (isIdPresent(id) && isEmailNotTakenByOtherUser(id, user.getEmail())) {
            UserEntity userEntity = userRepository.findById(id).get();
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setDateOfBirth(user.getDateOfBirth());
            userEntity.setEmail(user.getEmail());
            userEntity.setPhoneNumber(user.getPhoneNumber());
            userEntity = userRepository.save(userEntity);
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isEmailNotTakenByOtherUser(Long id, String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isEmpty() || user.get().getId().equals(id);
    }

    private boolean isIdPresent(Long id) {
        return userRepository.findById(id).isPresent();
    }
}