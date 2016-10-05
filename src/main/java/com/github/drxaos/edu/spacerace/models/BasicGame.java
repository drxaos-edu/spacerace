package com.github.drxaos.edu.spacerace.models;

import com.github.drxaos.spriter.Spriter;

import java.awt.*;

/**
 * Created by kotvaska on 04.10.2016.
 */
public class BasicGame {
    private Spriter spriter;

    public BasicGame(Spriter spriter) {
        this.spriter = spriter;
    }

    public Spriter getSpriter() {
        return spriter;
    }

    public void setSpriter(Spriter spriter) {
        this.spriter = spriter;
    }

    public void setWindowSize(int width, int height) {
        spriter.setViewportWidth(width);
        spriter.setViewportHeight(height);
    }

    public void startDraw(){
        spriter.beginFrame();
    }

    public void stopDraw() {
        spriter.endFrame();
    }

    public void pauseDraw() {
        spriter.pause();
    }

    public void playDraw() {
        spriter.unpause();
    }

    public void setBackground(Color background) {
        spriter.setBackgroundColor(Color.BLACK);
    }

    public void moveCamera(double x, double y) {
        spriter.setViewportShift(x, y);
    }
}
