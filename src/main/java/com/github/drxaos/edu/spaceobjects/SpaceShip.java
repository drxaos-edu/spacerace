package com.github.drxaos.edu.spaceobjects;

import com.github.drxaos.spriter.Spriter;

/**
 * Космический корабль с отрисовкой. Расширяет MovableSpaceObject для объектов
 * имеющих вектор.
 */
public class SpaceShip extends MovableSpaceObject
{
    private Spriter.Sprite ship;
    private Spriter.Sprite tail;
    private double angle;

    /**
     * Конструктор
     * @param ship - объект отрисовки корабля
     */
    public SpaceShip(Spriter.Sprite ship) 
    {
        this.ship = ship;
        this.tail = null;
    }
    
    /**
     * Конструктор
     * @param ship - объект отрисовки корабля
     * @param tail - объект отрисовки пламени из двигателя
     */
    public SpaceShip(Spriter.Sprite ship, Spriter.Sprite tail) 
    {
        this.ship = ship;
        this.tail = tail;
    }
    
    @Override
    public SpaceShip setPos(double posX, double posY)
    {
        ship.setPos(posX, posY);
        super.setPos(posX, posY);
        return this;
    }
    
    /**
     * Показывает или скрывает плямя из двигателя корабля при его наличии
     * @param show - показать или нет
     * @return текущий объект
     */
    public SpaceShip showTail(boolean show)
    {
        if (tail != null)
            tail.setVisible(show);
        return this;
    }
    
    /**
     * Устанавливает угол направления корабля
     * @param angle - новое занчение
     * @return текущий объект
     */
    public SpaceShip setAngle(double angle)
    {
        ship.setAngle(angle);
        this.angle = angle;
        return this;
    }

    /**
     * Поворачивает корабль на указанное значение.
     * Минус - влево, Плюс - всправо
     * @param angle - значение поворота
     * @return текущий объект
     */
    public SpaceShip turn(double angle)
    {
        this.angle += angle;
        ship.setAngle(this.angle);
        return this;
    }
    
    public double getAngle()
    {
        return angle;
    }
}
