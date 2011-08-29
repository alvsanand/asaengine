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
package es.alvsanand.asaengine.math.collision;

import java.io.Serializable;
import java.util.List;

import es.alvsanand.asaengine.math.Matrix4;
import es.alvsanand.asaengine.math.Vector3;

public class BoundingBox implements Serializable {
	private static final long serialVersionUID = -1286036817192127343L;
	final Vector3 crn[] = new Vector3[8];
	public final Vector3 min = new Vector3();
	public final Vector3 max = new Vector3();
	final Vector3 cnt = new Vector3();
	final Vector3 dim = new Vector3();
	boolean crn_dirty = true;

	public Vector3 getCenter () {
		return cnt;
	}

	protected void updateCorners () {
		if (!crn_dirty) return;

		crn[0].set(min.x, min.y, min.z);
		crn[1].set(max.x, min.y, min.z);
		crn[2].set(max.x, max.y, min.z);
		crn[3].set(min.x, max.y, min.z);
		crn[4].set(min.x, min.y, max.z);
		crn[5].set(max.x, min.y, max.z);
		crn[6].set(max.x, max.y, max.z);
		crn[7].set(min.x, max.y, max.z);
		crn_dirty = false;
	}

	public Vector3[] getCorners () {
		updateCorners();
		return crn;
	}

	public Vector3 getDimensions () {
		return dim;
	}

	public Vector3 getMin () {
		return min;
	}

	public synchronized Vector3 getMax () {
		return max;
	}

	public BoundingBox () {
		crn_dirty = true;
		for (int l_idx = 0; l_idx < 8; l_idx++)
			crn[l_idx] = new Vector3();
		clr();
	}

	public BoundingBox (BoundingBox bounds) {
		crn_dirty = true;
		for (int l_idx = 0; l_idx < 8; l_idx++)
			crn[l_idx] = new Vector3();
		this.set(bounds);
	}

	public BoundingBox (Vector3 minimum, Vector3 maximum) {
		crn_dirty = true;
		for (int l_idx = 0; l_idx < 8; l_idx++)
			crn[l_idx] = new Vector3();
		this.set(minimum, maximum);
	}

	public BoundingBox set (BoundingBox bounds) {
		crn_dirty = true;
		return this.set(bounds.min, bounds.max);
	}

	public BoundingBox set (Vector3 minimum, Vector3 maximum) {
		min.set(minimum.x < maximum.x ? minimum.x : maximum.x, minimum.y < maximum.y ? minimum.y : maximum.y,
			minimum.z < maximum.z ? minimum.z : maximum.z);
		max.set(minimum.x > maximum.x ? minimum.x : maximum.x, minimum.y > maximum.y ? minimum.y : maximum.y,
			minimum.z > maximum.z ? minimum.z : maximum.z);
		cnt.set(min).add(max).mul(0.5f);
		dim.set(max).sub(min);
		crn_dirty = true;
		return this;
	}

	public BoundingBox set (Vector3[] points) {
		this.inf();
		for (Vector3 l_point : points)
			this.ext(l_point);
		crn_dirty = true;
		return this;
	}

	public BoundingBox set (List<Vector3> points) {
		this.inf();
		for (Vector3 l_point : points)
			this.ext(l_point);
		crn_dirty = true;
		return this;
	}

	public BoundingBox inf () {
		min.set(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		max.set(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
		cnt.set(0, 0, 0);
		dim.set(0, 0, 0);
		crn_dirty = true;
		return this;
	}

	public BoundingBox ext (Vector3 point) {
		crn_dirty = true;
		return this.set(min.set(min(min.x, point.x), min(min.y, point.y), min(min.z, point.z)),
			max.set(Math.max(max.x, point.x), Math.max(max.y, point.y), Math.max(max.z, point.z)));
	}

	public BoundingBox clr () {
		crn_dirty = true;
		return this.set(min.set(0, 0, 0), max.set(0, 0, 0));
	}

	public boolean isValid () {
		return !(min.x == max.x && min.y == max.y && min.z == max.z);
	}

	public BoundingBox ext (BoundingBox a_bounds) {
		crn_dirty = true;
		return this.set(min.set(min(min.x, a_bounds.min.x), min(min.y, a_bounds.min.y), min(min.z, a_bounds.min.z)),
			max.set(max(max.x, a_bounds.max.x), max(max.y, a_bounds.max.y), max(max.z, a_bounds.max.z)));
	}

	public BoundingBox mul (Matrix4 matrix) {
		updateCorners();
		this.inf();
		for (Vector3 l_pnt : crn) {
			l_pnt.mul(matrix);
			min.set(min(min.x, l_pnt.x), min(min.y, l_pnt.y), min(min.z, l_pnt.z));
			max.set(max(max.x, l_pnt.x), max(max.y, l_pnt.y), max(max.z, l_pnt.z));
		}
		crn_dirty = true;
		return this.set(min, max);
	}

	public boolean contains (BoundingBox bounds) {
		if (!isValid()) return true;
		if (min.x > bounds.max.x) return false;
		if (min.y > bounds.max.y) return false;
		if (min.z > bounds.max.z) return false;
		if (max.x < bounds.min.x) return false;
		if (max.y < bounds.min.y) return false;
		if (max.z < bounds.min.z) return false;
		return true;
	}

	public boolean contains (Vector3 v) {
		if (min.x > v.x) return false;
		if (max.x < v.x) return false;
		if (min.y > v.y) return false;
		if (max.y < v.y) return false;
		if (min.z > v.z) return false;
		if (max.z < v.z) return false;

		return true;
	}

	public String toString () {
		return "[" + min + "|" + max + "]";
	}

	public BoundingBox ext (float x, float y, float z) {
		crn_dirty = true;
		return this.set(min.set(min(min.x, x), min(min.y, y), min(min.z, z)), max.set(max(max.x, x), max(max.y, y), max(max.z, z)));
	}

	static float min (float a, float b) {
		return a > b ? b : a;
	}

	static float max (float a, float b) {
		return a > b ? a : b;
	}
}
