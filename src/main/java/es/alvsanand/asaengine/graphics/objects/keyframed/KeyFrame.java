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
package es.alvsanand.asaengine.graphics.objects.keyframed;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.error.ASARuntimeException;
import es.alvsanand.asaengine.graphics.materials.Material;
import es.alvsanand.asaengine.graphics.objects.Renderable;
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
import es.alvsanand.asaengine.graphics.objects.utils.VertexData.VertexDataType;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer.GL_TYPE;
import es.alvsanand.asaengine.math.collision.BoundingBox;
import es.alvsanand.asaengine.util.Disposable;

public class KeyFrame implements Disposable, Renderable {
	private int frameNumber;

	protected static final ArrayList<KeyFrame> keyframes = new ArrayList<KeyFrame>();

	protected final VertexData vertices;
	protected final IndexData indices;
	protected final boolean isVertexArray;

	protected Material material;

	public KeyFrame(int frameNumber, boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
		frameNumber = this.frameNumber;

		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
			indices = new IndexBufferObject(isStatic, maxIndices);
			isVertexArray = false;
		} else {
			vertices = new VertexArray(maxVertices, attributes);
			indices = new IndexBufferObject(maxIndices);
			isVertexArray = true;
		}

		addManagedKeyFrame(this);
	}

	public KeyFrame(int frameNumber, boolean isStatic, int maxVertices, int maxIndices, VertexAttributes attributes) {
		frameNumber = this.frameNumber;

		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
			indices = new IndexBufferObject(isStatic, maxIndices);
			isVertexArray = false;
		} else {
			vertices = new VertexArray(maxVertices, attributes);
			indices = new IndexBufferObject(maxIndices);
			isVertexArray = true;
		}

		addManagedKeyFrame(this);
	}

	public KeyFrame(int frameNumber, VertexDataType type, boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
		frameNumber = this.frameNumber;

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
		addManagedKeyFrame(this);
	}

	public KeyFrame(int frameNumber, VertexData vertices, IndexData indices, boolean isVertexArray, Material material) {
		frameNumber = this.frameNumber;

		this.vertices = vertices;
		this.indices = indices;
		this.isVertexArray = isVertexArray;
		this.material = material;
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

	public void bind() {
		if (material != null && material.getTexture() != null) {
			OpenGLRenderer.gl.glEnable(GL10.GL_TEXTURE_2D);
			material.getTexture().bind();
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

		if (material != null && material.getTexture() != null) {
			OpenGLRenderer.gl.glDisable(GL10.GL_TEXTURE_2D);
		}
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
		//Only diffuse color
		if(material!=null && material.getDiffuseColor()!=null){
			OpenGLRenderer.gl.glColor4f(material.getDiffuseColor().r, material.getDiffuseColor().g, material.getDiffuseColor().b, material.getDiffuseColor().a);
		}

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
	}

	@Override
	public void dispose() {
		keyframes.remove(this);
		vertices.dispose();
		indices.dispose();

		if (material != null && material.getTexture() != null) {
			material.getTexture().dispose();
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

	private static void addManagedKeyFrame(KeyFrame mesh) {
		keyframes.add(mesh);
	}

	public static void invalidateAllKeyFramees() {
		if (keyframes == null)
			return;
		for (int i = 0; i < keyframes.size(); i++) {
			if (keyframes.get(i).vertices instanceof VertexBufferObject) {
				((VertexBufferObject) keyframes.get(i).vertices).invalidate();
				keyframes.get(i).indices.invalidate();
			}
		}
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public KeyFrame duplicate() {
		KeyFrame newKeyFrame = new KeyFrame(frameNumber, vertices, indices, isVertexArray, material);

		return newKeyFrame;
	}
}
