package com.syncslide.service;

import com.syncslide.model.DemoState;
import com.syncslide.model.SlideState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlideServiceTest {

    private SlideService slideService;

    @BeforeEach
    void setUp() {
        slideService = new SlideService();
        // Use reflection or just test the logic directly
        // For testing, we'll set totalSlides via the init mechanism
    }

    @Test
    @DisplayName("getCurrentSlide returns default slide 1")
    void getCurrentSlide() {
        SlideState state = slideService.getCurrentSlide();
        assertEquals(1, state.getSlide());
    }

    @Test
    @DisplayName("setCurrentSlide clamps to valid range")
    void setCurrentSlideClamps() {
        // Slide service defaults totalSlides=11 when no files found
        slideService.init();

        SlideState result = slideService.setCurrentSlide(0);
        assertEquals(1, result.getSlide(), "Should clamp minimum to 1");

        result = slideService.setCurrentSlide(999);
        assertTrue(result.getSlide() <= slideService.getTotalSlides(), "Should clamp to max");
    }

    @Test
    @DisplayName("nextSlide increments")
    void nextSlide() {
        slideService.init();
        slideService.setCurrentSlide(1);

        SlideState result = slideService.nextSlide();
        assertEquals(2, result.getSlide());
    }

    @Test
    @DisplayName("previousSlide decrements")
    void previousSlide() {
        slideService.init();
        slideService.setCurrentSlide(3);

        SlideState result = slideService.previousSlide();
        assertEquals(2, result.getSlide());
    }

    @Test
    @DisplayName("previousSlide does not go below 1")
    void previousSlideMinimum() {
        slideService.init();
        slideService.setCurrentSlide(1);

        SlideState result = slideService.previousSlide();
        assertEquals(1, result.getSlide());
    }

    @Test
    @DisplayName("getDemoState returns default slides mode")
    void getDemoState() {
        DemoState state = slideService.getDemoState();
        assertEquals("slides", state.getMode());
    }

    @Test
    @DisplayName("setDemoState changes mode to URL")
    void setDemoStateUrl() {
        DemoState newState = new DemoState("url", 1, "/map");
        DemoState result = slideService.setDemoState(newState);
        assertEquals("url", result.getMode());
        assertEquals("/map", result.getUrl());
    }

    @Test
    @DisplayName("setDemoState slides mode clears URL")
    void setDemoStateSlides() {
        DemoState newState = new DemoState("slides", 1, null);
        DemoState result = slideService.setDemoState(newState);
        assertEquals("slides", result.getMode());
        assertNull(result.getUrl());
    }

    @Test
    @DisplayName("detectTotalSlides returns positive number")
    void detectTotalSlides() {
        slideService.init();
        assertTrue(slideService.getTotalSlides() > 0);
    }
}
