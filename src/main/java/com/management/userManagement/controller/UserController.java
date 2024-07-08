package com.management.userManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/index")
public class UserController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}