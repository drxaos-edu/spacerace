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
    private double width;
    private double height;
    private double x;
    private double y;

    public BaseSprite(String img) {
        this.imgName = img;
        layer = -1;
        imageCenterX = -1.0;
        imageCenterY = -1.0;
        objectWidth = -1.0;
        objectHeight = -1.0;
        width = -1.0;
        height = -1.0;
        x = -1.0;
        y = -1.0;
        setImg();
    }

    @Override
    public void add(Spriter spriter) {
        if (layer != -1) {
            sprite = spriter.createSprite(getImg(), imageCenterX, imageCenterY, objectWidth).setLayer(layer);
        } else {
            sprite = spriter.createSprite(getImg(), imageCenterX, imageCenterY, objectWidth);
        }
    }

    public void addProtoWithAllSetProperties(Spriter spriter) {
        sprite = spriter.createSpriteProto(getImg(), imageCenterX, imageCenterY);
        if (width != -1) sprite.setWidth(width);
        if (height != -1) sprite.setHeight(height);
        if (x != -1) sprite.setX(x);
        if (y != -1) sprite.setY(y);
        if (layer != -1) sprite.setLayer(layer);
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

    public void makeVisible(int x, int y) {
        sprite.createGhost().setPos(x, y).setVisible(true);
    }

    public void makeVisibleClone(int x, int y, double w) {
        sprite.clone().setPos(x, y).setWidthProportional(w).setVisible(true);
    }

    public Spriter.Sprite makeVisibleForParent(BaseSprite baseSprite) {
        return sprite.clone().setParent(baseSprite.getSprite()).setVisible(true);
    }

    public void setSprite(Spriter.Sprite sprite) {
        this.sprite = sprite;
    }

    public Spriter.Sprite getSprite() {
        return sprite;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setImageCenterX(double imageCenterX) {
        this.imageCenterX = imageCenterX;
    }

    public void setImageCenterY(double imageCenterY) {
        this.imageCenterY = imageCenterY;
    }

    public void setObjectWidth(double objectWidth) {
        this.objectWidth = objectWidth;
    }

    public void setObjectHeight(double objectHeight) {
        this.objectHeight = objectHeight;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
}
