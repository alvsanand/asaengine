package es.alvsanand.asaengine;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import es.alvsanand.asaengine.graphics.cameras.LookAtCamera;
import es.alvsanand.asaengine.input.Input;
import es.alvsanand.asaengine.input.InputImpl;
import es.alvsanand.asaengine.math.Vector2;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.XZPointsTrajectory;
import es.alvsanand.asaengine.util.io.FileIO;

public class TestActivity extends Activity{
	private static String TAG = "TestActivity";

	GLSurfaceView glView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i(TAG, "onCreate");	

		//Initializing FileIO
		FileIO.initFileIO(getAssets());
		
		//Initializing OpenGL Window
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);
		
		//Initializing Camera
		loadCamera();
		
		TestOpenGLRenderer openGLRenderer = new TestOpenGLRenderer();
		openGLRenderer.camera = camera;
		
		//Initializing Renderer
		glView.setRenderer(openGLRenderer);
		setContentView(glView); 
		
		Input input = new InputImpl(this, glView, 1, 1);
		
		TestInputManager inputManager = new TestInputManager(input);		
		
		TestGameProcessingThread gameProcessingThread = new TestGameProcessingThread(openGLRenderer, inputManager);
		
		inputManager.setGameProcessingThread(gameProcessingThread);
		inputManager.setOpenGLRenderer(openGLRenderer);
		
		gameProcessingThread.start();
	}
	
	private LookAtCamera camera;
	
	private void loadCamera() {		
		Vector2[] points = new Vector2[] {};

		XZPointsTrajectory pointsTrayectory = new XZPointsTrajectory(0.2f, 0.1f, 3f, points);
		
		camera = new LookAtCamera(new Vector3(8f, 8f, 8f), 67, (float) glView.getWidth() / (float) glView.getHeight(), 0.1f, 100,
				pointsTrayectory);
		((LookAtCamera)camera).setLookAt(new Vector3(0, 0, 0));
	}	
}