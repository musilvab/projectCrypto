package com.crypto.Project.Crypto.controller;

import com.crypto.Project.Crypto.infra.security.TokenService;
import com.crypto.Project.Crypto.model.AuthenticationDTO;
import com.crypto.Project.Crypto.model.RegisterDTO;
import com.crypto.Project.Crypto.model.User;
import com.crypto.Project.Crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        Authentication auth;

        try {
            auth = authenticationManager.authenticate(usernamePassword);
        } catch (BadCredentialsException e) {
            ModelAndView modelAndView = new ModelAndView("error/loginError");
            modelAndView.addObject("errorMessage", "Credenciais inválidas");
            return modelAndView;
        } catch (AuthenticationException e) {
            System.out.println("erro de autenticação");
            ModelAndView modelAndView = new ModelAndView("error/authError");
            modelAndView.addObject("errorMessage", "Erro de autenticação. Tente novamente.");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("message", "Seu login foi bem-sucedido!");
        modelAndView.addObject("user", auth.getPrincipal());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute RegisterDTO data) {
        if (repository.findByUsername(data.username()) != null) {
            ModelAndView modelAndView = new ModelAndView("error/registerError");
            modelAndView.addObject("errorMessage", "Nome de usuário já existe");
            return modelAndView;
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), encryptedPassword, data.role());

        repository.save(newUser);

        return new ModelAndView("home");
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute AuthenticationDTO data) {
        ModelAndView modelAndView = new ModelAndView("/success/successDelete");

        User userToDelete = repository.findUserByUsername(data.username());
        if (userToDelete != null) {
            repository.delete(userToDelete); // Deleta o usuário
            modelAndView.addObject("successMessage", "Usuário deletado com sucesso.");
        } else {
            modelAndView.addObject("errorMessage", "Usuário não encontrado.");
        }

        return modelAndView; // Retorna o ModelAndView
    }

}
