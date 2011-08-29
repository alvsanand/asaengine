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
package es.alvsanand.asaengine.graphics.objects.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttribute;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes.Usage;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.util.BufferUtils;

public class VertexBufferObject implements VertexData {
	final static IntBuffer tmpHandle = BufferUtils.newIntBuffer(1);

	final VertexAttributes attributes;
	final FloatBuffer buffer;
	final ByteBuffer byteBuffer;
	int bufferHandle;
	final boolean isDirect;
	final boolean isStatic;
	final int usage;
	boolean isDirty = false;
	boolean isBound = false;

	public VertexBufferObject(boolean isStatic, int numVertices, VertexAttribute... attributes) {
		this(isStatic, numVertices, new VertexAttributes(attributes));
	}

	public VertexBufferObject(boolean isStatic, int numVertices, VertexAttributes attributes) {
		this.isStatic = isStatic;
		this.attributes = attributes;

		byteBuffer = ByteBuffer.allocateDirect(this.attributes.vertexSize * numVertices);
		byteBuffer.order(ByteOrder.nativeOrder());
		isDirect = true;
		buffer = byteBuffer.asFloatBuffer();
		buffer.flip();
		byteBuffer.flip();
		bufferHandle = createBufferObject();
		usage = isStatic ? GL11.GL_STATIC_DRAW : GL11.GL_DYNAMIC_DRAW;
	}

	private int createBufferObject() {
		OpenGLRenderer.gl11.glGenBuffers(1, tmpHandle);
		return tmpHandle.get(0);
	}

	@Override
	public VertexAttributes getAttributes() {
		return attributes;
	}

	@Override
	public int getNumVertices() {
		return buffer.limit() * 4 / attributes.vertexSize;
	}

	public int getNumMaxVertices() {
		return byteBuffer.capacity() / attributes.vertexSize;
	}

	@Override
	public FloatBuffer getBuffer() {
		isDirty = true;
		return buffer;
	}

	@Override
	public void setVertices(float[] vertices, int offset, int count) {
		isDirty = true;
		if (isDirect) {
			BufferUtils.copy(vertices, byteBuffer, count, offset);
			buffer.position(0);
			buffer.limit(count);
		} else {
			buffer.clear();
			buffer.put(vertices, offset, count);
			buffer.flip();
			byteBuffer.position(0);
			byteBuffer.limit(buffer.limit() << 2);
		}

		if (isBound) {
			OpenGLRenderer.gl11.glBufferData(GL11.GL_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);

			isDirty = false;
		}
	}

	@Override
	public void bind() {
		OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferHandle);
		if (isDirty) {
			byteBuffer.limit(buffer.limit() * 4);
			OpenGLRenderer.gl11.glBufferData(GL11.GL_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);
			isDirty = false;
		}

		int textureUnit = 0;
		int numAttributes = attributes.size();

		for (int i = 0; i < numAttributes; i++) {
			VertexAttribute attribute = attributes.get(i);

			switch (attribute.usage) {
			case Usage.Position:
				OpenGLRenderer.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				OpenGLRenderer.gl11.glVertexPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, attribute.offset);
				break;

			case Usage.Color:
			case Usage.ColorPacked:
				int colorType = GL10.GL_FLOAT;
				if (attribute.usage == Usage.ColorPacked)
					colorType = GL11.GL_UNSIGNED_BYTE;

				OpenGLRenderer.gl11.glEnableClientState(GL10.GL_COLOR_ARRAY);
				OpenGLRenderer.gl11.glColorPointer(attribute.numComponents, colorType, attributes.vertexSize, attribute.offset);
				break;

			case Usage.Normal:
				OpenGLRenderer.gl11.glEnableClientState(GL10.GL_NORMAL_ARRAY);
				OpenGLRenderer.gl11.glNormalPointer(GL10.GL_FLOAT, attributes.vertexSize, attribute.offset);
				break;

			case Usage.TextureCoordinates:
				OpenGLRenderer.gl11.glClientActiveTexture(GL10.GL_TEXTURE0 + textureUnit);
				OpenGLRenderer.gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				OpenGLRenderer.gl11.glTexCoordPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, attribute.offset);
				textureUnit++;
				break;

			default:
				throw new RuntimeException("unkown vertex attribute type: " + attribute.usage);
			}
		}

		isBound = true;
	}

	@Override
	public void unbind() {
		int textureUnit = 0;
		int numAttributes = attributes.size();

		for (int i = 0; i < numAttributes; i++) {

			VertexAttribute attribute = attributes.get(i);
			switch (attribute.usage) {
			case Usage.Position:
				break; // no-op, we also need a position bound in gles
			case Usage.Color:
			case Usage.ColorPacked:
				OpenGLRenderer.gl11.glDisableClientState(GL11.GL_COLOR_ARRAY);
				break;
			case Usage.Normal:
				OpenGLRenderer.gl11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
				break;
			case Usage.TextureCoordinates:
				OpenGLRenderer.gl11.glClientActiveTexture(GL11.GL_TEXTURE0 + textureUnit);
				OpenGLRenderer.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				textureUnit++;
				break;
			default:
				throw new RuntimeException("unkown vertex attribute type: " + attribute.usage);
			}
		}

		OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		isBound = false;
	}

	public void invalidate() {
		bufferHandle = createBufferObject();
		isDirty = true;
	}

	@Override
	public void dispose() {
		tmpHandle.clear();
		tmpHandle.put(bufferHandle);
		tmpHandle.flip();
		OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		OpenGLRenderer.gl11.glDeleteBuffers(1, tmpHandle);
		bufferHandle = 0;

	}
}
