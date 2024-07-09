package com.management.userManagement.repository;

import com.management.userManagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(Long id);
    List<UserEntity> findAllByOrderByLastNameAscDateOfBirthAsc();

    @Query("SELECT u FROM UserEntity u WHERE u.firstName LIKE %:text% OR u.lastName LIKE %:text% OR u.phoneNumber LIKE %:text% OR u.email LIKE %:text% ORDER BY u.lastName, u.dateOfBirth")
    List<UserEntity> findByKeyword(@Param("text") String text);
}