package com.aledma.hackathonBEfinal.repository;

import com.aledma.hackathonBEfinal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    String findByEmail(String email);
    String findByPassword(String password);
}
