package com.crypto.Project.Crypto.repository;

import com.crypto.Project.Crypto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
}
