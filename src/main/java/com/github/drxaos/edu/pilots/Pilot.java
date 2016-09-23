package com.github.drxaos.edu.pilots;

import java.awt.event.KeyEvent;

import com.github.drxaos.edu.spaceobjects.SpaceShip;
import com.github.drxaos.spriter.Spriter;;

/**
 * Класс выделен для представления объекта отвечающего
 * за упраление кораблем в пространстве
 */
public class Pilot
{
    protected SpaceShip ship;
    
    // Объект для считывания клавиш, нажимаемых пользователем
    protected Spriter.Control control; 
        
    public Pilot(SpaceShip ship, Spriter spriter)
    {
        this.ship = ship;
        control = spriter.getControl();
    }
    
    public void moveShip()
    {
        double posX = ship.getPosX() + ship.getVelocityX();
        double posY = ship.getPosY() + ship.getVelocityY();
        ship.setPos(posX, posY);
    }
    
    protected void turnLeft()
    {
        ship.turn(-0.06);
    }
    
    protected void turnRight()
    {
        ship.turn(0.06);
    }
    
    protected void makeFaster()
    {
        double angle = ship.getAngle();
        double attitudeX = Math.cos(angle) * 0.005;
        double attitudeY = Math.sin(angle) * 0.005;
        ship.ChangeVelocity(attitudeX, attitudeY);
    }
    
    public void checkControl()
    {
        if (control.isKeyDown(KeyEvent.VK_LEFT)) {
            turnLeft();
        }
        if (control.isKeyDown(KeyEvent.VK_RIGHT)) {
            turnRight();
        }
        if (control.isKeyDown(KeyEvent.VK_UP)) {
            makeFaster();
            ship.showTail(true);
        } else {
            ship.showTail(false);
        }
    }
}
