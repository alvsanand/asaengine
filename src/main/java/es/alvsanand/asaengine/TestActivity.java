package es.alvsanand.asaengine;

import java.io.InputStream;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import es.alvsanand.asaengine.graphics.cameras.DynamicLookAtCamera;
import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.lights.Light;
import es.alvsanand.asaengine.graphics.lights.PointLight;
import es.alvsanand.asaengine.graphics.objects.Mesh;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.objects.loaders.obj.ObjLoader;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.graphics.renderer.World;
import es.alvsanand.asaengine.input.Input;
import es.alvsanand.asaengine.input.InputImpl;
import es.alvsanand.asaengine.input.InputThread;
import es.alvsanand.asaengine.math.Vector2;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.XZPointsTrajectory;

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
		
//		Plane plane = new Plane(new Vector3(0,-1f,0), new Color(0f, 256f, 0f, 1.0f), 10, 10);
//		object3ds.add(plane);
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,-2), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,-1), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,0), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,1), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
//		for(int i=0; i<5; i++){
//			Cube cube = new Cube(new Vector3(i-2,0,2), (i%2==0)?(new Color(0f, 256f, 0f, 1.0f)):(new Color(256f, 0f, 0f, 1.0f)), new Color(256f, 256f, 256f, 1.0f), 0.5f, 0.5f, 0.5f);
//			object3ds.add(cube);
//		}
//		
		for(int i=0; i<3; i++){			
			InputStream inputStream = getResources().openRawResource(R.raw.ship);

			Mesh mesh = ObjLoader.loadObj(inputStream);
			mesh.position = new Vector3(i*1.5f,0,0);

			object3ds.add(mesh);
		}		

		World world = new World(lights, object3ds);

		Vector2[] points = new Vector2[] { new Vector2(5, 5), new Vector2(-5, 5), new Vector2(-5, -5), new Vector2(5, -5)};

		XZPointsTrajectory pointsTrayectory = new XZPointsTrajectory(0.3f, 0.5f, 5f, points);
		
		DynamicLookAtCamera camera = new DynamicLookAtCamera(new Vector3(5, 5, 5), 67,(float) glView.getWidth() / (float)glView.getHeight(), 0.1f, 100, pointsTrayectory);
		camera.lookAt = new Vector3(0,0,0);
		
		glView.setRenderer(new OpenGLRenderer(world, camera));
		setContentView(glView);
		
		Input input = new InputImpl(this, glView, 1, 1);
		
		InputThread inputThread = new TestInputThread(camera, input);
		inputThread.start();		
	}
}
