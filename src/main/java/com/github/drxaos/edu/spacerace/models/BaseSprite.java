package com.github.drxaos.edu.spacerace.models;

import com.github.drxaos.edu.spacerace.controllers.Creator;
import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by kotvaska on 25.09.2016.
 */
public class BaseSprite implements Creator {
    private String imgName;
    private int layer;
    private BufferedImage img;
    private Spriter.Sprite sprite;
    private double imageCenterX;
    private double imageCenterY;
    private double objectWidth;
    private double objectHeight;

    public BaseSprite(String img, int layer, double imageCenterX, double imageCenterY,
                      double objectWidth, double objectHeight) {
        this.imgName = img;
        this.layer = layer;
        this.imageCenterX = imageCenterX;
        this.imageCenterY = imageCenterY;
        this.objectWidth = objectWidth;
        this.objectHeight = objectHeight;
        setImg();
    }

    @Override
    public void add(Spriter spriter) {
        sprite = spriter.createSprite(getImg(), imageCenterX, imageCenterY, objectWidth).setLayer(layer);
    }

    public void addProto(Spriter spriter) {
        sprite = spriter.createSpriteProto(getImg(), imageCenterX, imageCenterY)
                .setWidth(objectWidth)
                .setHeight(objectHeight)
                .setLayer(layer);
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void setImg() {
        try {
            img = SpriterUtils.loadImageFromResource(imgName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSprite(Spriter.Sprite sprite) {
        this.sprite = sprite;
    }

    public Spriter.Sprite getSprite() {
        return sprite;
    }
}
