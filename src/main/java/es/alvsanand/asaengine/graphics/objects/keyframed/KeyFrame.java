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

	protected final VertexData vertexes;
	protected final IndexData indexes;
	protected final boolean isVertexArray;
	protected boolean useindexes = false;
	protected int numVertexes;

	protected Material material;

	public KeyFrame(int frameNumber, boolean isStatic, int maxVertexes, int maxindexes, VertexAttribute... attributes) {
		frameNumber = this.frameNumber;

		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			vertexes = new VertexBufferObject(isStatic, maxVertexes, attributes);
			indexes = new IndexBufferObject(isStatic, maxindexes);
			isVertexArray = false;
		} else {
			vertexes = new VertexArray(maxVertexes, attributes);
			indexes = new IndexBufferObject(maxindexes);
			isVertexArray = true;
		}

		addManagedKeyFrame(this);
	}

	public KeyFrame(int frameNumber, boolean isStatic, int maxVertexes, int maxindexes, VertexAttributes attributes) {
		frameNumber = this.frameNumber;

		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			vertexes = new VertexBufferObject(isStatic, maxVertexes, attributes);
			indexes = new IndexBufferObject(isStatic, maxindexes);
			isVertexArray = false;
		} else {
			vertexes = new VertexArray(maxVertexes, attributes);
			indexes = new IndexBufferObject(maxindexes);
			isVertexArray = true;
		}

		addManagedKeyFrame(this);
	}

	public KeyFrame(int frameNumber, VertexDataType type, boolean isStatic, int maxVertexes, int maxindexes, VertexAttribute... attributes) {
		frameNumber = this.frameNumber;

		if (type == VertexDataType.VertexBufferObject) {
			vertexes = new VertexBufferObject(isStatic, maxVertexes, attributes);
			indexes = new IndexBufferObject(isStatic, maxindexes);
			isVertexArray = false;
		} else if (type == VertexDataType.VertexBufferObjectSubData) {
			vertexes = new VertexBufferObjectSubData(isStatic, maxVertexes, attributes);
			indexes = new IndexBufferObjectSubData(isStatic, maxindexes);
			isVertexArray = false;
		} else {
			vertexes = new VertexArray(maxVertexes, attributes);
			indexes = new IndexBufferObject(maxindexes);
			isVertexArray = true;
		}
		addManagedKeyFrame(this);
	}

	public KeyFrame(int frameNumber, VertexData vertexes, IndexData indexes, boolean isVertexArray, Material material, int numVertexes) {
		frameNumber = this.frameNumber;

		this.vertexes = vertexes;
		this.indexes = indexes;
		this.isVertexArray = isVertexArray;
		this.material = material;
		this.numVertexes = numVertexes;
	}

	public void setVertexes(float[] vertexes) {		
		this.vertexes.setVertexes(vertexes, 0, vertexes.length);
		
		this.numVertexes = this.vertexes.getNumVertexes();
	}

	public void setVertexes(float[] vertexes, int offset, int count) {
		this.vertexes.setVertexes(vertexes, offset, count);
		
		this.numVertexes = this.vertexes.getNumVertexes();
	}

	public void getVertexes(float[] vertexes) {
		if (vertexes.length < getNumVertexes() * getVertexSize() / 4)
			throw new IllegalArgumentException("not enough room in Vertexes array, has " + vertexes.length + " floats, needs " + getNumVertexes()
					* getVertexSize() / 4);
		int pos = getVertexesBuffer().position();
		getVertexesBuffer().position(0);
		getVertexesBuffer().get(vertexes, 0, getNumVertexes() * getVertexSize() / 4);
		getVertexesBuffer().position(pos);
	}

	public void setindexes(short[] indexes) {
		this.useindexes = indexes != null && indexes.length > 0;
		
		this.indexes.setindexes(indexes, 0, indexes.length);
	}

	public void setindexes(short[] indexes, int offset, int count) {
		this.useindexes = indexes != null && count > 0;
		
		this.indexes.setindexes(indexes, offset, count);
	}

	public void getindexes(short[] indexes) {
		if (indexes.length < getNumindexes())
			throw new IllegalArgumentException("not enough room in indexes array, has " + indexes.length + " floats, needs " + getNumindexes());
		int pos = getindexesBuffer().position();
		getindexesBuffer().position(0);
		getindexesBuffer().get(indexes, 0, getNumindexes());
		getindexesBuffer().position(pos);
	}

	public int getNumindexes() {
		return indexes.getNumindexes();
	}

	public int getNumVertexes() {
		return vertexes.getNumVertexes();
	}

	public int getMaxVertexes() {
		return vertexes.getNumMaxVertexes();
	}

	public int getMaxindexes() {
		return indexes.getNumMaxindexes();
	}

	public int getVertexSize() {
		return vertexes.getAttributes().vertexSize;
	}

	public void bind() {
		if (material != null && material.getTexture() != null) {
			OpenGLRenderer.gl.glEnable(GL10.GL_TEXTURE_2D);
			material.getTexture().bind();
		}

		OpenGLRenderer.gl.glFrontFace(GL10.GL_CCW);

		vertexes.bind();
		if (!isVertexArray && indexes.getNumindexes() > 0)
			indexes.bind();
	}

	public void unbind() {
		vertexes.unbind();

		if (!isVertexArray && indexes.getNumindexes() > 0)
			indexes.unbind();

		if (material != null && material.getTexture() != null) {
			OpenGLRenderer.gl.glDisable(GL10.GL_TEXTURE_2D);
		}
	}

	@Override
	public void render() {
		render(GL10.GL_TRIANGLES);
	}

	public void render(int primitiveType) {
		render(primitiveType, 0, indexes.getNumindexes() > 0 ? getNumindexes() : getNumVertexes());
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
			if (indexes.getNumindexes() > 0) {
				ShortBuffer buffer = indexes.getBuffer();
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
			if (indexes.getNumindexes() > 0)
				OpenGLRenderer.gl11.glDrawElements(primitiveType, count, GL10.GL_UNSIGNED_SHORT, offset * 2);
			else
				OpenGLRenderer.gl11.glDrawArrays(primitiveType, offset, count);
		}
	}

	@Override
	public void dispose() {
		keyframes.remove(this);
		vertexes.dispose();
		indexes.dispose();

		if (material != null && material.getTexture() != null) {
			material.getTexture().dispose();
		}
	}

	public VertexAttribute getVertexAttribute(int usage) {
		VertexAttributes attributes = vertexes.getAttributes();
		int len = attributes.size();
		for (int i = 0; i < len; i++)
			if (attributes.get(i).usage == usage)
				return attributes.get(i);

		return null;
	}

	public VertexAttributes getVertexAttributes() {
		return vertexes.getAttributes();
	}

	public FloatBuffer getVertexesBuffer() {
		return vertexes.getBuffer();
	}

	public BoundingBox calculateBoundingBox() {
		final int numVertexes = getNumVertexes();
		if (numVertexes == 0)
			throw new ASARuntimeException("No Vertexes defined");

		final BoundingBox bbox = new BoundingBox();
		final FloatBuffer verts = vertexes.getBuffer();
		bbox.inf();
		final VertexAttribute posAttrib = getVertexAttribute(Usage.Position);
		final int offset = posAttrib.offset / 4;
		final int vertexSize = vertexes.getAttributes().vertexSize / 4;
		int idx = offset;

		switch (posAttrib.numComponents) {
		case 1:
			for (int i = 0; i < numVertexes; i++) {
				bbox.ext(verts.get(idx), 0, 0);
				idx += vertexSize;
			}
			break;
		case 2:
			for (int i = 0; i < numVertexes; i++) {
				bbox.ext(verts.get(idx), verts.get(idx + 1), 0);
				idx += vertexSize;
			}
			break;
		case 3:
			for (int i = 0; i < numVertexes; i++) {
				bbox.ext(verts.get(idx), verts.get(idx + 1), verts.get(idx + 2));
				idx += vertexSize;
			}
			break;
		}

		return bbox;
	}

	public ShortBuffer getindexesBuffer() {
		return indexes.getBuffer();
	}

	private static void addManagedKeyFrame(KeyFrame mesh) {
		keyframes.add(mesh);
	}

	public static void invalidateAllKeyFramees() {
		if (keyframes == null)
			return;
		for (int i = 0; i < keyframes.size(); i++) {
			if (keyframes.get(i).vertexes instanceof VertexBufferObject) {
				((VertexBufferObject) keyframes.get(i).vertexes).invalidate();
				keyframes.get(i).indexes.invalidate();
			}
		}
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public KeyFrame duplicate() {
		KeyFrame newKeyFrame = new KeyFrame(frameNumber, vertexes, indexes, isVertexArray, material, numVertexes);

		return newKeyFrame;
	}
}
