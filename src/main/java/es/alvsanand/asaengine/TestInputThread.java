package es.alvsanand.asaengine;

import java.util.List;

import android.util.Log;
import es.alvsanand.asaengine.graphics.cameras.LookAtCamera;
import es.alvsanand.asaengine.graphics.objects.Mesh;
import es.alvsanand.asaengine.graphics.objects.Terrain;
import es.alvsanand.asaengine.graphics.objects.keyframed.KeyFramedModel;
import es.alvsanand.asaengine.input.Input;
import es.alvsanand.asaengine.input.InputThread;
import es.alvsanand.asaengine.input.keyboard.KeyEvent;
import es.alvsanand.asaengine.input.touch.TouchEvent;

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
						lookAtCamera.getPosition().x = lookAtCamera.getPosition().x / 10;
						lookAtCamera.getPosition().y = lookAtCamera.getPosition().y / 10;
						lookAtCamera.getPosition().z = lookAtCamera.getPosition().z / 10;
						break;
					case android.view.KeyEvent.KEYCODE_DPAD_DOWN:
						Log.v(TAG, "KEYCODE_DPAD_DOWN");
						lookAtCamera.getPosition().x = lookAtCamera.getPosition().x * 10;
						lookAtCamera.getPosition().y = lookAtCamera.getPosition().y * 10;
						lookAtCamera.getPosition().z = lookAtCamera.getPosition().z * 10;
						break;
					case android.view.KeyEvent.KEYCODE_DPAD_CENTER:
						Log.v(TAG, "KEYCODE_DPAD_DOWN");

						if (!testOpenGLRenderer.getWorld().getObject3ds().get(0).isRunning()) {
							testOpenGLRenderer.getWorld().getObject3ds().get(0).startOrResume();
						} else {
							testOpenGLRenderer.getWorld().getObject3ds().get(0).pause();
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

						Mesh mesh = ((Mesh) (testOpenGLRenderer.getWorld().getObject3ds().get(0)));

						Terrain terrain = ((Terrain) (testOpenGLRenderer.getWorld().getObject3ds().get(1)));

						float y = terrain.calculateTerrainHeight(mesh.getPosition());

						mesh.getPosition().y = y;

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
