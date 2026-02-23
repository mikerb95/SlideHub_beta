package com.syncslide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowcaseController {

    @GetMapping("/")
    public String home() {
        return "redirect:/showcase";
    }

    @GetMapping("/showcase")
    public String showcase() {
        return "showcase";
    }
}
