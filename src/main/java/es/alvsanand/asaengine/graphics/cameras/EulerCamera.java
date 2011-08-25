package es.alvsanand.asaengine.graphics.cameras;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.Matrix;
import es.alvsanand.asaengine.math.Vector3;

public class EulerCamera extends Camera {
	public float yaw;
	public float pitch;

	public float near;
	public float far;

	protected final float[] matrix = new float[16];
	protected final float[] inVec = { 0, 0, -1, 1 };
	protected final float[] outVec = new float[4];
	protected final Vector3 direction = new Vector3();

	public EulerCamera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far) {
		super(position, fieldOfView, aspectRatio);

		this.near = near;
		this.far = far;
	}

	public void setAngles(float yaw, float pitch) {
		if (pitch < -90)
			pitch = -90;
		if (pitch > 90)
			pitch = 90;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public void rotate(float yawInc, float pitchInc) {
		this.yaw += yawInc;
		this.pitch += pitchInc;
		if (pitch < -90)
			pitch = -90;
		if (pitch > 90)
			pitch = 90;
	}

	public Vector3 getDirection() {
		Matrix.setIdentityM(matrix, 0);
		Matrix.rotateM(matrix, 0, yaw, 0, 1, 0);
		Matrix.rotateM(matrix, 0, pitch, 1, 0, 0);
		Matrix.multiplyMV(outVec, 0, matrix, 0, inVec, 0);
		
		direction.x = outVec[0];
		direction.y = outVec[1];
		direction.z = outVec[2];
		
		return direction;
	}

	@Override
	public void setMatrices(GL10 gl) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, fieldOfView, aspectRatio, near, far);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(-pitch, 1, 0, 0);
		gl.glRotatef(-yaw, 0, 1, 0);
		gl.glTranslatef(-position.x, -position.y, -position.z);
	}
}