package es.alvsanand.asaengine.graphics.objects.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.math.Vector3;

public abstract class PrimitiveObject extends Object3D {
	// Our vertex buffer.
	private FloatBuffer vertexesBuffer = null;

	// Our index buffer.
	private ShortBuffer indexesBuffer = null;
	
	// Our vertex buffer.
	private FloatBuffer borderVertexsBuffer = null;

	// Our index buffer.
	private ShortBuffer borderIndexesBuffer = null;

	// The number of indexes.
	private int numOfindexes = -1;

	// The number of indexes.
	private int numOfBorderindexes = -1;

	// Smooth Colors
	private FloatBuffer colorBuffer = null;
	
	public Color fillColor;
	public Color borderColor;
	public boolean printBorder;

	public PrimitiveObject(Vector3 position, Color fillColor) {
		super(position);

		this.fillColor = fillColor;
	}

	public PrimitiveObject(Vector3 position, Color fillColor, Color borderColor) {
		super(position);
		this.fillColor = fillColor;
		this.borderColor = borderColor;
		this.printBorder = true;
	}

	@Override
	public void render() {
//		// Counter-clockwise winding.
//		OpenGLRenderer.gl.glFrontFace(GL10.GL_CCW);
//		// Enable face culling.
//		OpenGLRenderer.gl.glEnable(GL10.GL_CULL_FACE);
//		// What faces to remove with the face culling.
//		OpenGLRenderer.gl.glCullFace(GL10.GL_BACK);
		// Enabled the Vertexes buffer for writing and to be used during
		// rendering.
		OpenGLRenderer.gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		OpenGLRenderer.gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexesBuffer);
		
		OpenGLRenderer.gl.glPushMatrix();
		
		OpenGLRenderer.gl.glTranslatef(position.x, position.y, position.z);
		OpenGLRenderer.gl.glRotatef(rx, 1, 0, 0);
		OpenGLRenderer.gl.glRotatef(ry, 0, 1, 0);
		OpenGLRenderer.gl.glRotatef(rz, 0, 0, 1);	
		
		// Set flat fill color
		OpenGLRenderer.gl.glColor4f(fillColor.r, fillColor.g, fillColor.b, fillColor.a);
		// Smooth color
		if (colorBuffer != null) {
			// Enable the color array buffer to be used during rendering.
			OpenGLRenderer.gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			OpenGLRenderer.gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		}

		// Point out the where the color buffer is.
		OpenGLRenderer.gl.glDrawElements(GL10.GL_TRIANGLES, numOfindexes, GL10.GL_UNSIGNED_SHORT, indexesBuffer);	

		//Draw border
		if(printBorder){
			OpenGLRenderer.gl.glPolygonOffset(1.0f, 1.0f);
			// Set flat border color
			OpenGLRenderer.gl.glColor4f(borderColor.r, borderColor.g, borderColor.b, borderColor.a);
		
			OpenGLRenderer.gl.glDrawElements(GL10.GL_LINES, numOfBorderindexes, GL10.GL_UNSIGNED_SHORT, borderIndexesBuffer);
		}
		
		OpenGLRenderer.gl.glPopMatrix();		

		{
			float heightLineVertexes[] = { position.x, -10, position.z, position.x, position.y, position.z };

			ByteBuffer vbb = ByteBuffer.allocateDirect(heightLineVertexes.length * 4);
			vbb.order(ByteOrder.nativeOrder());
			FloatBuffer heightLineBuffer = vbb.asFloatBuffer();
			heightLineBuffer.put(heightLineVertexes);
			heightLineBuffer.position(0);

			short indexes[] = { 0, 1 };

			ByteBuffer ibb = ByteBuffer.allocateDirect(indexes.length * 2);
			ibb.order(ByteOrder.nativeOrder());
			ShortBuffer heightLineIndexesBuffer = ibb.asShortBuffer();
			heightLineIndexesBuffer.put(indexes);
			heightLineIndexesBuffer.position(0);

			OpenGLRenderer.gl.glVertexPointer(3, GL10.GL_FLOAT, 0, heightLineBuffer);

			OpenGLRenderer.gl.glPolygonOffset(1.0f, 1.0f);
			// Set flat border color
			OpenGLRenderer.gl.glColor4f(borderColor.r, borderColor.g, borderColor.b, borderColor.a);

			OpenGLRenderer.gl.glDrawElements(GL10.GL_LINES, 2, GL10.GL_UNSIGNED_SHORT, heightLineIndexesBuffer);
		}
		
		// Disable the Vertexes buffer.
		OpenGLRenderer.gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// Disable face culling.
		OpenGLRenderer.gl.glDisable(GL10.GL_CULL_FACE);
	}

	protected void setVertexes(float[] vertexes) {
		// a float is 4 bytes, therefore we multiply the number if
		// Vertexes with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertexes.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexesBuffer = vbb.asFloatBuffer();
		vertexesBuffer.put(vertexes);
		vertexesBuffer.position(0);
	}

	protected void setIndexes(short[] indexes) {
		// short is 2 bytes, therefore we multiply the number if
		// Vertexes with 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(indexes.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexesBuffer = ibb.asShortBuffer();
		indexesBuffer.put(indexes);
		indexesBuffer.position(0);
		numOfindexes = indexes.length;
	}

	protected void setBorderVertexes(float[] borderVertexes) {
		// a float is 4 bytes, therefore we multiply the number if
		// Vertexes with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(borderVertexes.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		borderVertexsBuffer = vbb.asFloatBuffer();
		borderVertexsBuffer.put(borderVertexes);
		borderVertexsBuffer.position(0);
	}

	protected void setBorderIndexes(short[] borderIndexes) {
		// short is 2 bytes, therefore we multiply the number if
		// Vertexes with 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(borderIndexes.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		borderIndexesBuffer = ibb.asShortBuffer();
		borderIndexesBuffer.put(borderIndexes);
		borderIndexesBuffer.position(0);
		numOfBorderindexes = borderIndexes.length;
	}

	protected void setColors(float[] colors) {
		// float has 4 bytes.
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}

	@Override
	public void dispose() {
		if(vertexesBuffer!=null){
			vertexesBuffer.clear();
		}
		if(indexesBuffer!=null){
			indexesBuffer.clear();
		}
		if(vertexesBuffer!=null){
			vertexesBuffer.clear();
		}
		if(borderVertexsBuffer!=null){
			borderVertexsBuffer.clear();
		}
		if(borderIndexesBuffer!=null){
			borderIndexesBuffer.clear();
		}
		if(colorBuffer!=null){
			colorBuffer.clear();
		}
		
	}
	
	@Override
	protected void renderPosition() {
	}
}
