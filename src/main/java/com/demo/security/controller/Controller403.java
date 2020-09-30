package com.demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controller403 {

    @GetMapping("/403")
    public String AccessDeniedPage() {

        return "403";
    }
}
