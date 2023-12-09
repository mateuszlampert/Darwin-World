package agh.ics.oop.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString(){
        return String.format("(%d,%d)", this.x, this.y);
    }

    public boolean precedes(Vector2d other){
        return this.getX() <= other.getX() && this.getY() <= other.getY();
    }
    public boolean follows(Vector2d other){
        return this.getX() >= other.getX() && this.getY() >= other.getY();
    }
    public Vector2d add(Vector2d other){
        int newX = this.getX() + other.getX();
        int newY = this.getY() + other.getY();
        return new Vector2d(newX, newY);
    }
    public Vector2d subtract(Vector2d other){
        int newX = this.getX() - other.getX();
        int newY = this.getY() - other.getY();
        return new Vector2d(newX, newY);
    }
    public Vector2d upperRight(Vector2d other){
        int newX = this.getX() > other.getX() ? this.getX() : other.getX();
        int newY = this.getY() > other.getY() ? this.getY() : other.getY();
        return new Vector2d(newX, newY);
    }

    public Vector2d lowerLeft(Vector2d other){
        int newX = this.getX() < other.getX() ? this.getX() : other.getX();
        int newY = this.getY() < other.getY() ? this.getY() : other.getY();
        return new Vector2d(newX, newY);
    }

    public Vector2d opposite(){
        int newX = this.getX() * -1;
        int newY = this.getY() * -1;
        return new Vector2d(newX, newY);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.getX() == that.getX() && this.getY() == that.getY();
    }

    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
