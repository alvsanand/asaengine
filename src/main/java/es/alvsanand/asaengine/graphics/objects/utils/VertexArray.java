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

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import es.alvsanand.asaengine.error.ASARuntimeException;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttribute;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes.Usage;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.util.BufferUtils;

public class VertexArray implements VertexData {
	final VertexAttributes attributes;
	final FloatBuffer buffer;
	boolean isBound = false;

	public VertexArray(int numVertices, VertexAttribute... attributes) {
		this(numVertices, new VertexAttributes(attributes));
	}

	public VertexArray(int numVertices, VertexAttributes attributes) {
		this.attributes = attributes;
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(this.attributes.vertexSize * numVertices);
		byteBuffer.order(ByteOrder.nativeOrder());
		
		buffer = byteBuffer.asFloatBuffer();		
		buffer.flip();
	}

	@Override
	public void dispose() {

	}

	@Override
	public FloatBuffer getBuffer() {
		return buffer;
	}

	@Override
	public int getNumVertices() {
		return buffer.limit() * 4 / attributes.vertexSize;
	}

	public int getNumMaxVertices() {
		return buffer.capacity() * 4 / attributes.vertexSize;
	}

	@Override
	public void setVertices(float[] vertices, int offset, int count) {
		BufferUtils.copy(vertices, buffer, count, offset);
		buffer.position(0);
		buffer.limit(count);
	}

	@Override
	public void bind() {
		int textureUnit = 0;
		int numAttributes = attributes.size();

		for (int i = 0; i < numAttributes; i++) {
			VertexAttribute attribute = attributes.get(i);

			switch (attribute.usage) {
			case Usage.Position:
				buffer.position(attribute.offset);
				OpenGLRenderer.gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				OpenGLRenderer.gl.glVertexPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, buffer);
				break;

			case Usage.Color:
			case Usage.ColorPacked:
				int colorType = GL10.GL_FLOAT;
				if (attribute.usage == Usage.ColorPacked)
					colorType = GL11.GL_UNSIGNED_BYTE;
				buffer.position(attribute.offset);
				OpenGLRenderer.gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				OpenGLRenderer.gl.glColorPointer(attribute.numComponents, colorType, attributes.vertexSize, buffer);
				break;

			case Usage.Normal:
				buffer.position(attribute.offset);
				OpenGLRenderer.gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
				OpenGLRenderer.gl.glNormalPointer(GL10.GL_FLOAT, attributes.vertexSize, buffer);
				break;

			case Usage.TextureCoordinates:
				OpenGLRenderer.gl.glClientActiveTexture(GL10.GL_TEXTURE0 + textureUnit);
				OpenGLRenderer.gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				buffer.position(attribute.offset);
				OpenGLRenderer.gl.glTexCoordPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, buffer);
				textureUnit++;
				break;

			default:
				throw new ASARuntimeException("unkown vertex attribute type: " + attribute.usage);
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
				break; 
			case Usage.Color:
			case Usage.ColorPacked:
				OpenGLRenderer.gl.glDisableClientState(GL11.GL_COLOR_ARRAY);
				break;
			case Usage.Normal:
				OpenGLRenderer.gl.glDisableClientState(GL11.GL_NORMAL_ARRAY);
				break;
			case Usage.TextureCoordinates:
				OpenGLRenderer.gl.glClientActiveTexture(GL11.GL_TEXTURE0 + textureUnit);
				OpenGLRenderer.gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				textureUnit++;
				break;
			default:
				throw new ASARuntimeException("unkown vertex attribute type: " + attribute.usage);
			}
		}
		buffer.position(0);
		isBound = false;
	}

	@Override
	public VertexAttributes getAttributes() {
		return attributes;
	}
}
