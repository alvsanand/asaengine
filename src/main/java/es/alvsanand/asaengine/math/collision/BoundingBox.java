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
import es.alvsanand.asaengine.math.Vector3Util;

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

		Vector3Util.set(crn[0], min.x, min.y, min.z);
		Vector3Util.set(crn[1], max.x, min.y, min.z);
		Vector3Util.set(crn[2], max.x, max.y, min.z);
		Vector3Util.set(crn[3], min.x, max.y, min.z);
		Vector3Util.set(crn[4], min.x, min.y, max.z);
		Vector3Util.set(crn[5], max.x, min.y, max.z);
		Vector3Util.set(crn[6], max.x, max.y, max.z);
		Vector3Util.set(crn[7], min.x, max.y, max.z);
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
		Vector3Util.set(min, minimum.x < maximum.x ? minimum.x : maximum.x, minimum.y < maximum.y ? minimum.y : maximum.y,
			minimum.z < maximum.z ? minimum.z : maximum.z);
		Vector3Util.set(max, minimum.x > maximum.x ? minimum.x : maximum.x, minimum.y > maximum.y ? minimum.y : maximum.y,
			minimum.z > maximum.z ? minimum.z : maximum.z);
		Vector3Util.mul(Vector3Util.add(Vector3Util.set(cnt, min), max), 0.5f);
		Vector3Util.sub(Vector3Util.set(dim, max), min);
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
		Vector3Util.set(min, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		Vector3Util.set(max, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
		Vector3Util.set(cnt, 0, 0, 0);
		Vector3Util.set(dim, 0, 0, 0);
		crn_dirty = true;
		return this;
	}

	public BoundingBox ext (Vector3 point) {
		crn_dirty = true;
		return this.set(Vector3Util.set(min, min(min.x, point.x), min(min.y, point.y), min(min.z, point.z)),
				Vector3Util.set(max, Math.max(max.x, point.x), Math.max(max.y, point.y), Math.max(max.z, point.z)));
	}

	public BoundingBox clr () {
		crn_dirty = true;
		return this.set(Vector3Util.set(min, 0, 0, 0), Vector3Util.set(max, 0, 0, 0));
	}

	public boolean isValid () {
		return !(min.x == max.x && min.y == max.y && min.z == max.z);
	}

	public BoundingBox ext (BoundingBox a_bounds) {
		crn_dirty = true;
		return this.set(Vector3Util.set(min, min(min.x, a_bounds.min.x), min(min.y, a_bounds.min.y), min(min.z, a_bounds.min.z)),
				Vector3Util.set(max, max(max.x, a_bounds.max.x), max(max.y, a_bounds.max.y), max(max.z, a_bounds.max.z)));
	}

	public BoundingBox mul (Matrix4 matrix) {
		updateCorners();
		this.inf();
		for (Vector3 l_pnt : crn) {
			Vector3Util.mul(l_pnt, matrix);
			Vector3Util.set(min, min(min.x, l_pnt.x), min(min.y, l_pnt.y), min(min.z, l_pnt.z));
			Vector3Util.set(max, max(max.x, l_pnt.x), max(max.y, l_pnt.y), max(max.z, l_pnt.z));
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
		return this.set(Vector3Util.set(min, min(min.x, x), min(min.y, y), min(min.z, z)), Vector3Util.set(max, max(max.x, x), max(max.y, y), max(max.z, z)));
	}

	static float min (float a, float b) {
		return a > b ? b : a;
	}

	static float max (float a, float b) {
		return a > b ? a : b;
	}
}
