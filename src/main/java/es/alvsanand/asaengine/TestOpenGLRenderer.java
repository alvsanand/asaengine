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
package es.alvsanand.asaengine;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.lights.Light;
import es.alvsanand.asaengine.graphics.lights.PointLight;
import es.alvsanand.asaengine.graphics.objects.Mesh;
import es.alvsanand.asaengine.graphics.objects.MeshFactory;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.graphics.renderer.World;
import es.alvsanand.asaengine.graphics.textures.Texture;
import es.alvsanand.asaengine.graphics.textures.TextureFactory;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.util.io.error.ASAIOException;

public class TestOpenGLRenderer extends OpenGLRenderer {
	
	Texture SHIP_TEXTURE;
	Texture INVADERSHIP_TEXTURE;
	
	private void loadTextures(){
		SHIP_TEXTURE = TextureFactory.getTextureFromAsset("ship.png");
		INVADERSHIP_TEXTURE = TextureFactory.getTextureFromAsset("invader.png");
	}
	
	World WORLD;
	
	private void loadObjects(){
		ArrayList<Light> lights = new ArrayList<Light>();
		
		PointLight pointLight = new PointLight(new Color(256, 256, 256, 1), new Color(256, 256, 256, 1), new Color(256, 256, 256, 1), new Vector3(30, 30, 30), GL10.GL_LIGHT0);
		
		lights.add(pointLight);

		ArrayList<Object3D> object3ds = new ArrayList<Object3D>();
		
//		Plane plane = new Plane(new Vector3(0,-1f,0), new Color(0f, 256f, 0f, 1.0f), 10, 10);
//		object3ds.add(plane);
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,-2), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,-1), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,0), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,1), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,2), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
		{
			try {
				Mesh mesh = MeshFactory.getMeshFromAsset("invader.obj", MeshFactory.MeshType.OBJ);
				mesh.position = new Vector3(0,0,0);
				mesh.setTexture(INVADERSHIP_TEXTURE);
				
				mesh.rx = 90;
				
				object3ds.add(mesh);
			} catch (ASAIOException e) {
				e.printStackTrace();
			}	
		}
		
		{
			try {
				Mesh mesh = MeshFactory.getMeshFromAsset("ship.obj", MeshFactory.MeshType.OBJ);
				mesh.position = new Vector3(0,0,1.5f);
				mesh.setTexture(SHIP_TEXTURE);
				
				mesh.rx = 90f;
				
				object3ds.add(mesh);
			} catch (ASAIOException e) {
				e.printStackTrace();
			}	
		}
		{
			try {
				Mesh mesh = MeshFactory.getMeshFromAsset("ship.obj", MeshFactory.MeshType.OBJ);
				mesh.position = new Vector3(0,1.5f, 0);
				mesh.setTexture(SHIP_TEXTURE);
				
				mesh.rx = 90f;
				
				object3ds.add(mesh);
			} catch (ASAIOException e) {
				e.printStackTrace();
			}	
		}
		
		WORLD = new World(lights, object3ds);
	}

	@Override
	protected void loadWorld() {
		loadTextures();
		
		loadObjects();
		
		setWorld(WORLD);
		
		loadedWorld = true;
	}

}
