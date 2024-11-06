package com.crypto.Project.Crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("title", "Register");
        return "auth/register";
    }

    @GetMapping("/delete")
    public String deletePage(Model model){
        model.addAttribute("title", "Delete");
        return "delete";
    }

    @GetMapping("/error/loginError")
    public String loginError(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute("errorMessage", "Credenciais inválidas");
        return "error/loginError";
    }

    @GetMapping("/error/authError")
    public String authError(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute("errorMessage", "Erro de autenticação. Tente novamente.");
        return "error/authError";
    }
}
