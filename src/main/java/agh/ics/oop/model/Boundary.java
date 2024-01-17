package agh.ics.oop.model;

public record Boundary(Vector2d lowerLeft, Vector2d upperRight) {

    //gettery dodane dla czytelnosci
    public int getLeftX(){
        return this.lowerLeft().getX();
    }
    public int getRightX(){
        return this.upperRight().getX();
    }
    public int getXSpan(){
        return getRightX() - getLeftX();
    }

    public int getDownY(){
        return this.lowerLeft().getY();
    }
    public int getUpY(){
        return this.upperRight().getY();
    }
    public int getYSpan(){
        return getUpY() - getDownY();
    }
    public int getBoundarySize(){
        return getXSpan()*getYSpan();
    }
}
