package com.syncslide.model;

public class SlideState {

    private int slide;

    public SlideState() {
        this.slide = 1;
    }

    public SlideState(int slide) {
        this.slide = slide;
    }

    public int getSlide() {
        return slide;
    }

    public void setSlide(int slide) {
        this.slide = slide;
    }
}
