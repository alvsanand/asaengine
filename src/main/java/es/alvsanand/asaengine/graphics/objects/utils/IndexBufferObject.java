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
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL11;

import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer.GL_TYPE;

public class IndexBufferObject implements IndexData {
	final static IntBuffer tmpHandle = es.alvsanand.asaengine.util.BufferUtils.newIntBuffer(1);

	ShortBuffer buffer;
	ByteBuffer byteBuffer;
	int bufferHandle;
	final boolean isDirect;
	boolean isDirty = true;
	boolean isBound = false;
	final int usage;

	public IndexBufferObject (boolean isStatic, int maxIndices) {
		byteBuffer = ByteBuffer.allocateDirect(maxIndices * 2);
		byteBuffer.order(ByteOrder.nativeOrder());
		isDirect = true;

		buffer = byteBuffer.asShortBuffer();
		buffer.flip();
		byteBuffer.flip();
		bufferHandle = createBufferObject();
		usage = isStatic ? GL11.GL_STATIC_DRAW : GL11.GL_DYNAMIC_DRAW;
	}

	public IndexBufferObject (int maxIndices) {
		byteBuffer = ByteBuffer.allocateDirect(maxIndices * 2);
		byteBuffer.order(ByteOrder.nativeOrder());
		this.isDirect = true;

		buffer = byteBuffer.asShortBuffer();
		buffer.flip();
		byteBuffer.flip();
		bufferHandle = createBufferObject();
		usage = GL11.GL_STATIC_DRAW;
	}

	private int createBufferObject () {
		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			
			OpenGLRenderer.gl11.glGenBuffers(1, tmpHandle);
			return tmpHandle.get(0);
		}

		return 0;
	}

	public int getNumIndices () {
		return buffer.limit();
	}

	public int getNumMaxIndices () {
		return buffer.capacity();
	}

	public void setIndices (short[] indices, int offset, int count) {
		isDirty = true;
		
		buffer.clear();
		buffer.put(indices);
		buffer.flip();
		byteBuffer.position(0);
		byteBuffer.limit(count << 1);

		if (isBound) {
			if (OpenGLRenderer.glType == GL_TYPE.GL11) {
				OpenGLRenderer.gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);
			}
			isDirty = false;
		}
	}

	public ShortBuffer getBuffer () {
		isDirty = true;
		return buffer;
	}

	public void bind () {
		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, bufferHandle);
			if (isDirty) {
				byteBuffer.limit(buffer.limit() * 2);
				OpenGLRenderer.gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, byteBuffer.limit(), byteBuffer, usage);
				isDirty = false;
			}
		}
		isBound = true;
	}

	public void unbind () {
		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		}
		isBound = false;
	}

	public void invalidate () {
		bufferHandle = createBufferObject();
		isDirty = true;
	}

	public void dispose () {
		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			tmpHandle.clear();
			tmpHandle.put(bufferHandle);
			tmpHandle.flip();
			OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
			OpenGLRenderer.gl11.glDeleteBuffers(1, tmpHandle);
			bufferHandle = 0;
		}
	}
}
