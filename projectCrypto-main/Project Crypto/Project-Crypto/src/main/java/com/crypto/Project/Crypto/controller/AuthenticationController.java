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
        } catch (DisabledException e) {
            ModelAndView modelAndView = new ModelAndView("error/accountDisabled");
            modelAndView.addObject("errorMessage", "Sua conta está desativada. Por favor, entre em contato com o suporte.");
            return modelAndView;
        } catch (AccountExpiredException e) {
            ModelAndView modelAndView = new ModelAndView("error/accountExpired");
            modelAndView.addObject("errorMessage", "Sua conta expirou. Por favor, entre em contato com o suporte.");
            return modelAndView;
        } catch (AuthenticationException e) {
            ModelAndView modelAndView = new ModelAndView("error/authError");
            modelAndView.addObject("errorMessage", "Erro de autenticação. Tente novamente.");
            return modelAndView;
        }

        var token = tokenService.generateToken((User) auth.getPrincipal());
        ModelAndView modelAndView;
        if (!token.isEmpty()) {
            modelAndView = new ModelAndView("home");
            modelAndView.addObject("token", token);
        } else {
            modelAndView = new ModelAndView("error/tokenError");
            modelAndView.addObject("errorMessage", "Erro ao gerar token. Tente novamente.");
        }
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

}
