package es.alvsanand.asaengine;

import java.util.List;

import android.util.Log;
import es.alvsanand.asaengine.graphics.cameras.LookAtCamera;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.objects.keyframed.KeyFramedModel;
import es.alvsanand.asaengine.input.Input;
import es.alvsanand.asaengine.input.keyboard.KeyEvent;
import es.alvsanand.asaengine.input.touch.TouchEvent;
import es.alvsanand.asaengine.managers.InputManager;

public class TestInputManager extends InputManager {
	private static String TAG = "InputThread";
	
	public TestInputManager(Input input) {
		super(input);
	}

	@Override
	protected void updateInput() {
		List<KeyEvent> keyEvents = input.getKeyEvents();

		LookAtCamera lookAtCamera = (LookAtCamera) openGLRenderer.camera;
		
		if (keyEvents != null && keyEvents.size() > 0) {
			for (KeyEvent keyevent : keyEvents) {
				if (keyevent.type == KeyEvent.KEY_UP) {
					switch (keyevent.keyCode) {
					case android.view.KeyEvent.KEYCODE_DPAD_LEFT: {
						Log.v(TAG, "KEYCODE_DPAD_LEFT");

						KeyFramedModel framedModel = ((KeyFramedModel) (openGLRenderer.world.object3ds.get(0)));

						framedModel.pause();

					}
						break;
					case android.view.KeyEvent.KEYCODE_DPAD_RIGHT: {
						Log.v(TAG, "KEYCODE_DPAD_RIGHT");

						KeyFramedModel framedModel = ((KeyFramedModel) (openGLRenderer.world.object3ds.get(0)));

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
						
						int len = openGLRenderer.world.object3ds.size();
						
						for(int i=0; i<len; i++){
							Object3D object3d = ((Object3D) (openGLRenderer.world.object3ds.get(i)));

							if (!object3d.isRunning()) {
								object3d.startOrResume();
							} else {
								object3d.pause();
							}
						}
						break;
					case android.view.KeyEvent.KEYCODE_1:
						Log.v(TAG, "KEYCODE_1");

						if (lookAtCamera.getFar() > 5)
							lookAtCamera.setFar(lookAtCamera.getFar() - 5);

						break;
					case android.view.KeyEvent.KEYCODE_2:
						Log.v(TAG, "KEYCODE_2");

						if (lookAtCamera.getFar() < 100)
							lookAtCamera.setFar(lookAtCamera.getFar() + 5);

						break;
					case android.view.KeyEvent.KEYCODE_3:
						Log.v(TAG, "KEYCODE_3");

						break;
					}
				}
			}
		}

		List<TouchEvent> touchEvents = input.getTouchEvents();
		if (touchEvents != null && touchEvents.size() > 0) {
			for (TouchEvent touchEvent : touchEvents) {
				if (touchEvent.type == TouchEvent.TOUCH_UP) {
					Log.v(TAG, "TOUCH_UP");
					
					int len = openGLRenderer.world.object3ds.size();
					
					for(int i=0; i<len; i++){
						Object3D object3d = ((Object3D) (openGLRenderer.world.object3ds.get(i)));

						if (!object3d.isRunning()) {
							object3d.startOrResume();
						} else {
							object3d.pause();
						}
					}
					break;
				}
			}
		}
	}
}
