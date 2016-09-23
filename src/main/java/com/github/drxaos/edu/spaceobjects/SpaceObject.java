package com.github.drxaos.edu.spaceobjects;

/**
 * Любой космический объект. Выделен в класс для объединения объектов с 
 * характеристикой - наличие координат в пространстве.
 */
public class SpaceObject
{
    protected double posX = 0;
    protected double posY = 0;
    
    /**
     * Устанавливает текущую позицию объекта
     * @param posX - позиция по оси X
     * @param posY - позиция по оси Y
     * @return текущий объект
     */
    public SpaceObject setPos(double posX, double posY)
    {
        this.posX = posX;
        this.posY = posY;
        return this;
    }
    
    public double getPosX()
    {
        return posX;
    }

    public double getPosY()
    {
        return posY;
    }
}
