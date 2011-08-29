package es.alvsanand.asaengine.graphics.objects;

import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.util.Disposable;

public abstract class Object3D implements Disposable{
	public Vector3 position;

	public float rx = 0;

	public float ry = 0;

	public float rz = 0;

	public Object3D(Vector3 position) {
		this.position = position;
	}
	
	public abstract void render();
}