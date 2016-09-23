package com.github.drxaos.edu.pilots;

import java.awt.Point;

import com.github.drxaos.edu.spaceobjects.SpaceShip;
import com.github.drxaos.spriter.Spriter;

/**
 * Унаследован от Pilot, поскольку суть действий одна, а ИИ
 * является частным случаем пилота
 */
public class AiPilot extends Pilot
{
    public AiPilot(SpaceShip ship, Spriter spriter)
    {
        super(ship, spriter);
    }
    
    public void tuneAutomaticRoute(int[][] route)
    {
        Point target = getTarget(route);
        
        // Цель компьютера (для отладки)
        //trg.setPos(target_x, target_y).setVisible(true);

        // азимут до цели
        double targetAngle = getTargetAngle(target);

        // поправка от текущего угла
        double angleShift = targetAngle - ship.getAngle();

        // поправка от текущего угла скорости
        double velocityAngleShift = getVelocityAngleShift(targetAngle);

        tuneAngle(angleShift, velocityAngleShift);
    }
    
    private Point getTarget(int[][] compRoute)
    {
        int targetX = 0;
        int targetY = 0;
        int min = Integer.MAX_VALUE;
        // смотрим в разные стороны и ищем минимальный номер
        for (int a = 0; a < 18; a++) { // 18 шагов по 20 градусов
            double searchX = ship.getPosX();
            double searchY = ship.getPosY();
            while (true) {
                int sx = (int) Math.round(searchX);
                int sy = (int) Math.round(searchY);
                if (compRoute[sx][sy] < 0) {
                    // стена, дельше не смотрим
                    break;
                }
                if (compRoute[sx][sy] < min) {
                    // запоминаем минимум
                    min = compRoute[sx][sy];
                    targetX = sx;
                    targetY = sy;
                }
                // продвигаемся в выбранном направлении
                searchX += Math.cos(Math.PI * 2 / 18 * a) * 0.3;
                searchY += Math.sin(Math.PI * 2 / 18 * a) * 0.3;
            }
        }
        return new Point(targetX, targetY);
    }
    
    private double getTargetAngle(Point target)
    {
        return Math.atan2(target.getY() - ship.getPosY(), target.getX() - ship.getPosX());
    }
    
    private double getVelocityAngleShift(double targetAngle)
    {
        double shipCurrentVelocityAngle = Math.atan2(ship.getVelocityY(), ship.getVelocityX());
        return targetAngle - shipCurrentVelocityAngle;
    }
    
    private void tuneAngle(double angleShift, double velocityAngleShift)
    {
        // нормализуем поправку
        while (angleShift > Math.PI) {
            angleShift -= Math.PI * 2;
        }
        while (angleShift < -Math.PI) {
            angleShift += Math.PI * 2;
        }

        if (angleShift > 0.1) {
            turnRight();
        }
        if (angleShift < -0.1) {
            turnLeft();
        }
        if (ShouldAccelerate(angleShift, velocityAngleShift)) {
            makeFaster();
            ship.showTail(true);
        } else {
            ship.showTail(false);
        }
    }

    private boolean ShouldAccelerate(double angleShift, double velocityAngleShift)
    {
        return Math.abs(angleShift) < Math.PI / 4
                && (getCurrentVelocity() < 0.3 || Math.cos(velocityAngleShift) < 0.6);
    }

    private double getCurrentVelocity()
    {
        double velocityX = ship.getVelocityX();
        double velocityY = ship.getVelocityY();
        return Math.sqrt(velocityX * velocityX + velocityY * velocityY);
    }
}
