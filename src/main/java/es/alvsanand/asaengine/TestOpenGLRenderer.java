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
import es.alvsanand.asaengine.graphics.objects.MeshFactory;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.objects.Terrain;
import es.alvsanand.asaengine.graphics.objects.primitives.Cube;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.graphics.renderer.World;
import es.alvsanand.asaengine.math.Vector2;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.XZPointsTrajectory;
import es.alvsanand.asaengine.util.io.error.ASAIOException;

public class TestOpenGLRenderer extends OpenGLRenderer {	
	World WORLD;
	
	private void loadObjects(){
		ArrayList<Light> lights = new ArrayList<Light>();
		
		{
			PointLight pointLight = new PointLight(new Color(0,0,0,1), new Color(1,1,1,1), new Color(1,1,1,1), new Vector3(0, 10, 0), GL10.GL_LIGHT0);
			
			Vector2[] points = new Vector2[] {new Vector2(10f, -10f), new Vector2(-10f, -5f), new Vector2(-10f, 5f), new Vector2(10f, 10f)};
			
			XZPointsTrajectory pointsTrayectory = new XZPointsTrajectory(0.2f, 0.1f, 3f, points);
			
			pointLight.setTrajectory(pointsTrayectory);
			
			pointLight.startOrResume();
			
			lights.add(pointLight);
		}

		ArrayList<Object3D> object3ds = new ArrayList<Object3D>();
		
		Terrain terrain = null;
		
//		Plane plane = new Plane(new Vector3(-50,-1f,-50), new Color(0f, 1f, 0f, 1.0f), 100, 100);
//		object3ds.add(plane);
//		
		for(int i=0; i<50; i++){
			Cube cube = new Cube(new Vector3(i*0.1f,0,0), (i%2==0)?(new Color(0f, 1f, 0f, 1.0f)):(new Color(1f, 0f, 0f, 1.0f)), new Color(1f, 1f, 1f, 1.0f), 0.5f, 0.5f, 0.5f);
			
			Vector2[] points = new Vector2[] { new Vector2(10f, 10f), new Vector2(-3.5f, 10f), new Vector2(-3.5f, -3.5f), new Vector2(10f, -3.5f)};
			
			XZPointsTrajectory pointsTrayectory = new XZPointsTrajectory(0.2f, 0.1f, 3f, points);
			
			cube.trajectory = pointsTrayectory;
			
			object3ds.add(cube);
		}
//		
//		{
//			try {
//				Mesh mesh = MeshFactory.getMeshFromAsset("invader.obj", MeshFactory.MeshType.OBJ);
//				mesh.position = new Vector3(0,0,0);
//				mesh.setTexture(INVADERSHIP_TEXTURE);
//				
//				mesh.rx = 90;
//				
//				object3ds.add(mesh);
//			} catch (ASAIOException e) {
//				e.printStackTrace();
//			}	
//		}
//		
//		{
//			try {
//				Mesh mesh = MeshFactory.getMeshFromAsset("zebra.obj", MeshFactory.MeshType.OBJ);
//				mesh.setPosition(new Vector3(0,0,0));
//				
//				mesh.setSx(0.2f);
//				mesh.setSy(0.2f);
//				mesh.setSz(0.2f);
//				
//				Vector2[] points = new Vector2[] { new Vector2(3f, 3f), new Vector2(-3f, 3f), new Vector2(-3f, -3f), new Vector2(3f, -3f)};
//				
//				XZPointsTrajectory pointsTrayectory = new XZPointsTrajectory(0.2f, 0.1f, 3f, points);
//				
//				mesh.setTrajectory(pointsTrayectory);
//				
//				object3ds.add(mesh);
//			} catch (ASAIOException e) {
//				e.printStackTrace();
//			}	
//		}
		
		{
			try {
				terrain = MeshFactory.getTerrainFromAsset("terrain.obj", MeshFactory.MeshType.OBJ);
//				terrain.setPosition(new Vector3(0,0,0));
				
				terrain.calulateTerrainTriangleAprox();
			} catch (ASAIOException e) {
				e.printStackTrace();
			}	
		}
//		
//		{
//			try {
//				String[] assets = {
//						"luxo_000001.obj",
//						"luxo_000005.obj",
//						"luxo_000010.obj",
//						"luxo_000015.obj",
//						"luxo_000020.obj",
//						"luxo_000025.obj",
//						"luxo_000030.obj",
//						"luxo_000035.obj",
//						"luxo_000040.obj",
//						"luxo_000045.obj",
//						"luxo_000050.obj",
//						"luxo_000055.obj",
//						"luxo_000060.obj"
//						};
//				
//				Animation[] animations = {new Animation(new LinearInterpolator(), assets.length, 0, 0, 3000, 0, Animation.RepeatMode.RESTART)};
//				
//				KeyFramedModel keyFramedModel = KeyFramedModelFactory.getKeyFramedModelFromAsset("keyFramedModel", assets, MeshFactory.MeshType.OBJ, animations);
//				keyFramedModel.setPosition(new Vector3(0,0,1.5f));
//				
//				keyFramedModel.setSx(0.5f);
//				keyFramedModel.setSy(0.5f);
//				keyFramedModel.setSz(0.5f);
//				
//				Vector3[] points = new Vector3[] { new Vector3(5f, 0, 5f), new Vector3(-5f, 3, 5f), new Vector3(-5f, 0, -5f), new Vector3(5f, 3, -5f)};
//				
//				PointsTrajectory pointsTrayectory = new PointsTrajectory(0.2f, 0.1f, 3f, points);
//				
//				keyFramedModel.setTrajectory(pointsTrayectory);
//				
//				object3ds.add(keyFramedModel);
//			} catch (ASAIOException e) {
//				e.printStackTrace();
//			}	
//		}
		
		WORLD = new World(lights, object3ds, terrain);
	}

	@Override
	protected void loadWorld() {
		loadObjects();
		
		world  = WORLD;
		
		loadedWorld = true;
	}

}
