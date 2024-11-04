package com.crypto.Project.Crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/delete")
    public String deletePage(){
        return "delete";
    }
}
