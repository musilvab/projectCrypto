package com.crypto.Project.Crypto.controller;

import com.crypto.Project.Crypto.model.User;
import com.crypto.Project.Crypto.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public String loginForm() {
        return "login-form";
    }

    @RequestMapping("error-login")
    public String errorLoginForm() {
        return "error-login-form";
    }

    @RequestMapping("efetuaLogin")
    public String efetuaLogin(User user, HttpSession session) {
        User search = userService.exists(user);
        if (search != null) {
            session.setAttribute("userLogged", user);
            return "index";
        }
        return "redirect:error-login";
    }
}
