package com.github.drxaos.edu.spacerace.bodies;

import com.github.drxaos.spriter.Spriter;

/*
    НЛО
 */
public class Ufo extends PhysicsBody {
    private final Spriter.Sprite sprite;

    public Ufo(Spriter.Sprite sprite) {
        this.sprite = sprite;
    }

    private void setPos() {
        sprite.setPos(getX(), getY());
    }

    @Override
    public void move() {
        // Двигаем НЛО
        increaseX(getVelocityX());
        increaseY(getVelocityY());
        // гасим скорость НЛО, чтобы он быстрее остановился
        multiplyVelocityX(0.9);
        multiplyVelocityY(0.9);
        setPos();
    }
}
