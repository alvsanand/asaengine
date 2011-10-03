package es.alvsanand.asaengine;

import java.util.List;

import android.util.Log;
import es.alvsanand.asaengine.graphics.cameras.LookAtCamera;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.objects.Terrain;
import es.alvsanand.asaengine.graphics.objects.error.OutOfTerrainException;
import es.alvsanand.asaengine.graphics.objects.keyframed.KeyFramedModel;
import es.alvsanand.asaengine.input.Input;
import es.alvsanand.asaengine.input.InputThread;
import es.alvsanand.asaengine.input.keyboard.KeyEvent;
import es.alvsanand.asaengine.input.touch.TouchEvent;
import es.alvsanand.asaengine.math.collision.BoundingBox;

public class TestInputThread extends InputThread {
	private static String TAG = "InputThread";

	private LookAtCamera camera;

	private TestOpenGLRenderer testOpenGLRenderer;

	public TestInputThread(LookAtCamera camera, TestOpenGLRenderer testOpenGLRenderer, Input input) {
		super(input);

		this.camera = camera;
		this.testOpenGLRenderer = testOpenGLRenderer;
	}

	@Override
	protected void proccessInput() {
		List<KeyEvent> keyEvents = input.getKeyEvents();

		LookAtCamera lookAtCamera = (LookAtCamera) camera;

		if (keyEvents != null && keyEvents.size() > 0) {
			for (KeyEvent keyevent : keyEvents) {
				if (keyevent.type == KeyEvent.KEY_UP) {
					switch (keyevent.keyCode) {
					case android.view.KeyEvent.KEYCODE_DPAD_LEFT: {
						Log.v(TAG, "KEYCODE_DPAD_LEFT");

						KeyFramedModel framedModel = ((KeyFramedModel) (testOpenGLRenderer.getWorld().getObject3ds().get(0)));

						framedModel.pause();

					}
						break;
					case android.view.KeyEvent.KEYCODE_DPAD_RIGHT: {
						Log.v(TAG, "KEYCODE_DPAD_RIGHT");

						KeyFramedModel framedModel = ((KeyFramedModel) (testOpenGLRenderer.getWorld().getObject3ds().get(0)));

						if (framedModel.isStarted()) {
							framedModel.resume();
						} else {
							framedModel.start();
						}

						break;
					}
					case android.view.KeyEvent.KEYCODE_DPAD_UP:
						Log.v(TAG, "KEYCODE_DPAD_UP");
						lookAtCamera.getPosition().x = lookAtCamera.getPosition().x / 2.5f;
						lookAtCamera.getPosition().y = lookAtCamera.getPosition().y / 2.5f;
						lookAtCamera.getPosition().z = lookAtCamera.getPosition().z / 2.5f;
						break;
					case android.view.KeyEvent.KEYCODE_DPAD_DOWN:
						Log.v(TAG, "KEYCODE_DPAD_DOWN");
						lookAtCamera.getPosition().x = lookAtCamera.getPosition().x * 2.5f;
						lookAtCamera.getPosition().y = lookAtCamera.getPosition().y * 2.5f;
						lookAtCamera.getPosition().z = lookAtCamera.getPosition().z * 2.5f;
						break;
					case android.view.KeyEvent.KEYCODE_DPAD_CENTER:
						Log.v(TAG, "KEYCODE_DPAD_DOWN");
						
						int len = testOpenGLRenderer.getWorld().getObject3ds().size();
						
						for(int i=0; i<len; i++){
							Object3D object3d = ((Object3D) (testOpenGLRenderer.getWorld().getObject3ds().get(i)));

							if (!object3d.isRunning()) {
								object3d.startOrResume();
							} else {
								object3d.pause();
							}
						}
						break;
					case android.view.KeyEvent.KEYCODE_1:
						Log.v(TAG, "KEYCODE_1");

						if (camera.getFar() > 5)
							camera.setFar(camera.getFar() - 5);

						break;
					case android.view.KeyEvent.KEYCODE_2:
						Log.v(TAG, "KEYCODE_2");

						if (camera.getFar() < 100)
							camera.setFar(camera.getFar() + 5);

						break;
					case android.view.KeyEvent.KEYCODE_3:
						Log.v(TAG, "KEYCODE_3");

						break;
					}
				}
			}
		}
		
		if(testOpenGLRenderer!=null && testOpenGLRenderer.getWorld()!=null)
		{
			Log.v(TAG, "Terrain Begin");
			
			Terrain terrain = testOpenGLRenderer.getWorld().getTerrain();
			
			int len = testOpenGLRenderer.getWorld().getObject3ds().size();
			
			for(int i=0; i<len; i++){
				Object3D object3d = ((Object3D) (testOpenGLRenderer.getWorld().getObject3ds().get(i)));
				
				try {
					float width = 1f;
					float height = 1f;
					float length = 1f;
					
					float y = 2 + terrain.calculateTerrainHeight(object3d.getPosition().x + (width/2), object3d.getPosition().y, object3d.getPosition().z + (length/2) );
	
					object3d.getPosition().y = y;
				} catch (OutOfTerrainException e) {
					Log.v(TAG, "OutOfTerrain");
				}
			}
			Log.v(TAG, "Terrain End");
		}

		List<TouchEvent> touchEvents = input.getTouchEvents();
		if (touchEvents != null && touchEvents.size() > 0) {
			for (TouchEvent touchEvent : touchEvents) {
				if (touchEvent.type == TouchEvent.TOUCH_UP) {
					Log.v(TAG, "TOUCH_UP");

					if (!testOpenGLRenderer.getWorld().getObject3ds().get(0).isRunning()) {
						testOpenGLRenderer.getWorld().getObject3ds().get(0).startOrResume();
					} else {
						testOpenGLRenderer.getWorld().getObject3ds().get(0).pause();
					}
					break;
				}
			}
		}
	}
}
