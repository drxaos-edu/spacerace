package com.github.drxaos.edu.spacerace.physics;

import com.github.drxaos.edu.spacerace.bodies.PhysicsBody;
import com.github.drxaos.edu.spacerace.bodies.Spaceship;
import com.github.drxaos.edu.spacerace.bodies.Ufo;
import com.github.drxaos.edu.spacerace.bodies.Wall;

import java.util.List;

public class Collisions {
    private final static double VELOCITY_MULTIPLIER = 0.7;

    public void handle(Spaceship player,
                       Spaceship computer,
                       List<Ufo> ufos,
                       List<Wall> walls) {
        handleSpaceshipMeteorCollision(player, walls);
        handleSpaceshipUfoCollision(player, ufos);
        handleSpaceshipMeteorCollision(computer, walls);
        handleSpaceshipUfoCollision(computer, ufos);
        handlePlayerComputerCollision(player, computer);
    }

    private void handlePlayerComputerCollision(Spaceship player,
                                               Spaceship computer) {
        handleCollision(player, computer, VELOCITY_MULTIPLIER);
    }

    private void handleSpaceshipUfoCollision(Spaceship spaceship, List<Ufo> ufos) {
        // Столкновения корабля с НЛО
        for (Ufo ufo : ufos) {
            handleCollision(ufo, spaceship, VELOCITY_MULTIPLIER);
        }
    }

    private void handleSpaceshipMeteorCollision(Spaceship spaceship, List<Wall> walls) {
        // Столкновения корабля с астероидами
        for (Wall wall : walls) {
            handleCollision(wall, spaceship, VELOCITY_MULTIPLIER);
        }
    }

    private void handleCollision(PhysicsBody lhs, PhysicsBody rhs, double velocityMultiplier) {
        // Столкновения
        double dx = lhs.getX() - rhs.getX();
        double dy = lhs.getY() - rhs.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        // если расстояние <= 1, значит объекты столкнулись
        if (distance <= 1) {
            // расчет изменения скоростей шаров при соударении
            double angle = Math.atan2(dy, dx);
            double targetX = rhs.getX() + Math.cos(angle);
            double targetY = rhs.getY() + Math.sin(angle);
            double ax = (targetX - lhs.getX());
            double ay = (targetY - lhs.getY());
            // изменяются скорости
            rhs.decreaseVelocityX(ax);
            rhs.decreaseVelocityY(ay);
            lhs.increaseVelocityX(ax);
            lhs.increaseVelocityY(ay);
            // гасим скорости
            rhs.multiplyVelocityX(velocityMultiplier);
            rhs.multiplyVelocityY(velocityMultiplier);
            lhs.multiplyVelocityX(velocityMultiplier);
            lhs.multiplyVelocityY(velocityMultiplier);
        }
    }
}
