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

import java.nio.ShortBuffer;

import es.alvsanand.asaengine.util.Disposable;

public interface IndexData extends Disposable {
	public int getNumindexes ();

	public int getNumMaxindexes ();

	public void setindexes (short[] indexes, int offset, int count);

	public ShortBuffer getBuffer ();

	public void bind ();

	public void unbind ();

	public void invalidate ();
	
	public short getVertexIndex(int index);
}
