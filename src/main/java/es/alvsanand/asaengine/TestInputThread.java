package es.alvsanand.asaengine;

import java.util.List;

import android.util.Log;
import es.alvsanand.asaengine.graphics.Dynamic;
import es.alvsanand.asaengine.graphics.cameras.Camera;
import es.alvsanand.asaengine.graphics.cameras.DynamicLookAtCamera;
import es.alvsanand.asaengine.input.Input;
import es.alvsanand.asaengine.input.InputThread;
import es.alvsanand.asaengine.input.keyboard.KeyEvent;

public class TestInputThread extends InputThread {
	private static String TAG = "InputThread";
	
	private Camera camera;

	public TestInputThread(Camera camera, Input input) {
		super(input);
		
		this.camera = camera;
	}

	@Override
	protected void proccessInput() {
		List<KeyEvent> keyEvents = input.getKeyEvents();
		
		DynamicLookAtCamera dynamicLookAtCamera = (DynamicLookAtCamera)camera;
		
		if(keyEvents!=null && keyEvents.size()>0){
			for(KeyEvent keyevent: keyEvents){
				if (keyevent.type == KeyEvent.KEY_UP) {
					switch(keyevent.keyCode){
//						case android.view.KeyEvent.KEYCODE_DPAD_LEFT:
//							Log.v(TAG, "KEYCODE_DPAD_LEFT");
//							dynamicLookAtCamera.position.x -= 2;
//							break;
//						case android.view.KeyEvent.KEYCODE_DPAD_RIGHT:
//							Log.v(TAG, "KEYCODE_DPAD_RIGHT");
//							dynamicLookAtCamera.position.x += 2;
//							break;
						case android.view.KeyEvent.KEYCODE_DPAD_UP:
							Log.v(TAG, "KEYCODE_DPAD_UP");
							dynamicLookAtCamera.position.x = dynamicLookAtCamera.position.x / 10;
							dynamicLookAtCamera.position.y = dynamicLookAtCamera.position.y / 10;
							dynamicLookAtCamera.position.z = dynamicLookAtCamera.position.z / 10;
							break;
						case android.view.KeyEvent.KEYCODE_DPAD_DOWN:
							Log.v(TAG, "KEYCODE_DPAD_DOWN");
							dynamicLookAtCamera.position.x = dynamicLookAtCamera.position.x * 10;
							dynamicLookAtCamera.position.y = dynamicLookAtCamera.position.y * 10;
							dynamicLookAtCamera.position.z = dynamicLookAtCamera.position.z * 10;
							break;
						case android.view.KeyEvent.KEYCODE_DPAD_CENTER:
							Log.v(TAG, "KEYCODE_DPAD_DOWN");
							
							Dynamic dynamic = ((Dynamic)camera);
							
							if(!dynamic.isRunning()){
								dynamic.startOrResume();
							}
							else{
								dynamic.pause();
							}
							break;
					}
				}
			}
		}
	}

}
