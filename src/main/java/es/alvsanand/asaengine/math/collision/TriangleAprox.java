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
package es.alvsanand.asaengine.math.collision;

import es.alvsanand.asaengine.math.Vector3;

public class TriangleAprox {
	public Vector3 max = new Vector3();
	public Vector3 min = new Vector3();
	public int triangleIndex; // index of triangle
	
	public TriangleAprox(){		
		min.x = 100000;
		min.y = 100000;
		min.z = 100000;
		
		max.x = -100000;
		max.y = -100000;
		max.z = -100000;
	}
}
