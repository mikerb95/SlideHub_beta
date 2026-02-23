package com.syncslide.controller;

import com.syncslide.model.DemoState;
import com.syncslide.model.SlideState;
import com.syncslide.service.ConfigService;
import com.syncslide.service.SlideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final SlideService slideService;
    private final ConfigService configService;

    public ApiController(SlideService slideService, ConfigService configService) {
        this.slideService = slideService;
        this.configService = configService;
    }

    // ─── Slide State ─────────────────────────────────────────────

    @GetMapping("/slide")
    public ResponseEntity<SlideState> getSlide() {
        return ResponseEntity.ok(slideService.getCurrentSlide());
    }

    @PostMapping("/slide")
    public ResponseEntity<SlideState> setSlide(@RequestBody SlideState request) {
        SlideState result = slideService.setCurrentSlide(request.getSlide());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/slide/next")
    public ResponseEntity<SlideState> nextSlide() {
        return ResponseEntity.ok(slideService.nextSlide());
    }

    @PostMapping("/slide/prev")
    public ResponseEntity<SlideState> prevSlide() {
        return ResponseEntity.ok(slideService.previousSlide());
    }

    // ─── Demo State ──────────────────────────────────────────────

    @GetMapping("/demo")
    public ResponseEntity<DemoState> getDemo() {
        return ResponseEntity.ok(slideService.getDemoState());
    }

    @PostMapping("/demo")
    public ResponseEntity<DemoState> setDemo(@RequestBody DemoState request) {
        DemoState result = slideService.setDemoState(request);
        return ResponseEntity.ok(result);
    }

    // ─── Config ──────────────────────────────────────────────────

    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("totalSlides", slideService.getTotalSlides());
        config.put("presenterNotes", configService.getPresenterNotes());
        config.put("demoLinks", configService.getDemoLinks());
        return ResponseEntity.ok(config);
    }

    @PostMapping("/config/reload")
    public ResponseEntity<Map<String, String>> reloadConfig() {
        configService.reloadConfig();
        slideService.refreshSlideCount();
        return ResponseEntity.ok(Map.of("status", "ok", "message", "Configuration reloaded"));
    }
}
