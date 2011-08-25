package es.alvsanand.asaengine.math.util;

import android.util.FloatMath;
import es.alvsanand.asaengine.math.Vector2;

public class Vector2Utils {
	public static float TO_RADIANS = (1 / 180.0f) * (float) Math.PI;
	public static float TO_DEGREES = (1 / (float) Math.PI) * 180;

	public static Vector2 cpy(Vector2 in) {
		return new Vector2(in.x, in.y);
	}

	public static Vector2 set(Vector2 inTmp, float x, float y) {
		Vector2 in = cpy(inTmp);

		in.x = x;
		in.y = y;
		return in;
	}

	public static Vector2 set(Vector2 inTmp, Vector2 other) {
		Vector2 in = cpy(inTmp);
		in.x = other.x;
		in.y = other.y;
		return in;
	}

	public static Vector2 add(Vector2 inTmp, float x, float y) {
		Vector2 in = cpy(inTmp);
		in.x += x;
		in.y += y;
		return in;
	}

	public static Vector2 add(Vector2 inTmp, Vector2 other) {
		Vector2 in = cpy(inTmp);
		in.x += other.x;
		in.y += other.y;
		return in;
	}

	public static Vector2 sub(Vector2 inTmp, float x, float y) {
		Vector2 in = cpy(inTmp);
		in.x -= x;
		in.y -= y;
		return in;
	}

	public static Vector2 sub(Vector2 inTmp, Vector2 other) {
		Vector2 in = cpy(inTmp);
		in.x -= other.x;
		in.y -= other.y;
		return in;
	}

	public static Vector2 mul(Vector2 inTmp, float scalar) {
		Vector2 in = cpy(inTmp);
		in.x *= scalar;
		in.y *= scalar;
		return in;
	}

	public static float len(Vector2 in) {
		return FloatMath.sqrt(in.x * in.x + in.y * in.y);
	}

	public static Vector2 nor(Vector2 inTmp) {
		Vector2 in = cpy(inTmp);
		float len = len(in);
		if (len != 0) {
			in.x /= len;
			in.y /= len;
		}
		return in;
	}

	public static float angle(Vector2 in) {
		float angle = (float) Math.atan2(in.y, in.x) * TO_DEGREES;
		if (angle < 0)
			angle += 360;
		return angle;
	}

	public static Vector2 rotate(Vector2 inTmp, float angle) {
		Vector2 in = cpy(inTmp);

		float rad = angle * TO_RADIANS;
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);

		float newX = in.x * cos - in.y * sin;
		float newY = in.x * sin + in.y * cos;

		in.x = newX;
		in.y = newY;

		return in;
	}

	public static float dist(Vector2 in, Vector2 other) {
		float distX = in.x - other.x;
		float distY = in.y - other.y;
		return FloatMath.sqrt(distX * distX + distY * distY);
	}

	public static float dist(Vector2 in, float x, float y) {
		float distX = in.x - x;
		float distY = in.y - y;
		return FloatMath.sqrt(distX * distX + distY * distY);
	}

	public static float distSquared(Vector2 in, Vector2 other) {
		float distX = in.x - other.x;
		float distY = in.y - other.y;
		return distX * distX + distY * distY;
	}

	public static float distSquared(Vector2 in, float x, float y) {
		float distX = in.x - x;
		float distY = in.y - y;
		return distX * distX + distY * distY;
	}
}