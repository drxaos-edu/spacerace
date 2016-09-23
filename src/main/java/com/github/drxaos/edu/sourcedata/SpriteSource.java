package com.github.drxaos.edu.sourcedata;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.Spriter.Sprite;

public class SpriteSource implements ISpriteSource
{
    private IImageSource imageSource;
    
    // при наложении спрайты с меньшим номером слоя перекрываются спрайтами с большим номером слоя
    final static int
        LAYER_BG = 0,
        LAYER_STAR = LAYER_BG + 50,
        LAYER_OBJECTS = 500,
        LAYER_SHIP = LAYER_OBJECTS,
        LAYER_SHIP_TAIL = LAYER_SHIP - 100,
        LAYER_WALL = LAYER_OBJECTS,
        LAYER_UFO = LAYER_WALL + 50;

    public SpriteSource(IImageSource imageSource)
    {
        this.imageSource = imageSource;
    }
    
    public int[] getPixel(int posX, int posY) throws IOException
    {
        int[] pixel = new int[4]; // RGBA
        imageSource.getMap().getData().getPixel(posX, posY, pixel);
        return pixel;
    }
    
    public Sprite getLoadingTitle(Spriter spriter) throws IOException
    {
        double imageCenterX = 367 / 2; 
        double imageCenterY = 62 / 2; 
        double objectWidth = 5;
        
        return spriter.createSprite(imageSource.getLoading(), imageCenterX, imageCenterY, objectWidth);
    }
    
    public Sprite getUfo(Spriter spriter) throws IOException
    {
        double imageCenterX = 45;
        double imageCenterY = 45; 
        
        return spriter.createSpriteProto(imageSource.getUfo(), imageCenterX, imageCenterY)
                .setWidth(1).setHeight(1).setLayer(LAYER_UFO);
    }
    
    public Sprite getWall(Spriter spriter) throws IOException
    {
        double imageCenterX = 50;
        double imageCenterY = 50; 
        
        return spriter.createSpriteProto(imageSource.getMeteor(), imageCenterX, imageCenterY)
                .setWidth(1).setHeight(1).setLayer(LAYER_WALL);
    }
    
    public Sprite getStar(Spriter spriter) throws IOException
    {
        double imageCenterX = 50;
        double imageCenterY = 50; 
        
        return spriter.createSpriteProto(imageSource.getStar(), imageCenterX, imageCenterY)
                .setWidth(0.5).setHeight(0.5).setLayer(LAYER_STAR);
    }
    
    public Sprite getShipA(Spriter spriter) throws IOException
    {
        return getShip(spriter, imageSource.getPlayerA());
    }
    
    public Sprite getShipB(Spriter spriter) throws IOException
    {
        return getShip(spriter, imageSource.getPlayerB());
    }
    
    private Sprite getShip(Spriter spriter, BufferedImage image)
    {
        double imageCenterX = 40;
        double imageCenterY = 50; 
        double objectWidth = 1;
        
        return spriter.createSprite(image, imageCenterX, imageCenterY, objectWidth)
                .setLayer(LAYER_SHIP);
    }
    
    public Sprite getTail(Spriter spriter) throws IOException
    {
        double imageCenterX = 41;
        double imageCenterY = 8; 
        
        return spriter.createSpriteProto(imageSource.getTail(), imageCenterX, imageCenterY)
                .setWidth(0.4).setHeight(0.2).setX(-0.2).setLayer(LAYER_SHIP_TAIL);
    }
    
    public Sprite getBackground(Spriter spriter) throws IOException
    {
        double imageCenterX = 512;
        double imageCenterY = 512; 
        
        return spriter.createSpriteProto(imageSource.getBackground(), imageCenterX, imageCenterY)
                .setWidth(25).setHeight(25).setLayer(LAYER_BG);
    }
    
    public Spriter.Sprite getAttachedTail(Spriter.Sprite prototype, Spriter.Sprite parent)
    {
        // setParent закрепляет спрайт на другом спрайте и центром координат для него 
        // становится середина родительского
        return prototype.clone().setParent(parent).setVisible(true);
    }
}
