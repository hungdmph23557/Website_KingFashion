package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Home/")
public class LoginController {
    @GetMapping("login")
    public String Login(){
        return "login/login";
    }
}
