package com.github.drxaos.edu.spacerace.graphics;

import com.github.drxaos.edu.spacerace.bodies.PlayerSpaceship;
import com.github.drxaos.spriter.Spriter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class UI {
    private static Spriter.Control control;
    private final Spriter spriter;
    private ISpriteFactory spriteFactory;
    private Spriter.Sprite loading;

    public UI(ISpriteFactory spriteFactory) {
        this.spriteFactory = spriteFactory;
        spriter = spriteFactory.getSpriter();
        control = spriter.getControl();
    }

    public void showLoadingScreen() throws IOException {
        spriter.setBackgroundColor(Color.BLACK);
        spriter.beginFrame();
        loading = spriteFactory.getLoadingSprite();
        spriter.endFrame();
    }

    public void hideLoadingScreen() {
        assert loading != null;
        loading.setVisible(false);
    }

    public void pauseDrawing() {
        spriter.pause();
    }

    public void unpauseDrawing() {
        spriter.unpause();
    }

    public void createBackground() throws IOException {
        Spriter.Sprite background = spriteFactory.getBackgroundPrototypeSprite();
        for (int x = 0; x <= 100; x += 25) {
            for (int y = 0; y <= 100; y += 25) {
                background.createGhost().setPos(x, y).setVisible(true);
            }
        }
    }

    public void beginFrame() {
        spriter.beginFrame();
    }

    public void endFrame() {
        spriter.endFrame();
    }

    public void handleKeyEvents(PlayerSpaceship player) {
        if (control.isKeyDown(KeyEvent.VK_LEFT)) {
            moveLeft(player);
        }
        if (control.isKeyDown(KeyEvent.VK_RIGHT)) {
            moveRight(player);
        }
        if (control.isKeyDown(KeyEvent.VK_UP)) {
            accelerate(player);
        } else {
            hideTail(player);
        }
    }

    private void hideTail(PlayerSpaceship player) {
        player.setTailVisible(false);
    }

    private void accelerate(PlayerSpaceship player) {
        player.increaseVelocityX(Math.cos(player.getAngle()) * 0.005);
        player.increaseVelocityY(Math.sin(player.getAngle()) * 0.005);
        player.setTailVisible(true);
    }

    private void moveRight(PlayerSpaceship player) {
        player.increaseAngle(0.06);
        player.setAngle();
    }

    private void moveLeft(PlayerSpaceship player) {
        player.decreaseAngle(0.06);
        player.setAngle();
    }

    public void moveViewport(PlayerSpaceship player) {
        spriter.setViewportShift(player.getX(), player.getY());
    }

    public void setViewport(int viewportWidth, int viewportHeight) {
        spriter.setViewportWidth(viewportWidth);
        spriter.setViewportHeight(viewportHeight);
    }


}
