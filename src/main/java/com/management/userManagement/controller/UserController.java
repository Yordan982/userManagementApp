package com.management.userManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class UserController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}