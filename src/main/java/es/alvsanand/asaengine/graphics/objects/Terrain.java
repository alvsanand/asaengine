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
package es.alvsanand.asaengine.graphics.objects;

import android.util.Log;
import es.alvsanand.asaengine.graphics.materials.Material;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttribute;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes;
import es.alvsanand.asaengine.graphics.objects.utils.IndexData;
import es.alvsanand.asaengine.graphics.objects.utils.VertexData;
import es.alvsanand.asaengine.graphics.objects.utils.VertexData.VertexDataType;
import es.alvsanand.asaengine.math.SVector3;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.collision.TriangleAprox;

public class Terrain extends Mesh {
	private static String TAG = "Terrain";
	
	private TriangleAprox[] triangleAproxs;

	public Terrain(boolean isStatic, int maxVertexes, int maxindexes, VertexAttribute[] attributes) {
		super(isStatic, maxVertexes, maxindexes, attributes);
	}

	public Terrain(Vector3 position, boolean isStatic, int maxVertexes, int maxindexes, VertexAttribute... attributes) {
		super(position, isStatic, maxVertexes, maxindexes, attributes);
	}

	public Terrain(Vector3 position, boolean isStatic, int maxVertexes, int maxindexes, VertexAttributes attributes) {
		super(position, isStatic, maxVertexes, maxindexes, attributes);
	}

	public Terrain(Vector3 position, VertexData Vertexes, IndexData indexes, boolean autoBind, boolean isVertexArray, Material material) {
		super(position, Vertexes, indexes, autoBind, isVertexArray, material);
	}

	public Terrain(Vector3 position, VertexDataType type, boolean isStatic, int maxVertexes, int maxindexes, VertexAttribute... attributes) {
		super(position, type, isStatic, maxVertexes, maxindexes, attributes);
	}

	public void calulateTerrainTriangleAprox() {
		boolean useindexes = indexes != null && indexes.getNumindexes() > 0;

		int numVertexes = vertexes.getNumVertexes();

		triangleAproxs = new TriangleAprox[numVertexes];

		for (int i = 0; i < numVertexes; i++) {
			TriangleAprox box = new TriangleAprox();

			for (int j = 0; j < 3; j++) {
				if (useindexes) {
					int vertexIndex = indexes.getVertexIndex(i);

					float[] xyz = vertexes.getvertex(vertexIndex);

					float x = xyz[0];
					float y = xyz[1];
					float z = xyz[2];

					if (box.min.x > x)
						box.min.x = x;
					if (box.min.y > y)
						box.min.y = y;
					if (box.min.z > z)
						box.min.z = z;

					if (box.max.x < x)
						box.max.x = x;
					if (box.max.y < y)
						box.max.y = y;
					if (box.max.z < z)
						box.max.z = z;

					box.triangleIndex = i;

					triangleAproxs[i] = box;
				} else {
					float[] xyz = vertexes.getvertex(i);

					float x = xyz[0];
					float y = xyz[1];
					float z = xyz[2];

					if (box.min.x > x)
						box.min.x = x;
					if (box.min.y > y)
						box.min.y = y;
					if (box.min.z > z)
						box.min.z = z;

					if (box.max.x < x)
						box.max.x = x;
					if (box.max.y < y)
						box.max.y = y;
					if (box.max.z < z)
						box.max.z = z;

					box.triangleIndex = i;

					triangleAproxs[i] = box;
				}
			}
		}
	}

	private boolean isOver(TriangleAprox triangleAprox, float x, float y, float z) {	
		Log.v(TAG, triangleAprox.min.x + "\t\t-" + triangleAprox.max.x + "\t\t<->" + "\t\t-" + triangleAprox.min.z + "\t\t-" + triangleAprox.max.z);
		
		return x <= triangleAprox.max.x && x >= triangleAprox.min.x &&
		// y <= b->max.y && y >= b->min.y &&
				z <= triangleAprox.max.z && z >= triangleAprox.min.z;
	}

	private TriangleAprox findTerrainBox(float x, float y, float z) {
		Log.v(TAG, "**********************************");
		Log.v(TAG, "findTerrainBox: " + x + "\t\t" + y + "\t\t" + z);
		Log.v(TAG, "**********************************");
		
		for (int i = 0; i < triangleAproxs.length; i++) {
			if (isOver(triangleAproxs[i], x, y, z))
				return triangleAproxs[i];
		}

		return null;
	}

	public float calculateTerrainHeight(SVector3 position) {
		return calculateTerrainHeight(position.x, position.y, position.z);
	}

	public float calculateTerrainHeight(float px, float py, float pz) {
		int y = 10000;

		TriangleAprox triangleAprox = findTerrainBox(px, y, pz);

		if (triangleAprox == null)
			return -100000; // broken map? no triangle under the character???

		boolean useindexes = indexes != null && indexes.getNumindexes() > 0;

		float[] xyz = null;

		int vertexIndex = -1;
		if (useindexes) {
			vertexIndex = indexes.getVertexIndex(triangleAprox.triangleIndex);

			xyz = vertexes.getvertex(vertexIndex);
		} else {
			xyz = vertexes.getvertex(triangleAprox.triangleIndex);
		}

		float x1 = xyz[0];
		float y1 = xyz[1];
		float z1 = xyz[2];

		if (useindexes) {
			xyz = vertexes.getvertex(vertexIndex + 1);
		} else {
			xyz = vertexes.getvertex(triangleAprox.triangleIndex + 1);
		}

		float x2 = xyz[0];
		float y2 = xyz[1];
		float z2 = xyz[2];

		if (useindexes) {
			xyz = vertexes.getvertex(vertexIndex + 2);
		} else {
			xyz = vertexes.getvertex(triangleAprox.triangleIndex + 2);
		}

		float x3 = xyz[0];
		float y3 = xyz[1];
		float z3 = xyz[2];

		float a = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
		float b = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
		float c = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
		float d = -(x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1));

		if (b == 0)
			b = 0.1f;

		return (d + a * px + c * pz) / b;
	}
}