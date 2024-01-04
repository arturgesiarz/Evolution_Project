package oop.model;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;
    public Vector2d(int x,int y){
        this.x=x;
        this.y=y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Vector2d addVector(Vector2d other){  // funkcja dodaje wektory do siebie
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    @Override
    public String toString(){
        return "(%d,%d)".formatted(this.x,this.y);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
