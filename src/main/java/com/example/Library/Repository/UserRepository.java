package com.example.Library.Repository;

import com.example.Library.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Object> findUserByFullName(String fullName);

    Optional<Object> findUserByEmail(String email);

    Users findUserByUserId(int userId);

}
