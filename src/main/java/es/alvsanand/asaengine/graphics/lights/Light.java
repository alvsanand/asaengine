package es.alvsanand.asaengine.graphics.lights;

import javax.microedition.khronos.opengles.GL10;

public abstract class Light {
	public abstract void enable(GL10 gl);
	public abstract void disable(GL10 gl);
}
