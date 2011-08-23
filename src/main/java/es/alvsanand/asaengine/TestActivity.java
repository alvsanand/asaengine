package es.alvsanand.asaengine;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import es.alvsanand.asaengine.graphics.Object3D;
import es.alvsanand.asaengine.graphics.World;
import es.alvsanand.asaengine.graphics.cameras.Camera;
import es.alvsanand.asaengine.graphics.cameras.LookAtCamera;
import es.alvsanand.asaengine.graphics.lights.AmbientLight;
import es.alvsanand.asaengine.graphics.lights.Light;
import es.alvsanand.asaengine.math.Vector3;

public class TestActivity extends Activity implements Renderer {
	private static String TAG = "asaengine";

	GLSurfaceView glView;

	World world;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);
		glView.setRenderer(this);
		setContentView(glView);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Camera camera = new LookAtCamera(new Vector3(0, 6, 2), 67, glView.getWidth() / (float) glView.getHeight(), 0.1f, 100);

		camera.getPosition().set(0, 6, 2);

		ArrayList<Light> lights = new ArrayList<Light>();
		lights.add(new AmbientLight());
		
		ArrayList<Object3D> object3ds = new ArrayList<Object3D>();

		world = new World(gl, camera, lights, object3ds);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawFrame(GL10 gl) {
		world.render();
	}
}
