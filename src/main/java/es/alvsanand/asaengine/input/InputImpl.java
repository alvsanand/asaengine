package es.alvsanand.asaengine.input;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;
import es.alvsanand.asaengine.input.accelerometer.AccelerometerHandler;
import es.alvsanand.asaengine.input.keyboard.KeyEvent;
import es.alvsanand.asaengine.input.keyboard.KeyboardHandler;
import es.alvsanand.asaengine.input.touch.MultiTouchHandler;
import es.alvsanand.asaengine.input.touch.SingleTouchHandler;
import es.alvsanand.asaengine.input.touch.TouchEvent;
import es.alvsanand.asaengine.input.touch.TouchHandler;

public class InputImpl implements Input {
	protected AccelerometerHandler accelHandler;
	protected KeyboardHandler keyHandler;
	protected TouchHandler touchHandler;

	public InputImpl(Context context, View view, float scaleX, float scaleY) {
		accelHandler = new AccelerometerHandler(context);
		keyHandler = new KeyboardHandler(view);
		if (Integer.parseInt(VERSION.SDK) < 5)
			touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
		else
			touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
	}

	@Override
	public boolean isKeyPressed(int keyCode) {
		return keyHandler.isKeyPressed(keyCode);
	}

	@Override
	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}

	@Override
	public int getTouchX(int pointer) {
		return touchHandler.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		return touchHandler.getTouchY(pointer);
	}

	@Override
	public float getAccelX() {
		return accelHandler.getAccelX();
	}

	@Override
	public float getAccelY() {
		return accelHandler.getAccelY();
	}

	@Override
	public float getAccelZ() {
		return accelHandler.getAccelZ();
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}

	@Override
	public List<KeyEvent> getKeyEvents() {
		return keyHandler.getKeyEvents();
	}
}
