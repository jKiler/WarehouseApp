package com.jerzykwiatkowski.warehouse.controller;

import com.jerzykwiatkowski.warehouse.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class HomeController {

    @Autowired
    LoginService loginService;

    @GetMapping("/")
    public String index(Model model) {

        Collection<?> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        model.addAttribute("name", loginService.getUserName());
        model.addAttribute("roles", roles);

        return "index";
    }
}
