package es.alvsanand.asaengine.managers;

import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.input.Input;

public abstract class InputManager{	
	protected Input input;
	
	protected GameProcessingThread gameProcessingThread;
	
	protected OpenGLRenderer openGLRenderer;
	
	public InputManager(Input input) {		
		this.input = input; 
	}

	protected abstract void updateInput();

	public void setGameProcessingThread(GameProcessingThread gameProcessingThread) {
		this.gameProcessingThread = gameProcessingThread;
	}

	public void setOpenGLRenderer(OpenGLRenderer openGLRenderer) {
		this.openGLRenderer = openGLRenderer;
	}
}
