package com.management.userManagement.service;

import com.management.userManagement.dto.UserRegistrationDTO;
import com.management.userManagement.model.UserEntity;
import com.management.userManagement.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void register(UserRegistrationDTO user) {
        if (isEmailTaken(user.getEmail())) {
            throw new IllegalArgumentException("This email address is already used.");
        }
        userRepository.save(modelMapper.map(user, UserEntity.class));
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id.intValue());
        } catch (Exception e) {
            throw new NoSuchElementException("There is no user with this id");
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
            userRepository.save(userEntity);
            return true;
        } else {
            return false;
        }
    }

    public void save(UserRegistrationDTO user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userRepository.save(userEntity);
    }

    public List<UserEntity> listAll(String keyword) {
        List<UserEntity> users;

        if (keyword != null && !keyword.isEmpty()) {
            users = userRepository.findByKeyword(keyword);
        } else {
            users = userRepository.findAllByOrderByLastNameAscDateOfBirthAsc();
        }
        return users;
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