package com.crypto.Project.Crypto.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String loginPage(@RequestParam(defaultValue = "", required = false) String logout, Model model) {
        model.addAttribute("title", "Login");
        if (!logout.equals("")) {
            model.addAttribute("logoutMessage", "Você se desconectou com sucesso!");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("title", "Register");
        return "auth/register";
    }

    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/delete")
    public String deletePage(Model model){
        model.addAttribute("title", "Delete");
        return "delete";
    }
}
