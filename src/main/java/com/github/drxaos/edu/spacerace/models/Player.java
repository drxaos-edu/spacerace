package com.github.drxaos.edu.spacerace.models;

import static com.github.drxaos.edu.spacerace.models.Core.ai_map;

/**
 * Created by kotvaska on 06.10.2016.
 */
public class Player extends BaseSprite {
    // углы, координаты, скорости кораблей
    public double vx, vy, speed;

    public Player(String img) {
        super(img);
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void addSpeed(double acceleration) {
        this.speed += acceleration;
        sprite.setAngle(this.speed);
    }

    public void addAcceleration(double vx, double vy) {
        this.vx += vx;
        this.vy += vy;
    }

    public void multiplyAcceleration(double vx, double vy) {
        this.vx *= vx;
        this.vy *= vy;
    }

    public void move() {
        x += vx;
        y += vy;
        positionate();
    }

    public void overheadCompetitive(Player computer) {
        int player_step = ai_map[(int) Math.round(x)][(int) Math.round(y)];
        int computer_step = ai_map[(int) Math.round(computer.getX())][(int) Math.round(computer.getY())];
        if (computer_step - player_step > 20) {
            double deltaX = computer.getX() - x;
            double deltaY = computer.getY() - y;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if (distance > 15) { // корабль компьютера сейчас не видно
                computer_step = player_step + 20; // надо отставать не больше чем на 20 шагов
                // ищем подходящее место для телепортации
                for (int x = 1; x < 99; x++) {
                    for (int y = 1; y < 99; y++) {
                        if (ai_map[x][y] == computer_step) {
                            deltaX = x - x;
                            deltaY = y - y;
                            distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                            if (distance > 15) { // только если игрок не видит эту клетку
                                computer.setX(x);
                                computer.setY(y);
                            }
                        }
                    }
                }
            }
        }

    }

    public void collapse(BaseSprite baseSprite, double playerMulX, double playerMulY, int movable) {
        double deltaX = baseSprite.getX() - x;
        double deltaY = baseSprite.getY() - y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        // если расстояние <= 1, значит объекты столкнулись
        if (distance <= 1) {
            // расчет изменения скоростей шаров при соударении
            double dx = baseSprite.getX() - x;
            double dy = baseSprite.getY() - y;
            double angle = Math.atan2(dy, dx);
            double targetX = x + Math.cos(angle);
            double targetY = y + Math.sin(angle);
            double ax = (targetX - baseSprite.getX());
            double ay = (targetY - baseSprite.getY());
            // изменяется скорость только у игрока
            addAcceleration(-1 * ax, -1 * ay);
            // гасим скорость, чтобы игрока не уносило обратно
            multiplyAcceleration(playerMulX, playerMulY);
            // 0 - competitor is immovable, do nothing
            // 1 - competitor is movable, change competitor speed
            // 2 - competitor is movable, change and multiply speed
            switch (movable) {
                case 2:
                    ((Player) baseSprite).multiplyAcceleration(playerMulX, playerMulY);
                case 1:
                    ((Player) baseSprite).addAcceleration(ax, ay);
                    break;
                case 0:
                default:
                    break;
            }
        }
    }

}
