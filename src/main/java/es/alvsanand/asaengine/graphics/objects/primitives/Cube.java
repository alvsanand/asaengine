package es.alvsanand.asaengine.graphics.objects.primitives;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.math.Vector3;

public class Cube extends PrimitiveObject {
	public Cube(Vector3 position, Color color, float width, float height, float depth) {
		super(position, color);
		
        width  /= 2;
        height /= 2;
        depth  /= 2;
 
        float vertexes[] = { -width, -height, -depth, // 0
                              width, -height, -depth, // 1
                              width,  height, -depth, // 2
                             -width,  height, -depth, // 3
                             -width, -height,  depth, // 4
                              width, -height,  depth, // 5
                              width,  height,  depth, // 6
                             -width,  height,  depth, // 7
        };
 
        short indexes[] = { 0, 4, 5,
                            0, 5, 1,
                            1, 5, 6,
                            1, 6, 2,
                            2, 6, 7,
                            2, 7, 3,
                            3, 7, 4,
                            3, 4, 0,
                            4, 7, 6,
                            4, 6, 5,
                            3, 0, 1,
                            3, 1, 2};
 
        float borderVertexes[] = { -width, -height, -depth, // 0
                              width, -height, -depth, // 1
                              width,  height, -depth, // 2
                             -width,  height, -depth, // 3
                             -width, -height,  depth, // 4
                              width, -height,  depth, // 5
                              width,  height,  depth, // 6
                             -width,  height,  depth, // 7
        };
 
        short borderIndexes[] = { 	0, 1, 
		                            1, 2,
		                            2, 3,
		                            3, 0,
		                            4, 5,
		                            5, 6,
		                            6, 7,
		                            7, 4,
		                            7, 3,
		                            6, 2,
		                            4, 0,
		                            5, 1};
 
        setIndexes(indexes);
        setVertexes(vertexes);
        setBorderIndexes(borderIndexes);
        setBorderVertexes(borderVertexes);
    }
	
	public Cube(Vector3 position, Color fillColor, Color borderColor, float width, float height, float depth) {
		super(position, fillColor, borderColor);
		
        width  /= 2;
        height /= 2;
        depth  /= 2;
 
        float vertexes[] = { -width, -height, -depth, // 0
                              width, -height, -depth, // 1
                              width,  height, -depth, // 2
                             -width,  height, -depth, // 3
                             -width, -height,  depth, // 4
                              width, -height,  depth, // 5
                              width,  height,  depth, // 6
                             -width,  height,  depth, // 7
        };
 
        short indexes[] = { 0, 4, 5,
                            0, 5, 1,
                            1, 5, 6,
                            1, 6, 2,
                            2, 6, 7,
                            2, 7, 3,
                            3, 7, 4,
                            3, 4, 0,
                            4, 7, 6,
                            4, 6, 5,
                            3, 0, 1,
                            3, 1, 2};
 
        float borderVertexes[] = { -width, -height, -depth, // 0
                              width, -height, -depth, // 1
                              width,  height, -depth, // 2
                             -width,  height, -depth, // 3
                             -width, -height,  depth, // 4
                              width, -height,  depth, // 5
                              width,  height,  depth, // 6
                             -width,  height,  depth, // 7
        };
 
        short borderIndexes[] = { 	0, 1, 
		                            1, 2,
		                            2, 3,
		                            3, 0,
		                            4, 5,
		                            5, 6,
		                            6, 7,
		                            7, 4,
		                            7, 3,
		                            6, 2,
		                            4, 0,
		                            5, 1};
 
        setIndexes(indexes);
        setVertexes(vertexes);
        setBorderIndexes(borderIndexes);
        setBorderVertexes(borderVertexes);
    }
}
