package es.alvsanand.asaengine.graphics.objects;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.math.Vector3;

public abstract class Object3D {
	protected Vector3 position;

	public Object3D(Vector3 position) {
		super();
		this.position = position;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}
	
	public abstract void render(GL10 gl);
}