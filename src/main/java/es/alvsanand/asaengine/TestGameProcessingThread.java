package es.alvsanand.asaengine;

import android.util.Log;
import es.alvsanand.asaengine.graphics.Dynamic;
import es.alvsanand.asaengine.graphics.lights.Light;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.objects.Terrain;
import es.alvsanand.asaengine.graphics.objects.error.OutOfTerrainException;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.managers.GameProcessingThread;
import es.alvsanand.asaengine.managers.InputManager;

public class TestGameProcessingThread extends GameProcessingThread {
	public TestGameProcessingThread(OpenGLRenderer openGLRenderer, InputManager inputManager) {
		super(openGLRenderer, inputManager);
	}

	private static String TAG = "InputThread";

	@Override
	protected void updateState() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateAI() {		
		int objectSize = openGLRenderer.world.object3ds.size();
		
		for(int i=0; i<objectSize; i++){
			Object3D object3d = openGLRenderer.world.object3ds.get(i);
			
			if(object3d.isRunning()){
				object3d.updatePosition();
			}
		}
		
		
		int lightSize = openGLRenderer.world.lights.size();
		
		for(int i=0; i<lightSize; i++){
			Light light = openGLRenderer.world.lights.get(i);
			
			if(light instanceof Dynamic && ((Dynamic) light).isRunning()){
				((Dynamic) light).updatePosition();
			}
		}
	}

	@Override
	protected void updatePhysics() {
		if (openGLRenderer != null && openGLRenderer.world != null) {
			Terrain terrain = openGLRenderer.world.terrain;
			
			if(terrain!=null){
				int len = openGLRenderer.world.object3ds.size();
	
				for (int i = 0; i < len; i++) {
					Object3D object3d = ((Object3D) (openGLRenderer.world.object3ds.get(i)));
	
					try {
						float width = 1f;
						float height = 1f;
						float length = 1f;
	
						float y = 2 + Terrain.calculateTerrainHeight(terrain, object3d.position.x + (width / 2), object3d.position.y,
								object3d.position.z + (length / 2));
	
						object3d.position.y = y;
					} catch (OutOfTerrainException e) {
						Log.v(TAG, "OutOfTerrain");
					}
				}
			}
		}
	}

	@Override
	protected void updateAnimations() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateSound() {
		// TODO Auto-generated method stub

	}
}
