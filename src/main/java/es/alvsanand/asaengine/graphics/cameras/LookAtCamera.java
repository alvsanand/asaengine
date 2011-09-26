package es.alvsanand.asaengine.graphics.cameras;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.Trajectory;

public class LookAtCamera extends Camera {
	protected Vector3 up;
	protected Vector3 lookAt;

	public LookAtCamera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far) {
		super(position, fieldOfView, aspectRatio, near, far);

		up = new Vector3(0, 1, 0);
		lookAt = new Vector3(0, 0, -1);
	}

	public LookAtCamera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far, Trajectory trajectory, Vector3 up,
			Vector3 lookAt) {
		super(position, fieldOfView, aspectRatio, near, far, trajectory);
		this.up = up;
		this.lookAt = lookAt;
	}

	public LookAtCamera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far, Trajectory trajectory) {
		super(position, fieldOfView, aspectRatio, near, far, trajectory);

		up = new Vector3(0, 1, 0);
		lookAt = new Vector3(0, 0, -1);
	}

	@Override
	public void setMatrices() {
		//Draw Fog
		OpenGLRenderer.gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_LINEAR);
		OpenGLRenderer.gl.glFogfv(GL10.GL_FOG_COLOR,fogColor.toArray(), 0);
		OpenGLRenderer.gl.glFogf(GL10.GL_FOG_START, far-FOG_LENGTH);
		OpenGLRenderer.gl.glFogf(GL10.GL_FOG_END, far);
		OpenGLRenderer.gl.glEnable(GL10.GL_FOG);
		
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

	public Vector3 getUp() {
		return up;
	}

	public void setUp(Vector3 up) {
		this.up = up;
	}

	public Vector3 getLookAt() {
		return lookAt;
	}

	public void setLookAt(Vector3 lookAt) {
		this.lookAt = lookAt;
	}
}
