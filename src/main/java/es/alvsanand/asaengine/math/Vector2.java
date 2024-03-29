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
package es.alvsanand.asaengine.math;

import java.io.Serializable;


public class Vector2 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4267646046944070854L;
	
	/** the x-component of this vector **/
	public float x;
	/** the x-component of this vector **/
	public float y;

	public Vector2() {
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(float[] values) {
		this.x = values[0];
		this.y = values[1];
	}
}