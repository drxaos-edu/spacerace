package com.github.drxaos.edu.sourcedata;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.github.drxaos.spriter.SpriterUtils;

public class ImageSource implements IImageSource
{
    public BufferedImage getPlayerA() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/player-green.png");
    }
    
    public BufferedImage getPlayerB() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/player-red.png");
    }
    
    public BufferedImage getTail() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/tail.png");
    }
    
    public BufferedImage getUfo() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/ufo.png");
    }
    
    public BufferedImage getStar() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/star.png");
    }
    
    public BufferedImage getMeteor() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/meteor.png");
    }
    
    public BufferedImage getMap() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/map.png");
    }
    
    public BufferedImage getLoading() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/loading.png");
    }
    
    public BufferedImage getPoint() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/point.png");
    }
    
    public BufferedImage getBackground() throws IOException
    {
        return SpriterUtils.loadImageFromResource("/background.jpg");
    }
}
