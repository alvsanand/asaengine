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

import es.alvsanand.asaengine.graphics.materials.Material;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttribute;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes;
import es.alvsanand.asaengine.graphics.objects.error.OutOfTerrainException;
import es.alvsanand.asaengine.graphics.objects.utils.IndexData;
import es.alvsanand.asaengine.graphics.objects.utils.VertexData;
import es.alvsanand.asaengine.graphics.objects.utils.VertexData.VertexDataType;
import es.alvsanand.asaengine.math.Vector2Util;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.collision.TriangleAprox;

public class Terrain extends Mesh {
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

	public Terrain(Vector3 position, VertexData Vertexes, IndexData indexes, boolean autoBind, boolean isVertexArray, Material material, int numVertexes) {
		super(position, Vertexes, indexes, autoBind, isVertexArray, material, numVertexes);
	}

	public Terrain(Vector3 position, VertexDataType type, boolean isStatic, int maxVertexes, int maxindexes, VertexAttribute... attributes) {
		super(position, type, isStatic, maxVertexes, maxindexes, attributes);
	}

	public void calulateTerrainTriangleAprox() {
		int numTriangles = (int) (numVertexes / 3);

		triangleAproxs = new TriangleAprox[numTriangles];

		for (int i = 0; i < numTriangles; i++) {
			TriangleAprox box = new TriangleAprox();

			for (int j = 0; j < 3; j++) {
				if (useindexes) {
					int vertexIndex = indexes.getVertexIndex(i * 3 + j);

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
				} else {
					float[] xyz = vertexes.getvertex(i * 3 + j);

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
				}
			}

			box.triangleIndex = i;

			triangleAproxs[i] = box;
		}
	}

	private static boolean testCollision(TriangleAprox triangleAprox, float x, float y, float z, boolean over) {
		
		
		return x <= triangleAprox.max.x && x >= triangleAprox.min.x &&
				((!over)?(y <= triangleAprox.max.y && y >= triangleAprox.min.y):true) &&
				z <= triangleAprox.max.z && z >= triangleAprox.min.z;
	}

	private static TriangleAprox findTerrainBox(Terrain terrain, float x, float y, float z, boolean useindexes, boolean fast, boolean over) {
		TriangleAprox candidate = null;

		float minDistance = 0;
		for (int i = 0; i < terrain.triangleAproxs.length; i++) {
			if (testCollision(terrain.triangleAproxs[i], x, y, z, over)) {

				if (fast) {
					float[] xyz = null;

					int vertexOffset = terrain.triangleAproxs[i].triangleIndex * 3;
					int vertexIndex = -1;

					if (useindexes) {
						vertexIndex = terrain.indexes.getVertexIndex(vertexOffset);

						xyz = terrain.vertexes.getvertex(vertexIndex);
					} else {
						xyz = terrain.vertexes.getvertex(vertexOffset);
					}

					float dst = Vector2Util.dst2(xyz[0], xyz[2], x, z);

					if (useindexes) {
						vertexIndex = terrain.indexes.getVertexIndex(vertexOffset + 1);

						xyz = terrain.vertexes.getvertex(vertexIndex);
					} else {
						xyz = terrain.vertexes.getvertex(vertexOffset + 1);
					}

					dst += Vector2Util.dst2(xyz[0], xyz[2], x, z);

					if (useindexes) {
						vertexIndex = terrain.indexes.getVertexIndex(vertexOffset + 2);

						xyz = terrain.vertexes.getvertex(vertexIndex);
					} else {
						xyz = terrain.vertexes.getvertex(vertexOffset + 2);
					}

					dst += Vector2Util.dst2(xyz[0], xyz[2], x, z);

					if (candidate == null || dst < minDistance) {
						candidate = terrain.triangleAproxs[i];
						minDistance = dst;
					}
				} else {
					return terrain.triangleAproxs[i];
				}
			}
		}

		return candidate;
	}

	public static float calculateTerrainHeight(Terrain terrain, Vector3 position, boolean fast) throws OutOfTerrainException {
		return calculateTerrainHeight(terrain, position.x, position.y, position.z, fast);
	}

	public static float calculateTerrainHeight(Terrain terrain, Vector3 position) throws OutOfTerrainException {
		return calculateTerrainHeight(terrain, position.x, position.y, position.z, false);
	}

	public static float calculateTerrainHeight(Terrain terrain, float px, float py, float pz) throws OutOfTerrainException {
		return calculateTerrainHeight(terrain, px, py, pz, false);
	}

	public static float calculateTerrainHeight(Terrain terrain, float px, float py, float pz, boolean fast) throws OutOfTerrainException {
		TriangleAprox triangleAprox = findTerrainBox(terrain, px, py, pz, terrain.useindexes, fast, true);

		if (triangleAprox == null) {
			throw new OutOfTerrainException("Position is not over/behind terrain");
		}

		float[] xyz = null;

		int vertexOffset = triangleAprox.triangleIndex * 3;

		int vertexIndex = -1;
		if (terrain.useindexes) {
			vertexIndex = terrain.indexes.getVertexIndex(vertexOffset);

			xyz = terrain.vertexes.getvertex(vertexIndex);
		} else {
			xyz = terrain.vertexes.getvertex(vertexOffset);
		}

		float x1 = xyz[0];
		float y1 = xyz[1];
		float z1 = xyz[2];

		if (terrain.useindexes) {
			vertexIndex = terrain.indexes.getVertexIndex(vertexOffset + 1);

			xyz = terrain.vertexes.getvertex(vertexIndex);
		} else {
			xyz = terrain.vertexes.getvertex(vertexOffset + 1);
		}

		float x2 = xyz[0];
		float y2 = xyz[1];
		float z2 = xyz[2];

		if (terrain.useindexes) {
			vertexIndex = terrain.indexes.getVertexIndex(vertexOffset + 2);

			xyz = terrain.vertexes.getvertex(vertexIndex);
		} else {
			xyz = terrain.vertexes.getvertex(vertexOffset + 2);
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

		return -((d + a * px + c * pz) / b);
	}
}