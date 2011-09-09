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

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.error.ASARuntimeException;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttribute;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes.Usage;
import es.alvsanand.asaengine.graphics.objects.utils.IndexBufferObject;
import es.alvsanand.asaengine.graphics.objects.utils.IndexBufferObjectSubData;
import es.alvsanand.asaengine.graphics.objects.utils.IndexData;
import es.alvsanand.asaengine.graphics.objects.utils.VertexArray;
import es.alvsanand.asaengine.graphics.objects.utils.VertexBufferObject;
import es.alvsanand.asaengine.graphics.objects.utils.VertexBufferObjectSubData;
import es.alvsanand.asaengine.graphics.objects.utils.VertexData;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer.GL_TYPE;
import es.alvsanand.asaengine.graphics.textures.Texture;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.collision.BoundingBox;

public class Mesh extends Object3D {
	public enum VertexDataType {
		VertexArray, VertexBufferObject, VertexBufferObjectSubData,
	}

	protected static final ArrayList<Mesh> meshes = new ArrayList<Mesh>();

	protected final VertexData vertices;
	protected final IndexData indices;
	protected boolean autoBind = true;
	protected final boolean isVertexArray;

	protected Texture texture;

	public Mesh(boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
		super(new Vector3(0, 0, 0));

		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
			indices = new IndexBufferObject(isStatic, maxIndices);
			isVertexArray = false;
		} else {
			vertices = new VertexArray(maxVertices, attributes);
			indices = new IndexBufferObject(maxIndices);
			isVertexArray = true;
		}

		addManagedMesh(this);
	}

	public Mesh(Vector3 position, boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
		super(position);
		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
			indices = new IndexBufferObject(isStatic, maxIndices);
			isVertexArray = false;
		} else {
			vertices = new VertexArray(maxVertices, attributes);
			indices = new IndexBufferObject(maxIndices);
			isVertexArray = true;
		}

		addManagedMesh(this);
	}

	public Mesh(Vector3 position, boolean isStatic, int maxVertices, int maxIndices, VertexAttributes attributes) {
		super(position);
		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
			indices = new IndexBufferObject(isStatic, maxIndices);
			isVertexArray = false;
		} else {
			vertices = new VertexArray(maxVertices, attributes);
			indices = new IndexBufferObject(maxIndices);
			isVertexArray = true;
		}

		addManagedMesh(this);
	}

	public Mesh(Vector3 position, VertexDataType type, boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
		super(position);

		if (type == VertexDataType.VertexBufferObject) {
			vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
			indices = new IndexBufferObject(isStatic, maxIndices);
			isVertexArray = false;
		} else if (type == VertexDataType.VertexBufferObjectSubData) {
			vertices = new VertexBufferObjectSubData(isStatic, maxVertices, attributes);
			indices = new IndexBufferObjectSubData(isStatic, maxIndices);
			isVertexArray = false;
		} else {
			vertices = new VertexArray(maxVertices, attributes);
			indices = new IndexBufferObject(maxIndices);
			isVertexArray = true;
		}
		addManagedMesh(this);
	}

	public void setVertices(float[] vertices) {
		this.vertices.setVertices(vertices, 0, vertices.length);
	}

	public void setVertices(float[] vertices, int offset, int count) {
		this.vertices.setVertices(vertices, offset, count);
	}

	public void getVertices(float[] vertices) {
		if (vertices.length < getNumVertices() * getVertexSize() / 4)
			throw new IllegalArgumentException("not enough room in vertices array, has " + vertices.length + " floats, needs " + getNumVertices()
					* getVertexSize() / 4);
		int pos = getVerticesBuffer().position();
		getVerticesBuffer().position(0);
		getVerticesBuffer().get(vertices, 0, getNumVertices() * getVertexSize() / 4);
		getVerticesBuffer().position(pos);
	}

	public void setIndices(short[] indices) {
		this.indices.setIndices(indices, 0, indices.length);
	}

	public void setIndices(short[] indices, int offset, int count) {
		this.indices.setIndices(indices, offset, count);
	}

	public void getIndices(short[] indices) {
		if (indices.length < getNumIndices())
			throw new IllegalArgumentException("not enough room in indices array, has " + indices.length + " floats, needs " + getNumIndices());
		int pos = getIndicesBuffer().position();
		getIndicesBuffer().position(0);
		getIndicesBuffer().get(indices, 0, getNumIndices());
		getIndicesBuffer().position(pos);
	}

	public int getNumIndices() {
		return indices.getNumIndices();
	}

	public int getNumVertices() {
		return vertices.getNumVertices();
	}

	public int getMaxVertices() {
		return vertices.getNumMaxVertices();
	}

	public int getMaxIndices() {
		return indices.getNumMaxIndices();
	}

	public int getVertexSize() {
		return vertices.getAttributes().vertexSize;
	}

	public void setAutoBind(boolean autoBind) {
		this.autoBind = autoBind;
	}

	public void bind() {
		if (texture != null) {
			texture.bind();
		}
		
		OpenGLRenderer.gl.glFrontFace(GL10.GL_CCW);

		vertices.bind();
		if (!isVertexArray && indices.getNumIndices() > 0)
			indices.bind();
	}

	public void unbind() {
		vertices.unbind();
		if (!isVertexArray && indices.getNumIndices() > 0)
			indices.unbind();
	}

	@Override
	public void render() {
		render(GL10.GL_TRIANGLES);
	}

	public void render(int primitiveType) {
		render(primitiveType, 0, indices.getNumIndices() > 0 ? getNumIndices() : getNumVertices());
	}

	public void render(int offset, int count) {
		render(GL10.GL_TRIANGLES, offset, count);
	}

	public void render(int primitiveType, int offset, int count) {
		if (autoBind) {
			bind();
		}

		OpenGLRenderer.gl.glPushMatrix();

		OpenGLRenderer.gl.glTranslatef(position.x, position.y, position.z);
		
		if(rx != 0)
			OpenGLRenderer.gl.glRotatef(rx, 1, 0, 0);
		if(ry != 0)
			OpenGLRenderer.gl.glRotatef(ry, 0, 1, 0);
		if(rz != 0)
			OpenGLRenderer.gl.glRotatef(rz, 0, 0, 1);
		
		if(sx > 0 && sy > 0 && sz > 0)
			OpenGLRenderer.gl.glScalef(sx, sy, sz);
		
//		// Set flat fill color
//		OpenGLRenderer.gl.glColor4f(256f, 256f, 256f, 1.0f);
		
		if (isVertexArray) {
			if (indices.getNumIndices() > 0) {
				ShortBuffer buffer = indices.getBuffer();
				int oldPosition = buffer.position();
				int oldLimit = buffer.limit();
				buffer.position(offset);
				buffer.limit(offset + count);
				OpenGLRenderer.gl.glDrawElements(primitiveType, count, GL10.GL_UNSIGNED_SHORT, buffer);
				buffer.position(oldPosition);
				buffer.limit(oldLimit);
			} else {
				OpenGLRenderer.gl.glDrawArrays(primitiveType, offset, count);
			}
		} else {
			if (indices.getNumIndices() > 0)
				OpenGLRenderer.gl11.glDrawElements(primitiveType, count, GL10.GL_UNSIGNED_SHORT, offset * 2);
			else
				OpenGLRenderer.gl11.glDrawArrays(primitiveType, offset, count);
		}

		OpenGLRenderer.gl.glPopMatrix();

		if (autoBind) {
			unbind();
		}
	}

	@Override
	public void dispose() {
		meshes.remove(this);
		vertices.dispose();
		indices.dispose();

		if (texture != null) {
			texture.dispose();
		}
	}

	public VertexAttribute getVertexAttribute(int usage) {
		VertexAttributes attributes = vertices.getAttributes();
		int len = attributes.size();
		for (int i = 0; i < len; i++)
			if (attributes.get(i).usage == usage)
				return attributes.get(i);

		return null;
	}

	public VertexAttributes getVertexAttributes() {
		return vertices.getAttributes();
	}

	public FloatBuffer getVerticesBuffer() {
		return vertices.getBuffer();
	}

	public BoundingBox calculateBoundingBox() {
		final int numVertices = getNumVertices();
		if (numVertices == 0)
			throw new ASARuntimeException("No vertices defined");

		final BoundingBox bbox = new BoundingBox();
		final FloatBuffer verts = vertices.getBuffer();
		bbox.inf();
		final VertexAttribute posAttrib = getVertexAttribute(Usage.Position);
		final int offset = posAttrib.offset / 4;
		final int vertexSize = vertices.getAttributes().vertexSize / 4;
		int idx = offset;

		switch (posAttrib.numComponents) {
		case 1:
			for (int i = 0; i < numVertices; i++) {
				bbox.ext(verts.get(idx), 0, 0);
				idx += vertexSize;
			}
			break;
		case 2:
			for (int i = 0; i < numVertices; i++) {
				bbox.ext(verts.get(idx), verts.get(idx + 1), 0);
				idx += vertexSize;
			}
			break;
		case 3:
			for (int i = 0; i < numVertices; i++) {
				bbox.ext(verts.get(idx), verts.get(idx + 1), verts.get(idx + 2));
				idx += vertexSize;
			}
			break;
		}

		return bbox;
	}

	public ShortBuffer getIndicesBuffer() {
		return indices.getBuffer();
	}

	private static void addManagedMesh(Mesh mesh) {
		meshes.add(mesh);
	}

	public static void invalidateAllMeshes() {
		if (meshes == null)
			return;
		for (int i = 0; i < meshes.size(); i++) {
			if (meshes.get(i).vertices instanceof VertexBufferObject) {
				((VertexBufferObject) meshes.get(i).vertices).invalidate();
				meshes.get(i).indices.invalidate();
			}
		}
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
