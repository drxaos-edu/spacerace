package com.github.drxaos.edu.spacerace.bodies;

import com.github.drxaos.edu.spacerace.maps.SpaceMap;
import com.github.drxaos.spriter.Spriter;

/*
    Космический корабль, который берёт управление на себя
 */
public class ComputerSpaceship extends Spaceship {
    private SpaceMap map;

    public ComputerSpaceship(Spriter.Sprite mainSprite, Spriter.Sprite tailSprite) {
        super(mainSprite, tailSprite);
    }

    public void setMap(SpaceMap map) {
        this.map = map;
    }

    public void move() {
        assert map != null;
        // Корабль компьютера ищет минимальный номер в прямой видимости
        int targetX = 0;
        int targetY = 0;
        int min = Integer.MAX_VALUE;
        // смотрим в разные стороны и ищем минимальный номер
        final int ANGLE_GAP = 18;
        for (int a = 0; a < ANGLE_GAP; a++) { // 18 шагов по 20 градусов
            double searchX = getX();
            double searchY = getY();
            while (true) {
                int sx = (int) Math.round(searchX);
                int sy = (int) Math.round(searchY);
                if (findWall(sx, sy)) {
                    break;
                }
                if (findNewMin(min, sx, sy)) {
                    min = map.get(sx, sy);
                    targetX = sx;
                    targetY = sy;
                }
                // продвигаемся в выбранном направлении
                searchX += Math.cos(Math.PI * 2 / ANGLE_GAP * a) * 0.3;
                searchY += Math.sin(Math.PI * 2 / ANGLE_GAP * a) * 0.3;
            }
        }

        move(targetX, targetY);
    }

    private void move(int targetX, int targetY) {
        // текущая скорость
        double computerCurrentVelocity = Math.sqrt(getVelocityX() * getVelocityX() + getVelocityY() * getVelocityY());
        double computerCurrentVelocityAngle = Math.atan2(getVelocityY(), getVelocityY());

        // азимут до цели
        double targetAngle = Math.atan2(targetY - getY(), targetX - getX());

        // поправка от текущего угла
        double angleShift = targetAngle - getAngle();

        // поправка от текущего угла скорости
        double velocityAngleShift = targetAngle - computerCurrentVelocityAngle;

        // нормализуем поправку
        while (angleShift > Math.PI) {
            angleShift -= Math.PI * 2;
        }
        while (angleShift < -Math.PI) {
            angleShift += Math.PI * 2;
        }

        if (angleShift > 0.1) {
            // направо
            increaseAngle(0.06);
        }
        if (angleShift < -0.1) {
            // налево
            decreaseAngle(0.06);
        }
        // ускоряемся, если скорость < 0.3 или если она направлена не на цель
        boolean shouldAccelerate = (computerCurrentVelocity < 0.3 || Math.cos(velocityAngleShift) < 0.6);
        if (Math.abs(angleShift) < Math.PI / 4 && shouldAccelerate) {
            // угол незначительный, можно включать ускорение
            increaseVelocityX(Math.cos(getAngle()) * 0.005);
            increaseVelocityY(Math.sin(getAngle()) * 0.005);
            setTailVisible(true);
        } else {
            // нет ускорения - нет шлейфа
            setTailVisible(false);
        }
        setAngle();

        // Двигаем корабль компьютера
        increaseX(getVelocityX());
        increaseY(getVelocityY());
        setPos();
    }

    private boolean findNewMin(int min, int sx, int sy) {
        return map.get(sx, sy) < min;
    }

    private boolean findWall(int sx, int sy) {
        return findNewMin(0, sx, sy);
    }

    public void decreaseGap(PlayerSpaceship player) {
        // Искусственно уменьшаем отрыв компьютера от игрока, чтобы было интереснее играть
        int playerStep = map.get((int) Math.round(player.getX()), (int) Math.round(player.getY()));
        int computerStep = map.get((int) Math.round(this.getX()), (int) Math.round(this.getY()));
        if (computerStep - playerStep > 20) {
            double deltaX = this.getX() - player.getX();
            double deltaY = this.getY() - player.getY();
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if (distance > 15) { // корабль компьютера сейчас не видно
                computerStep = playerStep + 20; // надо отставать не больше чем на 20 шагов
                // ищем подходящее место для телепортации
                for (int x = 1; x < 99; x++) {
                    for (int y = 1; y < 99; y++) {
                        if (map.get(x, y) == computerStep) {
                            deltaX = x - player.getX();
                            deltaY = y - player.getY();
                            distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                            if (distance > 15) { // только если игрок не видит эту клетку
                                this.setX(x);
                                this.setY(y);
                            }
                        }
                    }
                }
            }
        }
    }
}
