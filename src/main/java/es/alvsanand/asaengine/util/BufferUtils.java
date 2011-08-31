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
package es.alvsanand.asaengine.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class BufferUtils {
	public static FloatBuffer newFloatBuffer(int numFloats) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(numFloats * 4);
		buffer.order(ByteOrder.nativeOrder());
		return buffer.asFloatBuffer();
	}

	public static DoubleBuffer newDoubleBuffer(int numDoubles) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(numDoubles * 8);
		buffer.order(ByteOrder.nativeOrder());
		return buffer.asDoubleBuffer();
	}

	public static ByteBuffer newByteBuffer(int numBytes) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(numBytes);
		buffer.order(ByteOrder.nativeOrder());
		return buffer;
	}

	public static ShortBuffer newShortBuffer(int numShorts) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(numShorts * 2);
		buffer.order(ByteOrder.nativeOrder());
		return buffer.asShortBuffer();
	}

	public static CharBuffer newCharBuffer(int numChars) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(numChars * 2);
		buffer.order(ByteOrder.nativeOrder());
		return buffer.asCharBuffer();
	}

	public static IntBuffer newIntBuffer(int numInts) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(numInts * 4);
		buffer.order(ByteOrder.nativeOrder());
		return buffer.asIntBuffer();
	}

	public static LongBuffer newLongBuffer(int numLongs) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(numLongs * 8);
		buffer.order(ByteOrder.nativeOrder());
		return buffer.asLongBuffer();
	}

	public static byte[] toByta(int data) {
		return new byte[] { (byte) ((data >> 24) & 0xff), (byte) ((data >> 16) & 0xff), (byte) ((data >> 8) & 0xff), (byte) ((data >> 0) & 0xff), };
	}

	public static void copy(float[] src, ByteBuffer dst, int numFloats, int offset) {
		dst.clear();

		dst.limit(numFloats << 2);

		for (int i = offset; i < offset + numFloats && i < src.length; i++) {
			dst.put(toByta(Float.floatToRawIntBits(src[i])));
		}

		dst.flip();
	}

	public static void copy(float[] src, FloatBuffer dst, int numFloats, int offset) {
		dst.clear();

		dst.limit(numFloats);

		dst.put(src, offset, numFloats);

		dst.flip();
	}

	public static void copy(float[] src, IntBuffer dst, int numFloats, int offset) {
		dst.clear();

		dst.limit(numFloats);

		for (int i = offset; i < offset + numFloats && i < src.length; i++) {
			dst.put(Float.floatToRawIntBits(src[i]));
		}

		dst.flip();
	}

	public static void copy(short[] src, ShortBuffer dst, int numShorts, int offset) {
		dst.clear();

		dst.limit(numShorts);

		dst.put(src, offset, numShorts);

		dst.flip();
	}
}
