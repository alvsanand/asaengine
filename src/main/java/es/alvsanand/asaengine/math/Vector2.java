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

import java.io.Serializable;

import es.alvsanand.asaengine.math.util.MathUtils;

public class Vector2 implements Serializable {
	private static final long serialVersionUID = 913902788239530931L;

	/** static temporary vector **/
	private final static Vector2 tmp = new Vector2();

	/** the x-component of this vector **/
	public float x;
	/** the y-component of this vector **/
	public float y;

	public Vector2() {

	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(Vector2 v) {
		set(v);
	}

	public Vector2 cpy() {
		return new Vector2(this);
	}

	public float len() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float len2() {
		return x * x + y * y;
	}

	public Vector2 set(Vector2 v) {
		x = v.x;
		y = v.y;
		return this;
	}

	public Vector2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2 sub(Vector2 v) {
		x -= v.x;
		y -= v.y;
		return this;
	}

	public Vector2 nor() {
		float len = len();
		if (len != 0) {
			x /= len;
			y /= len;
		}
		return this;
	}

	public Vector2 add(Vector2 v) {
		x += v.x;
		y += v.y;
		return this;
	}

	public Vector2 add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public float dot(Vector2 v) {
		return x * v.x + y * v.y;
	}

	public Vector2 mul(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public float dst(Vector2 v) {
		final float x_d = v.x - x;
		final float y_d = v.y - y;
		return (float) Math.sqrt(x_d * x_d + y_d * y_d);
	}

	public float dst(float x, float y) {
		final float x_d = x - this.x;
		final float y_d = y - this.y;
		return (float) Math.sqrt(x_d * x_d + y_d * y_d);
	}

	public float dst2(Vector2 v) {
		final float x_d = v.x - x;
		final float y_d = v.y - y;
		return x_d * x_d + y_d * y_d;
	}

	public String toString() {
		return "[" + x + ":" + y + "]";
	}

	public Vector2 sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2 tmp() {
		return tmp.set(this);
	}

	public Vector2 mul(Matrix3 mat) {
		float x = this.x * mat.vals[0] + this.y * mat.vals[3] + mat.vals[6];
		float y = this.x * mat.vals[1] + this.y * mat.vals[4] + mat.vals[7];
		this.x = x;
		this.y = y;
		return this;
	}

	public float crs(Vector2 v) {
		return this.x * v.y - this.y * v.x;
	}

	public float crs(float x, float y) {
		return this.x * y - this.y * x;
	}

	public float angle() {
		float angle = (float) Math.atan2(y, x) * MathUtils.radiansToDegrees;
		if (angle < 0)
			angle += 360;
		return angle;
	}

	public Vector2 rotate(float angle) {
		float rad = angle * MathUtils.degreesToRadians;
		float cos = (float) Math.cos(rad);
		float sin = (float) Math.sin(rad);

		float newX = this.x * cos - this.y * sin;
		float newY = this.x * sin + this.y * cos;

		this.x = newX;
		this.y = newY;

		return this;
	}

	public Vector2 lerp(Vector2 target, float alpha) {
		Vector2 r = this.mul(1.0f - alpha);
		r.add(target.tmp().mul(alpha));
		return r;
	}
}
