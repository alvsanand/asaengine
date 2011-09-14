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
package es.alvsanand.asaengine.graphics.objects.keyframed;

import es.alvsanand.asaengine.graphics.objects.Mesh;
import es.alvsanand.asaengine.graphics.textures.Texture;
import es.alvsanand.asaengine.util.Disposable;

public class KeyFrame implements Disposable{
	private Mesh mesh;
	private int frameNumber;

	public KeyFrame() {
	}


	public KeyFrame(Mesh mesh, int frameNumber) {
		this.mesh = mesh;
		this.frameNumber = frameNumber;
	}


	public Mesh getMesh() {
		return mesh;
	}


	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}


	public int getFrameNumber() {
		return frameNumber;
	}


	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}
	
	public void render(){
		mesh.render();
	}
	
	public void setTexture(Texture texture){
		mesh.setTexture(texture);
	}
	
	@Override
	public void dispose() {
		mesh.dispose();		
	}
}
