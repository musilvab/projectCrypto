package com.crypto.Project.Crypto.repository;

import com.crypto.Project.Crypto.model.EncriptedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncriptedDataRepository extends JpaRepository<EncriptedData, Long> {
    List<EncriptedData> findByUsername(String username);
}
