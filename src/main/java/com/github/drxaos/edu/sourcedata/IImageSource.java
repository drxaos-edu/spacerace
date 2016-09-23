package com.github.drxaos.edu.sourcedata;

import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * Предназначин для связи с объектами на диске.
 * Выделен в интерфейс для гибкости при изменнении изображений объектов
 */
public interface IImageSource
{
    public BufferedImage getPlayerA() throws IOException;
    
    public BufferedImage getPlayerB() throws IOException;
    
    public BufferedImage getTail() throws IOException;
    
    public BufferedImage getUfo() throws IOException;
    
    public BufferedImage getStar() throws IOException;
    
    public BufferedImage getMeteor() throws IOException;
    
    public BufferedImage getMap() throws IOException;
    
    public BufferedImage getLoading() throws IOException;
    
    public BufferedImage getPoint() throws IOException;
    
    public BufferedImage getBackground() throws IOException;
}
