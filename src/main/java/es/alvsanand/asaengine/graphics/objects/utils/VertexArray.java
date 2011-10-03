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

	public VertexArray(int numVertexes, VertexAttribute... attributes) {
		this(numVertexes, new VertexAttributes(attributes));
	}

	public VertexArray(int numVertexes, VertexAttributes attributes) {
		this.attributes = attributes;
		
		buffer = BufferUtils.newFloatBuffer(this.attributes.vertexSize * numVertexes / 4);
		
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
	public int getNumVertexes() {
		return buffer.limit() * 4 / attributes.vertexSize;
	}

	public int getNumMaxVertexes() {
		return buffer.capacity() * 4 / attributes.vertexSize;
	}

	@Override
	public void setVertexes(float[] vertexes, int offset, int count) {
		BufferUtils.copy(vertexes, buffer, count, offset);
	}

	@Override
	public void bind() {
		int numAttributes = attributes.size();

		for (int i = 0; i < numAttributes; i++) {
			VertexAttribute attribute = attributes.get(i);

			switch (attribute.usage) {
			case Usage.Position:
				buffer.position(attribute.offset / 4);
				OpenGLRenderer.gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				OpenGLRenderer.gl.glVertexPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, buffer);
				break;

			case Usage.Color:
			case Usage.ColorPacked:
				int colorType = GL10.GL_FLOAT;
				if (attribute.usage == Usage.ColorPacked)
					colorType = GL11.GL_UNSIGNED_BYTE;
				buffer.position(attribute.offset / 4);
				OpenGLRenderer.gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				OpenGLRenderer.gl.glColorPointer(attribute.numComponents, colorType, attributes.vertexSize, buffer);
				break;

			case Usage.Normal:
				buffer.position(attribute.offset / 4);
				OpenGLRenderer.gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
				OpenGLRenderer.gl.glNormalPointer(GL10.GL_FLOAT, attributes.vertexSize, buffer);
				break;

			case Usage.TextureCoordinates:
				OpenGLRenderer.gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				buffer.position(attribute.offset / 4);
				OpenGLRenderer.gl.glTexCoordPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, buffer);
				break;

			default:
				throw new ASARuntimeException("unkown vertex attribute type: " + attribute.usage);
			}
		}

		isBound = true;
	}

	@Override
	public void unbind() {
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
				OpenGLRenderer.gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
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

	private int positionAttributeOffset = -1;

	private int vertexFloatSize = -1;

	@Override
	public float[] getvertex(int index) {
		if (positionAttributeOffset == -1) {
			int numAttributes = attributes.size();

			for (int i = 0; i < numAttributes; i++) {
				VertexAttribute attribute = attributes.get(i);

				switch (attribute.usage) {
				case Usage.Position:
					positionAttributeOffset = attribute.offset;
					break;
				}

				if (positionAttributeOffset != -1) {
					break;
				}
			}

			vertexFloatSize = attributes.vertexSize / 4;
		}

		int indexOffset = index * vertexFloatSize;

		return new float[] { buffer.get(indexOffset + positionAttributeOffset), buffer.get(indexOffset + positionAttributeOffset + 1),
				buffer.get(indexOffset + positionAttributeOffset + 2) };
	}
}
