package es.alvsanand.asaengine.graphics.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import es.alvsanand.asaengine.graphics.Dynamic;
import es.alvsanand.asaengine.graphics.cameras.Camera;
import es.alvsanand.asaengine.graphics.objects.Mesh;

public class OpenGLRenderer implements Renderer {
	private static String TAG = "OpenGLRenderer";
	
	public World world;

	public Camera camera;
	
	public static GL10 gl;
	
	public static GL11 gl11;
	
	public static enum GL_TYPE{
		GL10, GL11
	};
	
	public static GL_TYPE glType;
	
	public OpenGLRenderer(World world, Camera camera) {
		this.world = world;
		this.camera = camera;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		try {
			Log.v(TAG, "onSurfaceCreated");
			
			Mesh.invalidateAllMeshes();
			
			if(gl!=null){
				OpenGLRenderer.gl = gl;
				if(gl instanceof GL11){
					glType = GL_TYPE.GL11;
					gl11 = (GL11)gl;
				}
				else{
					glType = GL_TYPE.GL10;
				}
			}
			
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
			Log.v(TAG, "onSurfaceChanged");
			
			// Sets the current view port to the new size.
			gl.glViewport(0, 0, width, height);

			camera.aspectRatio = (float) width / (float)height;			
			
			if(camera instanceof Dynamic){
				((Dynamic) camera).updatePosition();
			}
			
			camera.setMatrices();
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
			
			if(camera instanceof Dynamic){
				((Dynamic) camera).updatePosition();
			}
			
			camera.setMatrices();

			world.render();
		} catch (Throwable throwable) {
			Log.e(TAG, "Error in onSurfaceCreated", throwable);
		}
	}
}
