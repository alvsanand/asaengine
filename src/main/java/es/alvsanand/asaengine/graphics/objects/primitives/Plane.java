package es.alvsanand.asaengine.graphics.objects.primitives;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.math.Vector3;

public class Plane extends PrimitiveObject {
	public Plane(Vector3 position, Color color, float width, float height) {
		this(position, color, width, height, 1, 1);
	}

	public Plane(Vector3 position, Color color, float width, float height, int widthSegments, int heightSegments) {
		super(position, color);

		float[] Vertexes = new float[(widthSegments + 1) * (heightSegments + 1) * 3];
		short[] indexes = new short[(widthSegments + 1) * (heightSegments + 1) * 6];

		float xOffset = width / -2;
		float zOffset = height / -2;
		float xWidth = width / (widthSegments);
		float zHeight = height / (heightSegments);
		int currentVertex = 0;
		int currentIndex = 0;
		short w = (short) (widthSegments + 1);
		for (int z = 0; z < heightSegments + 1; z++) {
			for (int x = 0; x < widthSegments + 1; x++) {
				Vertexes[currentVertex] = xOffset + x * xWidth;
				Vertexes[currentVertex + 1] = 0;
				Vertexes[currentVertex + 2] = zOffset + z * zHeight;
				currentVertex += 3;

				int n = z * (widthSegments + 1) + x;

				if (z < heightSegments && x < widthSegments) {
					// Face one
					indexes[currentIndex] = (short) n;
					indexes[currentIndex + 1] = (short) (n + 1);
					indexes[currentIndex + 2] = (short) (n + w);
					// Face two
					indexes[currentIndex + 3] = (short) (n + 1);
					indexes[currentIndex + 4] = (short) (n + 1 + w);
					indexes[currentIndex + 5] = (short) (n + 1 + w - 1);

					currentIndex += 6;
				}
			}
		}

		setIndexes(indexes);
		setVertexes(Vertexes);
	}

	public Plane(Vector3 position, Color fillColor, Color borderColor, float width, float height) {
		this(position, fillColor, borderColor, width, height, 1, 1);
	}

	public Plane(Vector3 position, Color fillColor, Color borderColor, float width, float height, int widthSegments, int heightSegments) {
		super(position, fillColor, borderColor);

		float[] Vertexes = new float[(widthSegments + 1) * (heightSegments + 1) * 3];
		short[] indexes = new short[(widthSegments + 1) * (heightSegments + 1) * 6];

		float xOffset = width / -2;
		float zOffset = height / -2;
		float xWidth = width / (widthSegments);
		float zHeight = height / (heightSegments);
		int currentVertex = 0;
		int currentIndex = 0;
		short w = (short) (widthSegments + 1);
		for (int z = 0; z < heightSegments + 1; z++) {
			for (int x = 0; x < widthSegments + 1; x++) {
				Vertexes[currentVertex] = xOffset + x * xWidth;
				Vertexes[currentVertex + 1] = 0;
				Vertexes[currentVertex + 2] = zOffset + z * zHeight;
				currentVertex += 3;

				int n = z * (widthSegments + 1) + x;

				if (z < heightSegments && x < widthSegments) {
					// Face one
					indexes[currentIndex] = (short) n;
					indexes[currentIndex + 1] = (short) (n + 1);
					indexes[currentIndex + 2] = (short) (n + w);
					// Face two
					indexes[currentIndex + 3] = (short) (n + 1);
					indexes[currentIndex + 4] = (short) (n + 1 + w);
					indexes[currentIndex + 5] = (short) (n + 1 + w - 1);

					currentIndex += 6;
				}
			}
		}

		setIndexes(indexes);
		setVertexes(Vertexes);
	}
}