package com.management.userManagement.repository;

import com.management.userManagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(Long id);
    List<UserEntity> findAllByOrderByLastNameAscDateOfBirthAsc();
}