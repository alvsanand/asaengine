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

import es.alvsanand.asaengine.graphics.objects.attributes.VertexAttributes;
import es.alvsanand.asaengine.util.Disposable;

public interface VertexData extends Disposable {
	public int getNumVertices ();

	public int getNumMaxVertices ();

	public VertexAttributes getAttributes ();

	public void setVertices (float[] vertices, int offset, int count);

	public FloatBuffer getBuffer ();

	public void bind ();

	public void unbind ();
}
