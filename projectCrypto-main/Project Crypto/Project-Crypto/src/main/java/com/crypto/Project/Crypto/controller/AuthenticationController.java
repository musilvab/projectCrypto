package com.crypto.Project.Crypto.controller;

import com.crypto.Project.Crypto.infra.security.TokenService;
import com.crypto.Project.Crypto.model.AuthenticationDTO;
import com.crypto.Project.Crypto.model.RegisterDTO;
import com.crypto.Project.Crypto.model.User;
import com.crypto.Project.Crypto.repository.UserRepository;
import com.crypto.Project.Crypto.service.AuthorizationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute AuthenticationDTO data, HttpServletResponse response){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        Authentication auth;

        try {
            auth = authenticationManager.authenticate(usernamePassword);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro de autenticação. Tente novamente.");
        }

        String token = tokenService.generateToken(auth);
        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true);  // Impede o acesso via JavaScript
        jwtCookie.setPath("/");  // O cookie é válido para todo o site
        jwtCookie.setMaxAge(60 * 60); // O cookie expira após 1 hora
        response.addCookie(jwtCookie);
        return ResponseEntity.ok("Login bem sucedido!");
    }
//
////    @PostMapping("/login")
////    public String login(@ModelAttribute AuthenticationDTO data, HttpServletResponse response, RedirectAttributes redirectAttributes) {
////        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
////        Authentication auth;
////
////        try {
////            auth = authenticationManager.authenticate(usernamePassword);
////        } catch (BadCredentialsException e) {
////            return "redirect:/error/loginError";
////        } catch (AuthenticationException e) {
////            return "redirect:/error/authError";
////        }
////
////        String token = tokenService.generateToken(auth);
////
////        redirectAttributes.addFlashAttribute("token", token);
////        redirectAttributes.addFlashAttribute("user", auth.getPrincipal());
////        return "redirect:/home";
////    }
//
//
//    @PostMapping("/register")
//    public ModelAndView register(@ModelAttribute RegisterDTO data) {
//        if (repository.findByUsername(data.username()) != null) {
//            ModelAndView modelAndView = new ModelAndView("error/registerError");
//            modelAndView.addObject("errorMessage", "Nome de usuário já existe");
//            return modelAndView;
//        }
//
//        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
//        User newUser = new User(data.username(), encryptedPassword, data.role());
//
//        repository.save(newUser);
//
//        return new ModelAndView("home");
//    }
//
//    @PostMapping("/delete")
//    public ModelAndView delete(@ModelAttribute AuthenticationDTO data) {
//        ModelAndView modelAndView = new ModelAndView("/success/successDelete");
//
//        User userToDelete = repository.findUserByUsername(data.username());
//        if (userToDelete != null) {
//            repository.delete(userToDelete); // Deleta o usuário
//            modelAndView.addObject("successMessage", "Usuário deletado com sucesso.");
//        } else {
//            modelAndView.addObject("errorMessage", "Usuário não encontrado.");
//        }
//
//        return modelAndView; // Retorna o ModelAndView
//    }
//
}
