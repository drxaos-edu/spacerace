package com.github.drxaos.edu.spacerace;

import java.awt.geom.Point2D;

import com.github.drxaos.edu.spaceobjects.*;

public class PhysicsLaw implements IPhysicsLaw
{
    public void moveMovableSpaceObjects(SpaceShip[] ufos, int ufoCounter)
    {
        for (int i = 0; i < ufoCounter; i++) {
              
            double posX = ufos[i].getPosX() + ufos[i].getVelocityX();
            double posY = ufos[i].getPosY() + ufos[i].getVelocityY();
            ufos[i].setPos(posX, posY);
            ufos[i].ChangeVelocityMulty(0.9, 0.9);
        }
    }
    
    public void impactMovableSpaceObjects(MovableSpaceObject shipA, MovableSpaceObject shipB)
    {
        double posXshipA = shipA.getPosX();
        double posYshipA = shipA.getPosY();
        double posXshipB = shipB.getPosX();
        double posYshipB = shipB.getPosY();
        
        double distance = getDistanceBetweenPoints(posXshipA, posYshipA, posXshipB, posYshipB);
        
        if (isImpacted(distance)) {
            
            Point2D.Double changedAngle = getAngleAfterImpact(
                    posXshipA, posYshipA, posXshipB, posYshipB);
            
            shipA.ChangeVelocity(changedAngle.x, changedAngle.y);
            shipB.ChangeVelocity(-changedAngle.x, -changedAngle.y);
            shipA.ChangeVelocityMulty(0.7, 0.7);
            shipB.ChangeVelocityMulty(0.7, 0.7);
        }
    }
    
    public void impactMovableSpaceObjects(
            MovableSpaceObject ship, MovableSpaceObject[] ufos, int ufoCounter)
    {
        double shipPosX = ship.getPosX();
        double shipPosY = ship.getPosY();
        
        for (int i = 0; i < ufoCounter; i++) {
            double ufoPosX = ufos[i].getPosX();
            double ufoPosY = ufos[i].getPosY();
            double distance = getDistanceBetweenPoints(ufoPosX, ufoPosY, shipPosX, shipPosY);
            
            if (isImpacted(distance)) {
                Point2D.Double changedAngle = getAngleAfterImpact(
                        ufoPosX, ufoPosY, shipPosX, shipPosY);
                
                ship.ChangeVelocity(-changedAngle.x, -changedAngle.y);
                ship.ChangeVelocityMulty(0.7, 0.7);
                ufos[i].ChangeVelocity(changedAngle.x, changedAngle.y);
            }
        }
    }
    
    public void impactMovableWithSpaceObject(
            MovableSpaceObject ship, SpaceObject[] asteroids, int asteroidCounter) 
    {
        double shipPosX = ship.getPosX();
        double shipPosY = ship.getPosY();
        
        for (int i = 0; i < asteroidCounter; i++) {
            double asterPosX = asteroids[i].getPosX();
            double asterPosY = asteroids[i].getPosY();
            double distance = getDistanceBetweenPoints(asterPosX, asterPosY, shipPosX, shipPosY);
            
            if (isImpacted(distance)) {
                Point2D.Double changedAngle = getAngleAfterImpact(
                        asterPosX, asterPosY, shipPosX, shipPosY);
                
                ship.ChangeVelocity(-changedAngle.x, -changedAngle.y);
                ship.ChangeVelocityMulty(0.7, 0.7);
            }
        }
    }
    
    private double getDistanceBetweenPoints(
            double ptOnePosX, double ptOnePosY, double ptTwoPosX, double ptTwoPosY)
    {
        double deltaX = ptOnePosX - ptTwoPosX;
        double deltaY = ptOnePosY - ptTwoPosY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance;
    }
    
    private boolean isImpacted(double distance)
    {
        return distance <= 1;
    }
    
    private Point2D.Double getAngleAfterImpact(
            double ptOnePosX, double ptOnePosY, double ptTwoPosX, double ptTwoPosY)
    {
        double dx = ptOnePosX - ptTwoPosX;
        double dy = ptOnePosY - ptTwoPosY;
        double angle = Math.atan2(dy, dx);
        double targetX = ptTwoPosX + Math.cos(angle);
        double targetY = ptTwoPosY + Math.sin(angle);
        double ax = (targetX - ptOnePosX);
        double ay = (targetY - ptOnePosY);
        
        return new Point2D.Double(ax, ay);
    }
}
