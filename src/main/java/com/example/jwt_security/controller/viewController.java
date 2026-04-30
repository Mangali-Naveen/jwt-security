package com.example.jwt_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class viewController {

    @GetMapping("/")
    public String home() {
        return "redirect:/index.html";
    }

}
