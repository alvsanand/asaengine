package es.alvsanand.asaengine.graphics.objects;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.math.Vector3;

public abstract class Object3D {
	public Vector3 position;

	public Object3D(Vector3 position) {
		this.position = position;
	}
	
	public abstract void render(GL10 gl);
}