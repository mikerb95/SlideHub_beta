package com.syncslide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncslide.model.DemoState;
import com.syncslide.model.SlideState;
import com.syncslide.service.ConfigService;
import com.syncslide.service.SlideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.bean.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SlideService slideService;

    @MockBean
    private ConfigService configService;

    @BeforeEach
    void setUp() {
        when(slideService.getCurrentSlide()).thenReturn(new SlideState(1));
        when(slideService.getTotalSlides()).thenReturn(11);
        when(slideService.getDemoState()).thenReturn(new DemoState("slides", 1, null));
        when(configService.getPresenterNotes()).thenReturn(Map.of());
        when(configService.getDemoLinks()).thenReturn(Map.of());
    }

    @Test
    @DisplayName("GET /api/slide returns current slide")
    void getSlide() throws Exception {
        mockMvc.perform(get("/api/slide"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slide").value(1));
    }

    @Test
    @DisplayName("POST /api/slide changes slide")
    void setSlide() throws Exception {
        when(slideService.setCurrentSlide(anyInt())).thenReturn(new SlideState(5));

        mockMvc.perform(post("/api/slide")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"slide\": 5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slide").value(5));
    }

    @Test
    @DisplayName("POST /api/slide/next advances slide")
    void nextSlide() throws Exception {
        when(slideService.nextSlide()).thenReturn(new SlideState(2));

        mockMvc.perform(post("/api/slide/next"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slide").value(2));
    }

    @Test
    @DisplayName("POST /api/slide/prev goes back")
    void prevSlide() throws Exception {
        when(slideService.previousSlide()).thenReturn(new SlideState(1));

        mockMvc.perform(post("/api/slide/prev"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slide").value(1));
    }

    @Test
    @DisplayName("GET /api/demo returns demo state")
    void getDemo() throws Exception {
        mockMvc.perform(get("/api/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mode").value("slides"));
    }

    @Test
    @DisplayName("POST /api/demo changes demo mode")
    void setDemo() throws Exception {
        DemoState urlState = new DemoState("url", 1, "/map");
        when(slideService.setDemoState(org.mockito.ArgumentMatchers.any())).thenReturn(urlState);

        mockMvc.perform(post("/api/demo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mode\": \"url\", \"url\": \"/map\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mode").value("url"))
                .andExpect(jsonPath("$.url").value("/map"));
    }

    @Test
    @DisplayName("GET /api/config returns configuration")
    void getConfig() throws Exception {
        mockMvc.perform(get("/api/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSlides").value(11))
                .andExpect(jsonPath("$.presenterNotes").exists())
                .andExpect(jsonPath("$.demoLinks").exists());
    }
}
