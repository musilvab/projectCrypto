package com.crypto.Project.Crypto.repository;

import com.crypto.Project.Crypto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository <User, Long> {
    UserDetails findByUsername(String username);
}
