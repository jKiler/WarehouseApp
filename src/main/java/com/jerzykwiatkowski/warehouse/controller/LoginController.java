package com.jerzykwiatkowski.warehouse.controller;

import com.jerzykwiatkowski.warehouse.repository.RoleRepository;
import com.jerzykwiatkowski.warehouse.repository.UserRepository;
import com.jerzykwiatkowski.warehouse.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("urls", loginService.getOauth2AuthenticationUrls());
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(@AuthenticationPrincipal User customUser) {
        return "this is user id " + customUser;
    }

}
