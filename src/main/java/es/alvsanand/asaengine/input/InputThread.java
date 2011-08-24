package es.alvsanand.asaengine.input;

import android.util.Log;

public abstract class InputThread extends Thread {
	private static String TAG = "InputThread";
	
	private static final int SLEEP_TIME = 100;
	
	protected boolean running = true;
	
	protected Input input;
	
	public InputThread(Input input) {
		this.input = input;
	}

	@Override
	public void run() {
		while(running){
			proccessInput();
			
			try {
				sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				Log.e(TAG, "Error while sleeping", e);
			}
		}
	}
	
	public void shutdown(){		
		running = false;
	}

	protected abstract void proccessInput();
}
