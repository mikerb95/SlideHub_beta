package com.syncslide.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

@Service
public class ConfigService {

    private static final Logger log = LoggerFactory.getLogger(ConfigService.class);

    private final ObjectMapper objectMapper;

    @Value("${syncslide.presenter-notes-path:config/presenter-notes.json}")
    private String presenterNotesPath;

    @Value("${syncslide.demo-links-path:config/demo-links.json}")
    private String demoLinksPath;

    private Map<String, Object> presenterNotes = Collections.emptyMap();
    private Map<String, Object> demoLinks = Collections.emptyMap();

    public ConfigService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        loadPresenterNotes();
        loadDemoLinks();
    }

    private void loadPresenterNotes() {
        try {
            ClassPathResource resource = new ClassPathResource(presenterNotesPath);
            try (InputStream is = resource.getInputStream()) {
                presenterNotes = objectMapper.readValue(is, new TypeReference<>() {
                });
                log.info("Loaded presenter notes for {} slides", presenterNotes.size());
            }
        } catch (IOException e) {
            log.warn("Could not load presenter notes from {}: {}", presenterNotesPath, e.getMessage());
        }
    }

    private void loadDemoLinks() {
        try {
            ClassPathResource resource = new ClassPathResource(demoLinksPath);
            try (InputStream is = resource.getInputStream()) {
                demoLinks = objectMapper.readValue(is, new TypeReference<>() {
                });
                log.info("Loaded demo links configuration");
            }
        } catch (IOException e) {
            log.warn("Could not load demo links from {}: {}", demoLinksPath, e.getMessage());
        }
    }

    public Map<String, Object> getPresenterNotes() {
        return presenterNotes;
    }

    public Map<String, Object> getDemoLinks() {
        return demoLinks;
    }

    /**
     * Reload config files at runtime
     */
    public void reloadConfig() {
        loadPresenterNotes();
        loadDemoLinks();
        log.info("Configuration reloaded successfully");
    }
}
