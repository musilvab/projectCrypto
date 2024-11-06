package com.crypto.Project.Crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage(@RequestParam(defaultValue = "", required = false) String logout, Model model) {
        model.addAttribute("title", "Login");
        if (!logout.isEmpty()) {
            model.addAttribute("logoutMessage", "Você se desconectou com sucesso!");
        }
        return "auth/login";
    }
}
