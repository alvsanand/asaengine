package es.alvsanand.asaengine.graphics.cameras;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.math.Vector3;

public class LookAtCamera extends Camera {
	public Vector3 up;
	public Vector3 lookAt;
	public float near;
	public float far;

	public LookAtCamera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far) {
		super(position, fieldOfView, aspectRatio);

		this.near = near;
		this.far = far;

		up = new Vector3(0, 1, 0);
		lookAt = new Vector3(0, 0, -1);
	}

	@Override
	public void setMatrices() {
		// Select the projection matrix
		OpenGLRenderer.gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		OpenGLRenderer.gl.glLoadIdentity();

		// Calculate the aspect ratio of the window
		GLU.gluPerspective(OpenGLRenderer.gl, fieldOfView, aspectRatio, near, far);

		OpenGLRenderer.gl.glMatrixMode(GL10.GL_MODELVIEW);
		OpenGLRenderer.gl.glLoadIdentity();

		GLU.gluLookAt(OpenGLRenderer.gl, position.x, position.y, position.z, lookAt.x, lookAt.y, lookAt.z, up.x, up.y, up.z);
	}
}
