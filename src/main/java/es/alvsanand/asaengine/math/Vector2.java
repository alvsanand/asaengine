package es.alvsanand.asaengine.math;


public class Vector2 {
	public float x, y;

	public Vector2() {
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(Vector2 other) {
		this.x = other.x;
		this.y = other.y;
	}

	@Override
	public String toString() {
		return "Vector2 [x=" + x + ", y=" + y + "]";
	}
}