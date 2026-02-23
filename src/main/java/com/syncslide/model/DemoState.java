package com.syncslide.model;

public class DemoState {

    private String mode; // "slides" or "url"
    private int slide;
    private String url;

    public DemoState() {
        this.mode = "slides";
        this.slide = 1;
        this.url = null;
    }

    public DemoState(String mode, int slide, String url) {
        this.mode = mode;
        this.slide = slide;
        this.url = url;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getSlide() {
        return slide;
    }

    public void setSlide(int slide) {
        this.slide = slide;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
