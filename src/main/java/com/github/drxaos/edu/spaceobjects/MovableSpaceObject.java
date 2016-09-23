package com.github.drxaos.edu.spaceobjects;

/**
 * Любой объект который может двигаться. Расширяет SpaceObject для
 * объектов, имеющих скорость.
 */
public class MovableSpaceObject extends SpaceObject
{
    protected double velocityX;
    protected double velocityY;

    /**
     * Изменить скорость объекта на указанное число (плюс) по оси X и Y.
     * Если передаваемый параметр отрицательный, объект замедляется.
     * @param x - ускорение по оси X
     * @param y - ускорение по оси Y
     * @return текущий объект
     */
    public MovableSpaceObject ChangeVelocity(double x, double y)
    {
        this.velocityX += x;
        this.velocityY += y;
        return this;
    }
    
    /**
     * Изменить скорость объекта на указанное число (умножить) по оси X и Y.
     * @param x - ускорение по оси X
     * @param y - ускорение по оси Y
     * @return текущий объект
     */
    public MovableSpaceObject ChangeVelocityMulty(double x, double y)
    {
        this.velocityX *= x;
        this.velocityY *= y;
        return this;
    }
    
    /**
     * Остановить объект
     * @return текущий объект
     */
    public MovableSpaceObject stop()
    {
        this.velocityX = 0;
        this.velocityY = 0;
        return this;
    }
    
    public double getVelocityX()
    {
        return velocityX;
    }

    public double getVelocityY()
    {
        return velocityY;
    }
}
