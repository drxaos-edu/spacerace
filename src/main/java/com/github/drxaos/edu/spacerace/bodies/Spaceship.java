package com.github.drxaos.edu.spacerace.bodies;

import com.github.drxaos.spriter.Spriter;

/*
    Космический корабль, он еще и угол поворота имеет
 */
public abstract class Spaceship extends PhysicsBody {
    private final Spriter.Sprite mainSprite;
    private final Spriter.Sprite tailSprite;
    private double angle;

    Spaceship(Spriter.Sprite mainSprite, Spriter.Sprite tailSprite) {
        this.mainSprite = mainSprite;
        this.tailSprite = tailSprite;
    }

    private Spriter.Sprite getMainSprite() {
        return mainSprite;
    }

    private Spriter.Sprite getTailSprite() {
        return tailSprite;
    }

    public void setTailVisible(boolean visible) {
        getTailSprite().setVisible(visible);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setAngle() {
        getMainSprite().setAngle(getAngle());
    }

    public void increaseAngle(double dax) {
        this.angle += dax;
    }

    public void decreaseAngle(double dax) {
        this.angle -= dax;
    }

    public void setPos() {
        getMainSprite().setPos(getX(), getY());
    }

    @Override
    abstract void move();
}
