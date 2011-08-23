package es.alvsanand.asaengine.graphics.cameras;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import es.alvsanand.asaengine.math.Vector3;

public class LookAtCamera extends Camera {
	final Vector3 up;
	final Vector3 lookAt;
	float fieldOfView;
	float aspectRatio;
	float near;
	float far;

	public LookAtCamera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far) {
		super(position);
		this.fieldOfView = fieldOfView;
		this.aspectRatio = aspectRatio;
		this.near = near;
		this.far = far;

		up = new Vector3(0, 1, 0);
		lookAt = new Vector3(0, 0, -1);
	}

	public Vector3 getPosition() {
		return position;
	}

	public Vector3 getUp() {
		return up;
	}

	public Vector3 getLookAt() {
		return lookAt;
	}

	@Override
	public void setMatrices(GL10 gl) {
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();

		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, fieldOfView, aspectRatio, near, far);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		GLU.gluLookAt(gl, position.x, position.y, position.z, lookAt.x, lookAt.y, lookAt.z, up.x, up.y, up.z);
	}
}
