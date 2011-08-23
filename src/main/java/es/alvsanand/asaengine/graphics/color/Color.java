package es.alvsanand.asaengine.graphics.color;

public class Color {
	public float r, g, b, a;
	
	public Color(float r, float g, float b, float a) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public float[] toArray(){
		return new float[]{r, g, b, a};
	}
}
