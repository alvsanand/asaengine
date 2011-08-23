package es.alvsanand.asaengine.graphics;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import es.alvsanand.asaengine.graphics.cameras.Camera;
import es.alvsanand.asaengine.graphics.cameras.LookAtCamera;
import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.lights.Light;
import es.alvsanand.asaengine.graphics.lights.PointLight;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.objects.primitives.Cube;
import es.alvsanand.asaengine.math.Vector3;

public class OpenGLRenderer implements Renderer {
	private static String TAG = "OpenGLRenderer";
	
	World world;

	Camera camera;

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		try {
			// Set the background color to black ( rgba ).
			gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
			// Enable Smooth Shading, default not really needed.
			gl.glShadeModel(GL10.GL_SMOOTH);
			// Depth buffer setup.
			gl.glClearDepthf(1.0f);
			// Enables depth testing.
			gl.glEnable(GL10.GL_DEPTH_TEST);
			// The type of depth testing to do.
			gl.glDepthFunc(GL10.GL_LEQUAL);
			// Really nice perspective calculations.
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

			ArrayList<Light> lights = new ArrayList<Light>();
			lights.add(new PointLight(new Color(256, 256, 256, 1), new Color(256, 256, 256, 1), new Color(256, 256, 256, 1), new Vector3(30, 30, 30), GL10.GL_LIGHT0));

			ArrayList<Object3D> object3ds = new ArrayList<Object3D>();
			
			Cube cube = new Cube(new Vector3(0,0,0), new Color(0f, 256f, 0f, 1.0f), 1, 1, 1);
			cube.rx = 45;
			cube.ry = 45;
			
			object3ds.add(cube);

			world = new World(gl, lights, object3ds);
		} catch (Throwable throwable) {
			Log.e(TAG, "Error in onSurfaceCreated", throwable);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		try {
			// Sets the current view port to the new size.
			gl.glViewport(0, 0, width, height);

			camera = new LookAtCamera(new Vector3(10, 10, 10), 67,(float) width / (float)height, 0.1f, 100);
			((LookAtCamera)camera).getLookAt().set(0,0,0);

			camera.setMatrices(gl);
		} catch (Throwable throwable) {
			Log.e(TAG, "Error in onSurfaceCreated", throwable);
		}
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		try {
			// Clears the screen and depth buffer.
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			// Replace the current matrix with the identity matrix
			gl.glLoadIdentity();
			// Translates 4 units into the screen.
			gl.glTranslatef(0, 0, -4); 

			world.render();
		} catch (Throwable throwable) {
			Log.e(TAG, "Error in onSurfaceCreated", throwable);
		}
	}
}
