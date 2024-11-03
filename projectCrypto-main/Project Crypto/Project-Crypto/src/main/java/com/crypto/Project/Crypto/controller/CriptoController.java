package com.crypto.Project.Crypto.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.crypto.Project.Crypto.model.CriptoDTO;
import com.crypto.Project.Crypto.model.EncriptedData;
import com.crypto.Project.Crypto.repository.EncriptedDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CriptoController {

    @Value("${JWT_SECRET:my-secret-key}")
    private String secret;

    @Autowired
    private EncriptedDataRepository encriptedDataRepository; // Injetando o repositório

    @GetMapping("/cripto")
    public ModelAndView criptoPage(@RequestParam(required = false) String username) {
        ModelAndView modelAndView = new ModelAndView("cripto"); // Nome da view: cripto.html

        if (username != null && !username.isEmpty()) {
            // Busca os dados do usuário e os adiciona ao modelo se o username estiver presente
            List<EncriptedData> userData = encriptedDataRepository.findByUsername(username);
            modelAndView.addObject("userData", userData);
            modelAndView.addObject("username", username);

            // Lista para armazenar dados originais
            List<String> originalDataList = new ArrayList<>();

            for (EncriptedData data : userData) {
                try {
                    DecodedJWT decodedJWT = JWT.decode(data.getData()); // Decodifica o token
                    String originalData = decodedJWT.getClaim("data").asString(); // Obtém a claim "data"
                    originalDataList.add(originalData); // Adiciona à lista
                } catch (Exception e) {
                    modelAndView.addObject("errorMessage", "Erro ao decodificar os dados.");
                }
            }

            // Adiciona a lista de dados originais ao modelo
            modelAndView.addObject("originalDataList", originalDataList);
        }
        return modelAndView;
    }



    @PostMapping("/cripto")
    public ModelAndView criptografar(@ModelAttribute CriptoDTO data) {
        ModelAndView modelAndView = new ModelAndView("resultado");
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String crypted = JWT.create()
                    .withIssuer("encrypt-api")
                    .withSubject(data.username())
                    .withClaim("data", data.data())
                    .sign(algorithm);

            // Criando o objeto EncriptedData e salvando no banco
            EncriptedData encriptedData = new EncriptedData();
            encriptedData.setUsername(data.username());
            encriptedData.setData(crypted);
            encriptedDataRepository.save(encriptedData);

            modelAndView.addObject("token", crypted);
            return modelAndView;

        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }
}
