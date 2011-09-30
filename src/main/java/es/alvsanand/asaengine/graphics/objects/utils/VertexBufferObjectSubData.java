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
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import es.alvsanand.asaengine.error.ASARuntimeException;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttribute;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes;
import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes.Usage;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.util.BufferUtils;

public class VertexBufferObjectSubData implements VertexData {
	final static IntBuffer tmpHandle = BufferUtils.newIntBuffer(1);

	final VertexAttributes attributes;
	final FloatBuffer buffer;
	int bufferHandle;
	final boolean isStatic;
	final int usage;
	boolean isDirty = false;
	boolean isBound = false;

	public VertexBufferObjectSubData(boolean isStatic, int numVertexes, VertexAttribute... attributes) {
		this.isStatic = isStatic;
		this.attributes = new VertexAttributes(attributes);

		buffer = BufferUtils.newFloatBuffer(this.attributes.vertexSize * numVertexes / 4);
		buffer.flip();
		
		bufferHandle = createBufferObject();

		usage = isStatic ? GL11.GL_STATIC_DRAW : GL11.GL_DYNAMIC_DRAW;
	}

	private int createBufferObject() {
		OpenGLRenderer.gl11.glGenBuffers(1, tmpHandle);
		OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, tmpHandle.get(0));
		OpenGLRenderer.gl11.glBufferData(GL11.GL_ARRAY_BUFFER, buffer.capacity() * 4, null, usage);
		OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		return tmpHandle.get(0);
	}

	@Override
	public VertexAttributes getAttributes() {
		return attributes;
	}

	@Override
	public int getNumVertexes() {
		return buffer.limit() * 4 / attributes.vertexSize;
	}

	public int getNumMaxVertexes() {
		return buffer.capacity() * 4 / attributes.vertexSize;
	}

	@Override
	public FloatBuffer getBuffer() {
		isDirty = true;
		return buffer;
	}

	@Override
	public void setVertexes(float[] Vertexes, int offset, int count) {
		isDirty = true;

		BufferUtils.copy(Vertexes, buffer, count, offset);

		if (isBound) {
			OpenGLRenderer.gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, buffer.limit() * 4, buffer);
			isDirty = false;
		}
	}

	@Override
	public void bind() {
		OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferHandle);
		
		if (isDirty) {
			OpenGLRenderer.gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, buffer.limit() * 4, buffer);
			isDirty = false;
		}

		int numAttributes = attributes.size();

		for (int i = 0; i < numAttributes; i++) {
			VertexAttribute attribute = attributes.get(i);

			switch (attribute.usage) {
			case Usage.Position:
				OpenGLRenderer.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				OpenGLRenderer.gl11.glVertexPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, attribute.offset / 4);
				break;

			case Usage.Color:
			case Usage.ColorPacked:
				int colorType = GL10.GL_FLOAT;
				if (attribute.usage == Usage.ColorPacked)
					colorType = GL11.GL_UNSIGNED_BYTE;

				OpenGLRenderer.gl11.glEnableClientState(GL10.GL_COLOR_ARRAY);
				OpenGLRenderer.gl11.glColorPointer(attribute.numComponents, colorType, attributes.vertexSize, attribute.offset / 4);
				break;

			case Usage.Normal:
				OpenGLRenderer.gl11.glEnableClientState(GL10.GL_NORMAL_ARRAY);
				OpenGLRenderer.gl11.glNormalPointer(GL10.GL_FLOAT, attributes.vertexSize, attribute.offset / 4);
				break;

			case Usage.TextureCoordinates:
				OpenGLRenderer.gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				OpenGLRenderer.gl11.glTexCoordPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, attribute.offset / 4);
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
				break; // no-op, we also need a position bound in gles
			case Usage.Color:
			case Usage.ColorPacked:
				OpenGLRenderer.gl11.glDisableClientState(GL11.GL_COLOR_ARRAY);
				break;
			case Usage.Normal:
				OpenGLRenderer.gl11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
				break;
			case Usage.TextureCoordinates:
				OpenGLRenderer.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				break;
			default:
				throw new ASARuntimeException("unkown vertex attribute type: " + attribute.usage);
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

	public int getBufferHandle() {
		return bufferHandle;
	}

	private int positionAttributeOffset = -1;
	
	@Override
	public float[] getvertex(int index) {
		if(positionAttributeOffset==-1){		
			int numAttributes = attributes.size();
			
			for (int i = 0; i < numAttributes; i++) {
				VertexAttribute attribute = attributes.get(i);
	
				switch (attribute.usage) {
				case Usage.Position:
					positionAttributeOffset = attribute.offset;
					break;
				}
				
				if(positionAttributeOffset!=-1){
					break;
				}
			}
		}
		
		return new float[]{buffer.get(index), buffer.get(index+1), buffer.get(index+2)};
	}
}
