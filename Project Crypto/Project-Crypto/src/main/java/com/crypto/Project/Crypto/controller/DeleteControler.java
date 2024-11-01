package com.crypto.Project.Crypto.controller;

import com.crypto.Project.Crypto.model.User;
import com.crypto.Project.Crypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteControler {
    @Autowired
    private UserService userService;

    @RequestMapping("delete")
    public String delete() {
        return "delete";
    }

    @RequestMapping("efetuaDelete")
    public String efetuaDelete(User user) {
        User search = userService.exists(user);
        if (search != null) {
            userService.deleteById(search.getId());
            return "index";
        }
        return "login-form";
    }
}
