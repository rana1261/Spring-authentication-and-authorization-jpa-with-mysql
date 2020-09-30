package com.demo.security.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthHomeController {
    @GetMapping("/authHome")
    public String authenticationHome() {

        return "authHome";
    }
}
