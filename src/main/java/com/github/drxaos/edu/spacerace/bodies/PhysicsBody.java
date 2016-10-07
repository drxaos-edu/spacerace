package com.github.drxaos.edu.spacerace.bodies;

/*
    Любое тело, имеющее способность находиться в пространстве
 */
public abstract class PhysicsBody {
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    void increaseX(double dx) {
        this.x += dx;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    void increaseY(double dy) {
        this.y += dy;
    }

    double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void decreaseVelocityX(double dvx) {
        this.velocityX -= dvx;
    }

    public void increaseVelocityX(double dvx) {
        this.velocityX += dvx;
    }

    public void multiplyVelocityX(double dvx) {
        this.velocityX *= dvx;
    }

    double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void decreaseVelocityY(double dvy) {
        this.velocityY -= dvy;
    }

    public void increaseVelocityY(double dvy) {
        this.velocityY += dvy;
    }

    public void multiplyVelocityY(double dvy) {
        this.velocityY *= dvy;
    }

    void move() {
        // can be overridden
    }
}
