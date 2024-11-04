package com.crypto.Project.Crypto.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "logout", required = false) String logout, Model model) {
        model.addAttribute("title", "Login");
        if (logout != null) {
            model.addAttribute("logoutMessage", "Você foi desconectado com sucesso!");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        System.out.println("ID da Sessão: " + session.getId());
        System.out.println("Usuário: " + session.getAttribute("SPRING_SECURITY_CONTEXT"));
        model.addAttribute("title", "Página Inicial");
        return "home";
    }

    @GetMapping("/delete")
    public String deletePage(){
        return "delete";
    }
}
