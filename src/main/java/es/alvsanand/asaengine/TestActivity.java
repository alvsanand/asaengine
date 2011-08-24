package es.alvsanand.asaengine;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import es.alvsanand.asaengine.graphics.OpenGLRenderer;
import es.alvsanand.asaengine.graphics.World;
import es.alvsanand.asaengine.graphics.cameras.LookAtCamera;
import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.lights.Light;
import es.alvsanand.asaengine.graphics.lights.PointLight;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.objects.primitives.Cube;
import es.alvsanand.asaengine.input.Input;
import es.alvsanand.asaengine.input.InputImpl;
import es.alvsanand.asaengine.input.InputThread;
import es.alvsanand.asaengine.math.Vector3;

public class TestActivity extends Activity{
	private static String TAG = "TestActivity";

	GLSurfaceView glView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i(TAG, "onCreate");

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);

		ArrayList<Light> lights = new ArrayList<Light>();
		
		PointLight pointLight = new PointLight(new Color(256, 256, 256, 1), new Color(256, 256, 256, 1), new Color(256, 256, 256, 1), new Vector3(30, 30, 30), GL10.GL_LIGHT0);
		
		lights.add(pointLight);

		ArrayList<Object3D> object3ds = new ArrayList<Object3D>();
		
		Cube cube = new Cube(new Vector3(0,0,0), new Color(0f, 256f, 0f, 1.0f), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//		cube.rx = 20;
//		cube.ry = 45;
		
		object3ds.add(cube);
		
		Cube cube2 = new Cube(new Vector3(1f, 0, 0), new Color(0f, 0f, 256f, 1.0f), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//		cube2.rx = 45;
//		cube2.ry = 60;
		
		object3ds.add(cube2);

		World world = new World(lights, object3ds);

		LookAtCamera camera = new LookAtCamera(new Vector3(10, 10, 10), 67,(float) glView.getWidth() / (float)glView.getHeight(), 0.1f, 100);
		((LookAtCamera)camera).setLookAt(new Vector3(0,0,0));
		
		glView.setRenderer(new OpenGLRenderer(world, camera));
		setContentView(glView);
		
		Input input = new InputImpl(this, glView, 1, 1);
		
		InputThread inputThread = new TestInputThread(camera, input);
		inputThread.start();		
	}
}
