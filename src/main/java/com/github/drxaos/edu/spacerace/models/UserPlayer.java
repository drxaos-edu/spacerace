package com.github.drxaos.edu.spacerace.models;

import com.github.drxaos.edu.spacerace.controllers.Creator;
import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by kotvaska on 25.09.2016.
 */
public class UserPlayer implements Creator {
    private String imgName;
    private int layer;
    private BufferedImage img;
    private Spriter.Sprite sprite;

    public UserPlayer(String img, int layer) {
        this.imgName = img;
        this.layer = layer;
        setImg();
    }

    @Override
    public void add(Spriter spriter) {
        sprite = spriter.createSprite(getImg(), 40, 50, 1).setLayer(layer);
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
