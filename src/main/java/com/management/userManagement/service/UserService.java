package com.management.userManagement.service;

import com.management.userManagement.dto.UserRegisterDTO;
import com.management.userManagement.dto.UserUpdateDTO;
import com.management.userManagement.model.UserEntity;
import com.management.userManagement.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegisterDTO user) {
        if (isEmailTaken(user.getEmail())) {
            throw new IllegalArgumentException("This email address is already used by another user");
        }
        save(user);
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id.intValue());
        } catch (Exception e) {
            throw new NoSuchElementException("There is no user with this id");
        }
    }

    public void update(Long id, UserUpdateDTO userUpdateDTO) {
        if (isIdPresent(id) && isEmailNotTakenByOtherUser(id, userUpdateDTO.getEmail())) {
            UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no user with this id"));
            userEntity.setFirstName(userUpdateDTO.getFirstName());
            userEntity.setLastName(userUpdateDTO.getLastName());
            userEntity.setDateOfBirth(userUpdateDTO.getDateOfBirth());
            userEntity.setEmail(userUpdateDTO.getEmail());
            userEntity.setPhoneNumber(userUpdateDTO.getPhoneNumber());
            userRepository.save(userEntity);
        } else {
            throw new IllegalArgumentException("This email address is already used by another user");
        }
    }

    public UserUpdateDTO getUser(Long id) {
        UserEntity userEntity = listId(id).orElseThrow(() -> new NoSuchElementException("User ID not found"));
        return this.modelMapper.map(userEntity, UserUpdateDTO.class);
    }


    public void save(UserRegisterDTO user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public Optional<UserEntity> listId(Long id) {
        return userRepository.findById(id);
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