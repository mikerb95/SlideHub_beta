package com.syncslide.controller;

import com.syncslide.service.SlideService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PresentationController {

    private final SlideService slideService;

    public PresentationController(SlideService slideService) {
        this.slideService = slideService;
    }

    @GetMapping("/slides")
    public String slides(Model model) {
        model.addAttribute("totalSlides", slideService.getTotalSlides());
        return "slides";
    }

    @GetMapping("/remote")
    public String remote(Model model) {
        model.addAttribute("totalSlides", slideService.getTotalSlides());
        return "remote";
    }

    @GetMapping("/presenter")
    public String presenter(Model model) {
        model.addAttribute("totalSlides", slideService.getTotalSlides());
        return "presenter";
    }

    @GetMapping("/main-panel")
    public String mainPanel(Model model) {
        model.addAttribute("totalSlides", slideService.getTotalSlides());
        return "main-panel";
    }

    @GetMapping("/demo")
    public String demo(Model model) {
        model.addAttribute("totalSlides", slideService.getTotalSlides());
        return "demo";
    }
}
