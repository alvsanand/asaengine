package es.alvsanand.asaengine.graphics.cameras;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.math.Vector3;

public abstract class Camera {
	public Vector3 position;
	public float fieldOfView;
	public float aspectRatio;

	public Camera(Vector3 position, float fieldOfView, float aspectRatio) {
		super();
		this.position = position;
		this.fieldOfView = fieldOfView;
		this.aspectRatio = aspectRatio;
	}

	public abstract void setMatrices(GL10 gl);
}
