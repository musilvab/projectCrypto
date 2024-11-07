package com.crypto.Project.Crypto.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.crypto.Project.Crypto.model.EncriptedData;
import com.crypto.Project.Crypto.model.User;
import com.crypto.Project.Crypto.service.EncriptedDataService;
import com.crypto.Project.Crypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EncriptedDataService encriptedDataService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/encripted/{username}")
    public ResponseEntity<List<EncriptedData>> encriptedUser(@PathVariable String username) {
        List<EncriptedData> encriptedData = encriptedDataService.getUserEncriptedData(username);
        if (!encriptedData.isEmpty()) {
            return ResponseEntity.ok(encriptedData);
        }
        return null;
    }

    @GetMapping("/decripted/{username}")
    public ResponseEntity<List<String>> decriptedUser(@PathVariable String username) {
        List<EncriptedData> encriptedData = encriptedDataService.getUserEncriptedData(username);
        List<String> originalDataList = new ArrayList<>();
        for (EncriptedData data : encriptedData) {
            try {
                DecodedJWT decodedJWT = JWT.decode(data.getData()); // Decodifica o token
                String originalData = decodedJWT.getClaim("data").asString(); // Obtém a claim "data"
                originalDataList.add(originalData); // Adiciona à lista
            } catch (Exception e) {
                throw new Error("Erro ao descriptografar");
            }
        }
        return ResponseEntity.ok(originalDataList);
    }

}

