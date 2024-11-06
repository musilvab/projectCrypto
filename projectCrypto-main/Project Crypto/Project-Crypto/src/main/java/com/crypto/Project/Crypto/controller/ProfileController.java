package com.crypto.Project.Crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("/{username}")
    public String profile(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("title", "Profile");
        return "profile";
    }
}
