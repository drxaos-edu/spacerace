package com.github.drxaos.edu.spacerace.graphics;

import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

/*
    Конкретная реализация фабрики спрайтов
 */
public class SpriteFactory implements ISpriteFactory {
    private final static int LAYER_DEFAULT = 1;
    private final static int LAYER_BG = 0;
    private final static int LAYER_STAR = LAYER_BG + 50;
    private final static int LAYER_OBJECTS = 500;
    private final static int LAYER_SHIP = LAYER_OBJECTS;
    private final static int LAYER_SHIP_TAIL = LAYER_SHIP - 100;
    private final static int LAYER_WALL = LAYER_OBJECTS;
    private final static int LAYER_UFO = LAYER_WALL + 50;

    private final Spriter spriter;
    private Spriter.Sprite tailPrototypeSprite;

    public SpriteFactory(Spriter spriter) {
        this.spriter = spriter;
        try {
            tailPrototypeSprite = getTailPrototypeSprite();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Spriter.Sprite getTailPrototypeSprite() throws IOException {
        return createSpriteProto("/tail.png", 41, 8, 0.4, 0.2, LAYER_SHIP_TAIL).setX(-0.2);
    }

    @Override
    public Spriter.Sprite getTailCloneSprite(Spriter.Sprite parent) throws IOException {
        return tailPrototypeSprite.clone().setParent(parent).setVisible(true);
    }

    @Override
    public Spriter.Sprite getStarPrototypeSprite() throws IOException {
        return createSpriteProto("/star.png", 50, 50, 0.5, 0.5, LAYER_STAR);
    }

    @Override
    public Spriter.Sprite getWallPrototypeSprite() throws IOException {
        return createSpriteProto("/meteor.png", 50, 50, 1, 1, LAYER_WALL);
    }

    @Override
    public Spriter.Sprite getUfoPrototypeSprite() throws IOException {
        return createSpriteProto("/ufo.png", 45, 45, 1, 1, LAYER_UFO);
    }

    @Override
    public Spriter.Sprite getLoadingSprite() throws IOException {
        return createSprite("/loading.png", 367 / 2, 62 / 2, 5, LAYER_DEFAULT);
    }

    @Override
    public Spriter.Sprite getGreenPlayerSprite() throws IOException {
        return createSprite("/player-green.png", 40, 50, 1, LAYER_SHIP);
    }

    @Override
    public Spriter.Sprite getRedPlayerSprite() throws IOException {
        return createSprite("/player-red.png", 40, 50, 1, LAYER_SHIP);
    }

    @Override
    public Spriter.Sprite getBackgroundPrototypeSprite() throws IOException {
        return createSpriteProto("/background.jpg", 512, 512, 25, 25, LAYER_BG);
    }

    @Override
    public Spriter.Sprite getTrgSprite() throws IOException {
        return createSprite("/point.png", 256 / 2, 256 / 2, 0.5, LAYER_DEFAULT);
    }

    private Spriter.Sprite createSpriteProto(final String resourcePath, int centerX, int centerY,
                                             double width, double height, int layer) throws IOException {
        BufferedImage image = SpriterUtils.loadImageFromResource(resourcePath);
        return spriter.createSpriteProto(image, centerX, centerY).setWidth(width).setHeight(height).setLayer(layer);
    }

    private Spriter.Sprite createSprite(final String resourcePath, int centerX, int centerY,
                                        double width, int layer) throws IOException {
        BufferedImage image = SpriterUtils.loadImageFromResource(resourcePath);
        return spriter.createSprite(image, centerX, centerY, width).setLayer(layer);
    }


    @Override
    public Spriter getSpriter() {
        return spriter;
    }
}
