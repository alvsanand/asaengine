package es.alvsanand.asaengine.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import es.alvsanand.asaengine.graphics.cameras.Camera;

public class OpenGLRenderer implements Renderer {
	private static String TAG = "OpenGLRenderer";
	
	protected World world;

	protected Camera camera;
	
	public OpenGLRenderer(World world, Camera camera) {
		this.world = world;
		this.camera = camera;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

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
		} catch (Throwable throwable) {
			Log.e(TAG, "Error in onSurfaceCreated", throwable);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		try {
			// Sets the current view port to the new size.
			gl.glViewport(0, 0, width, height);

			camera.setAspectRatio((float) width / (float)height);			
			
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

			world.render(gl);
		} catch (Throwable throwable) {
			Log.e(TAG, "Error in onSurfaceCreated", throwable);
		}
	}
}
