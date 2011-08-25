package es.alvsanand.asaengine.math;

public class Sphere {
    public final Vector3 center;
    public float radius;
    
    public Sphere(float x, float y, float z, float radius) {
        this.center = new Vector3(x,y,z);
        this.radius = radius;
    }
}