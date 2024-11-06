package com.crypto.Project.Crypto.service;

import com.crypto.Project.Crypto.model.EncriptedData;
import com.crypto.Project.Crypto.repository.EncriptedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EncriptedDataService {

    @Autowired
    private EncriptedDataRepository encriptedDataRepository;

    public List<EncriptedData> getUserEncriptedData(String username) {
         List<EncriptedData> data = encriptedDataRepository.findByUsername(username);
         return data;
    }
}
