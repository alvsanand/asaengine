package es.alvsanand.asaengine.math;

public class Circle {
    public final Vector2 center;
    public float radius;

    public Circle(float x, float y, float radius) {
        this.center = new Vector2(x,y);
        this.radius = radius;
    }
}
