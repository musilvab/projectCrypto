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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String login(@ModelAttribute AuthenticationDTO data, RedirectAttributes redirectAttributes) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        Authentication auth;

        try {
            auth = authenticationManager.authenticate(usernamePassword);
        } catch (BadCredentialsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Credenciais inválidas");
            return "redirect:/error/loginError";
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro de autenticação. Tente novamente.");
            return "redirect:/error/authError";
        }

        redirectAttributes.addFlashAttribute("message", "Seu login foi bem-sucedido!");
        redirectAttributes.addFlashAttribute("user", auth.getPrincipal());
        return "redirect:/home";
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
