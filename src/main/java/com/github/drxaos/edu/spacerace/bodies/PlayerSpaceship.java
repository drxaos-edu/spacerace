package com.github.drxaos.edu.spacerace.bodies;

import com.github.drxaos.spriter.Spriter;

/*
    Корабль игрока, управляется извне
 */
public class PlayerSpaceship extends Spaceship {
    public PlayerSpaceship(Spriter.Sprite mainSprite, Spriter.Sprite tailSprite) {
        super(mainSprite, tailSprite);
    }

    @Override
    public void move() {
        increaseX(getVelocityX());
        increaseY(getVelocityY());
        setPos();
    }
}
