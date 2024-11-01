package com.crypto.Project.Crypto.controller;

import com.crypto.Project.Crypto.model.User;
import com.crypto.Project.Crypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @RequestMapping("register")
    public String register() {
        return "register";
    }

    @RequestMapping("efetuaRegistro")
    public String registerUser(@RequestParam String username, @RequestParam String password){
        User user = new User(username, password);
        userService.save(user);
        return "index";
    }
}
