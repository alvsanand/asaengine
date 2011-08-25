package es.alvsanand.asaengine.math.util;

import es.alvsanand.asaengine.math.Circle;
import es.alvsanand.asaengine.math.Rectangle;
import es.alvsanand.asaengine.math.Sphere;
import es.alvsanand.asaengine.math.Vector2;
import es.alvsanand.asaengine.math.Vector3;

public class OverlapTester {
    public static boolean overlapCircles(Circle c1, Circle c2) {
        float distance = Vector2Utils.distSquared(c1.center,c2.center);
        float radiusSum = c1.radius + c2.radius;
        return distance <= radiusSum * radiusSum;
    }
    
    public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
        if(r1.lowerLeft.x < r2.lowerLeft.x + r2.width &&
           r1.lowerLeft.x + r1.width > r2.lowerLeft.x &&
           r1.lowerLeft.y < r2.lowerLeft.y + r2.height &&
           r1.lowerLeft.y + r1.height > r2.lowerLeft.y)
            return true;
        else
            return false;
    }
    
    public static boolean overlapCircleRectangle(Circle c, Rectangle r) {
        float closestX = c.center.x;
        float closestY = c.center.y;
        
        if(c.center.x < r.lowerLeft.x) {
            closestX = r.lowerLeft.x; 
        } 
        else if(c.center.x > r.lowerLeft.x + r.width) {
            closestX = r.lowerLeft.x + r.width;
        }
          
        if(c.center.y < r.lowerLeft.y) {
            closestY = r.lowerLeft.y;
        } 
        else if(c.center.y > r.lowerLeft.y + r.height) {
            closestY = r.lowerLeft.y + r.height;
        }
        
        return Vector2Utils.distSquared(c.center, closestX, closestY) < c.radius * c.radius;           
    }
    
	public static boolean overlapSpheres(Sphere s1, Sphere s2) {
		  float distance = Vector3Utils.distSquared(s1.center, s2.center);
	      float radiusSum = s1.radius + s2.radius;
	      return distance <= radiusSum * radiusSum;
	}
	
	public static boolean pointInSphere(Sphere c, Vector3 p) {
	    return Vector3Utils.distSquared(c.center,p) < c.radius * c.radius;
	}
	
	public static boolean pointInSphere(Sphere c, float x, float y, float z) {
	    return Vector3Utils.distSquared(c.center,x, y, z) < c.radius * c.radius;
	}
    
    public static boolean pointInCircle(Circle c, Vector2 p) {
        return Vector2Utils.distSquared(c.center, p) < c.radius * c.radius;
    }
    
    public static boolean pointInCircle(Circle c, float x, float y) {
        return Vector2Utils.distSquared(c.center, x, y) < c.radius * c.radius;
    }
    
    public static boolean pointInRectangle(Rectangle r, Vector2 p) {
        return r.lowerLeft.x <= p.x && r.lowerLeft.x + r.width >= p.x &&
               r.lowerLeft.y <= p.y && r.lowerLeft.y + r.height >= p.y;
    }
    
    public static boolean pointInRectangle(Rectangle r, float x, float y) {
        return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x &&
               r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;
    }
}