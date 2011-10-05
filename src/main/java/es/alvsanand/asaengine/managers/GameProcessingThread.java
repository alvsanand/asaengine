package es.alvsanand.asaengine.managers;

import android.util.Log;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;

public abstract class GameProcessingThread extends Thread {
	protected long lastTimeCalculated;
	
	private static String TAG = "GameProcessingThread";
	
	private static final int SLEEP_TIME = 1000/20;
	
	protected boolean running = true;
	
	protected OpenGLRenderer openGLRenderer;
	
	protected InputManager inputManager;
	
	public GameProcessingThread(OpenGLRenderer openGLRenderer, InputManager inputManager) {
		super("GameProcessingThread");
		
		this.openGLRenderer = openGLRenderer;
		this.inputManager = inputManager;
	}

	@Override
	public void run() {
		//Waiting until World is loaded
		while(!openGLRenderer.loadedWorld){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			
			if(!running){
				return;
			}
		}
		
		while(running){
			updateState();
			inputManager.updateInput();
			updateAI();
			updatePhysics();
			updateAnimations();
			updateSound();
			
			try {
				sleep(getDeltaTime());
			} catch (InterruptedException e) {
				Log.e(TAG, "Error while sleeping", e);
			}
		}
	}
	
	private long getDeltaTime(){		
		long now = System.currentTimeMillis();

		if (lastTimeCalculated == 0) {
			lastTimeCalculated = now;
		}

		long millisTime = now - this.lastTimeCalculated;

		this.lastTimeCalculated = now;
		
		if(SLEEP_TIME>millisTime){
			return SLEEP_TIME-millisTime;
		} 
		else{
			return 0;
		}		
	}
	
	public void shutdown(){		
		running = false;
	}

	protected abstract void updateState();
	protected abstract void updateAI();
	protected abstract void updatePhysics();
	protected abstract void updateAnimations();
	protected abstract void updateSound();
}
