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

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL11;

import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer.GL_TYPE;
import es.alvsanand.asaengine.util.BufferUtils;

public class IndexBufferObjectSubData implements IndexData {
	final static IntBuffer tmpHandle = BufferUtils.newIntBuffer(1);

	ShortBuffer buffer;
	int bufferHandle;
	boolean isDirty = true;
	boolean isBound = false;
	final int usage;

	public IndexBufferObjectSubData(boolean isStatic, int maxIndices) {
		buffer = BufferUtils.newShortBuffer(maxIndices / 2);
		buffer.flip();

		bufferHandle = createBufferObject();

		usage = isStatic ? GL11.GL_STATIC_DRAW : GL11.GL_DYNAMIC_DRAW;
	}

	public IndexBufferObjectSubData(int maxIndices) {
		buffer = BufferUtils.newShortBuffer(maxIndices);
		buffer.flip();

		bufferHandle = createBufferObject();

		usage = GL11.GL_STATIC_DRAW;
	}

	private int createBufferObject() {
		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			OpenGLRenderer.gl11.glGenBuffers(1, tmpHandle);
			OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, tmpHandle.get(0));
			OpenGLRenderer.gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, buffer.capacity() * 2, null, usage);
			OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
			return tmpHandle.get(0);
		}

		return 0;
	}

	public int getNumIndices() {
		return buffer.limit() * 2;
	}

	public int getNumMaxIndices() {
		return buffer.capacity() * 2;
	}

	public ShortBuffer getBuffer() {
		isDirty = true;
		return buffer;
	}

	public void setIndices(short[] indices, int offset, int count) {
		isDirty = true;

		BufferUtils.copy(indices, buffer, count, offset);

		if (isBound && OpenGLRenderer.glType == GL_TYPE.GL11) {
			OpenGLRenderer.gl11.glBufferSubData(GL11.GL_ELEMENT_ARRAY_BUFFER, 0, buffer.limit() * 2, buffer);

			isDirty = false;
		}
	}

	@Override
	public void bind() {
		OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, bufferHandle);
		if (isDirty && OpenGLRenderer.glType == GL_TYPE.GL11) {
			OpenGLRenderer.gl11.glBufferSubData(GL11.GL_ELEMENT_ARRAY_BUFFER, 0, buffer.limit() * 2, buffer);

			isDirty = false;
		}

		isBound = true;
	}

	@Override
	public void unbind() {
		if (OpenGLRenderer.glType == GL_TYPE.GL11) {
			OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		}
		isBound = false;
	}

	@Override
	public void invalidate() {
		bufferHandle = createBufferObject();
		isDirty = true;
	}

	@Override
	public void dispose() {
		tmpHandle.clear();
		tmpHandle.put(bufferHandle);
		tmpHandle.flip();
		OpenGLRenderer.gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		OpenGLRenderer.gl11.glDeleteBuffers(1, tmpHandle);
		bufferHandle = 0;
	}
}
