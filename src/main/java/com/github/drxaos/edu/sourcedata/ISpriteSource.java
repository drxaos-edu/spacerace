package com.github.drxaos.edu.sourcedata;

import java.io.IOException;
import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.Spriter.Sprite;

/**
 * Служит своего рода фабрикой готовых спрайтов. Выделен в интерфейс
 * для гибкости при необходимости изменения настрек готовых спрайтов
 */
public interface ISpriteSource
{
    public int[] getPixel(int posX, int posY) throws IOException;
    
    public Sprite getLoadingTitle(Spriter spriter) throws IOException;
    
    public Sprite getUfo(Spriter spriter) throws IOException;
    
    public Sprite getWall(Spriter spriter) throws IOException;
    
    public Sprite getStar(Spriter spriter) throws IOException;
    
    public Sprite getShipA(Spriter spriter) throws IOException;
    
    public Sprite getShipB(Spriter spriter) throws IOException;
    
    public Sprite getTail(Spriter spriter) throws IOException;
    
    public Sprite getBackground(Spriter spriter) throws IOException;
    
    public Spriter.Sprite getAttachedTail(Spriter.Sprite prototype, Spriter.Sprite parent);
}
