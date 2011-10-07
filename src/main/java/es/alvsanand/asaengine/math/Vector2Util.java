/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package es.alvsanand.asaengine.math;

import android.util.FloatMath;
import es.alvsanand.asaengine.math.util.MathUtils;

public class Vector2Util{
	public static Vector2 cpy(Vector2 vectorA) {
		Vector2 newSVector2 = new Vector2();
		
		newSVector2.x = vectorA.x;
		newSVector2.y = vectorA.y;
		
		return newSVector2;
	}

	public static float len(Vector2 vectorA) {
		return FloatMath.sqrt(vectorA.x * vectorA.x + vectorA.y * vectorA.y);
	}

	public static float len2(Vector2 vectorA) {
		return vectorA.x * vectorA.x + vectorA.y * vectorA.y;
	}

	public static Vector2 set(Vector2 vectorA, Vector2 vectorB) {
		vectorA.x = vectorB.x;
		vectorA.y = vectorB.y;
		return vectorA;
	}

	public static Vector2 set(Vector2 vectorA, float x, float y) {
		vectorA.x = x;
		vectorA.y = y;
		return vectorA;
	}

	public static Vector2 sub(Vector2 vectorA, Vector2 vectorB) {
		vectorA.x -= vectorB.x;
		vectorA.y -= vectorB.y;
		return vectorA;
	}

	public static Vector2 nor(Vector2 vectorA) {
		float len = len(vectorA);
		if (len != 0) {
			vectorA.x /= len;
			vectorA.y /= len;
		}
		return vectorA;
	}

	public static Vector2 add(Vector2 vectorA, Vector2 vectorB) {
		vectorA.x += vectorB.x;
		vectorA.y += vectorB.y;
		return vectorA;
	}

	public static Vector2 add(Vector2 vectorA, float x, float y) {
		vectorA.x += x;
		vectorA.y += y;
		return vectorA;
	}

	public static float dot(Vector2 vectorA, Vector2 vectorB) {
		return vectorA.x * vectorB.x + vectorA.y * vectorB.y;
	}

	public static Vector2 mul(Vector2 vectorA, float scalar) {
		vectorA.x *= scalar;
		vectorA.y *= scalar;
		return vectorA;
	}

	public static float dst(Vector2 vectorA, Vector2 vectorB) {
		final float x_d = vectorB.x - vectorA.x;
		final float y_d = vectorB.y - vectorA.y;
		return FloatMath.sqrt(x_d * x_d + y_d * y_d);
	}

	public static float dst(Vector2 vectorA, float x, float y) {
		final float x_d = x - vectorA.x;
		final float y_d = y - vectorA.y;
		return FloatMath.sqrt(x_d * x_d + y_d * y_d);
	}

	public static float dst2(Vector2 vectorA, Vector2 vectorB) {
		final float x_d = vectorB.x - vectorA.x;
		final float y_d = vectorB.y - vectorA.y;
		return x_d * x_d + y_d * y_d;
	}

	public static Vector2 sub(Vector2 vectorA, float x, float y) {
		vectorA.x -= x;
		vectorA.y -= y;
		return vectorA;
	}

	public static Vector2 mul(Vector2 vectorA, Matrix3 mat) {
		vectorA.x = vectorA.x * mat.vals[0] + vectorA.y * mat.vals[3] + mat.vals[6];
		vectorA.y = vectorA.x * mat.vals[1] + vectorA.y * mat.vals[4] + mat.vals[7];
		return vectorA;
	}

	public static float crs(Vector2 vectorA, Vector2 vectorB) {
		return vectorA.x * vectorB.y - vectorA.y * vectorB.x;
	}

	public static float crs(Vector2 vectorA, float x, float y) {
		return vectorA.x * y - vectorA.y * x;
	}

	public static float angle(Vector2 vectorA) {
		float angle = (float) Math.atan2(vectorA.y, vectorA.x) * MathUtils.radiansToDegrees;
		if (angle < 0)
			angle += 360;
		return angle;
	}

	public static Vector2 rotate(Vector2 vectorA, float angle) {
		float rad = angle * MathUtils.degreesToRadians;
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);

		float newX = vectorA.x * cos - vectorA.y * sin;
		float newY = vectorA.x * sin + vectorA.y * cos;

		vectorA.x = newX;
		vectorA.y = newY;

		return vectorA;
	}

	public static Vector2 lerp(Vector2 vectorA, Vector2 target, float alpha) {
		Vector2 r = mul(vectorA, 1.0f - alpha);
		add(r, mul(cpy(target),alpha));
		return r;
	}

	public static float dst2(float x1, float y1, float x2, float y2) {
		final float x_d = x2 - x1;
		final float y_d = y2 - y1;
		return x_d * x_d + y_d * y_d;
	}
}
