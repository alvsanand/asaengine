package es.alvsanand.asaengine.graphics.cameras;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.math.Vector3;

public abstract class Camera {
	final Vector3 position;
	
	public Camera(Vector3 position) {
		this.position = position;
	}

	public Vector3 getPosition() {
		return position;
	}

	public abstract void setMatrices(GL10 gl);
}
