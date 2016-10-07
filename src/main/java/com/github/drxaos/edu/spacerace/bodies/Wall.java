package com.github.drxaos.edu.spacerace.bodies;

import com.github.drxaos.spriter.Spriter;

/*
    Стена
 */
public class Wall extends PhysicsBody {
    private final Spriter.Sprite sprite;

    public Wall(Spriter.Sprite sprite) {
        this.sprite = sprite;
    }
}
