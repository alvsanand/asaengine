package es.alvsanand.asaengine.graphics.cameras;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.Matrix;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.Trajectory;

public class EulerCamera extends Camera {
	protected float yaw;
	protected float pitch;

	protected final float[] matrix = new float[16];
	protected final float[] inVec = { 0, 0, -1, 1 };
	protected final float[] outVec = new float[4];
	protected final Vector3 direction = new Vector3();

	public EulerCamera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far) {
		super(position, fieldOfView, aspectRatio, near, far);
	}

	public EulerCamera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far, Trajectory trajectory, float yaw, float pitch) {
		super(position, fieldOfView, aspectRatio, near, far, trajectory);
		
		this.yaw = yaw;
		this.pitch = pitch;
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
	public void setMatrices() {
		//Draw Fog
		OpenGLRenderer.gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_LINEAR);
		OpenGLRenderer.gl.glFogfv(GL10.GL_FOG_COLOR,fogColor.toArray(), 0);
		OpenGLRenderer.gl.glFogf(GL10.GL_FOG_START, far - FOG_LENGTH);
		OpenGLRenderer.gl.glFogf(GL10.GL_FOG_END, far);
		OpenGLRenderer.gl.glEnable(GL10.GL_FOG);
		
		OpenGLRenderer.gl.glMatrixMode(GL10.GL_PROJECTION);
		OpenGLRenderer.gl.glLoadIdentity();
		GLU.gluPerspective(OpenGLRenderer.gl, fieldOfView, aspectRatio, near, far);
		OpenGLRenderer.gl.glMatrixMode(GL10.GL_MODELVIEW);
		OpenGLRenderer.gl.glLoadIdentity();
		OpenGLRenderer.gl.glRotatef(-pitch, 1, 0, 0);
		OpenGLRenderer.gl.glRotatef(-yaw, 0, 1, 0);
		OpenGLRenderer.gl.glTranslatef(-position.x, -position.y, -position.z);
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
}