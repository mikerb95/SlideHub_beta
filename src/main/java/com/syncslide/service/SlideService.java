package com.syncslide.service;

import com.syncslide.model.DemoState;
import com.syncslide.model.SlideState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SlideService {

    private static final Logger log = LoggerFactory.getLogger(SlideService.class);

    private final AtomicInteger currentSlide = new AtomicInteger(1);
    private final AtomicReference<DemoState> demoState = new AtomicReference<>(new DemoState());

    @Value("${syncslide.slides-dir:static/presentation}")
    private String slidesDir;

    private int totalSlides = 0;

    @PostConstruct
    public void init() {
        this.totalSlides = detectTotalSlides();
        log.info("Detected {} slides in presentation directory", totalSlides);
    }

    /**
     * Detect total number of slides by scanning the presentation directory.
     * Works both from filesystem and classpath (inside JAR).
     */
    public int detectTotalSlides() {
        // First, try classpath resource scanning (works inside JAR)
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:" + slidesDir + "/Slide*");
            int count = 0;
            for (Resource r : resources) {
                String filename = r.getFilename();
                if (filename != null && filename.matches("Slide\\d+\\.(png|PNG|jpg|jpeg|gif|svg)")) {
                    count++;
                }
            }
            if (count > 0) {
                return count;
            }
        } catch (IOException e) {
            log.debug("Could not scan classpath for slides: {}", e.getMessage());
        }

        // Fallback: try filesystem directly
        File dir = new File("src/main/resources/" + slidesDir);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.matches("Slide\\d+\\.(png|PNG|jpg|jpeg|gif|svg)"));
            return files != null ? files.length : 0;
        }

        log.warn("No slides found, defaulting to 11");
        return 11; // default fallback
    }

    public int getTotalSlides() {
        return totalSlides;
    }

    public SlideState getCurrentSlide() {
        return new SlideState(currentSlide.get());
    }

    public SlideState setCurrentSlide(int slide) {
        if (slide < 1)
            slide = 1;
        if (slide > totalSlides)
            slide = totalSlides;
        currentSlide.set(slide);
        return new SlideState(currentSlide.get());
    }

    public SlideState nextSlide() {
        int next = currentSlide.get() + 1;
        if (next > totalSlides)
            next = totalSlides;
        currentSlide.set(next);
        return new SlideState(currentSlide.get());
    }

    public SlideState previousSlide() {
        int prev = currentSlide.get() - 1;
        if (prev < 1)
            prev = 1;
        currentSlide.set(prev);
        return new SlideState(currentSlide.get());
    }

    public DemoState getDemoState() {
        DemoState state = demoState.get();
        // Always sync slide number
        state.setSlide(currentSlide.get());
        return state;
    }

    public DemoState setDemoState(DemoState newState) {
        if ("slides".equals(newState.getMode())) {
            newState.setSlide(currentSlide.get());
            newState.setUrl(null);
        }
        demoState.set(newState);
        return newState;
    }

    /**
     * Refresh slide count (useful after adding new slides at runtime)
     */
    public int refreshSlideCount() {
        this.totalSlides = detectTotalSlides();
        log.info("Refreshed slide count: {}", totalSlides);
        return totalSlides;
    }
}
