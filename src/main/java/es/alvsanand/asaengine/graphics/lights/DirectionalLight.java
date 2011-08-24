package es.alvsanand.asaengine.graphics.lights;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.math.Vector3;

public class DirectionalLight extends Light {
	protected Color ambient = new Color(0.2f, 0.2f, 0.2f, 1.0f);
	protected Color diffuse = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	protected Color specular = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	protected float[] direction = { 0, 0, -1, 0 };
	protected int id = 0;

	public void setId(int id) {
		this.id = id;
	}

	public void setAmbient(Color ambient) {
		this.ambient = ambient;
	}

	public void setDiffuse(Color diffuse) {
		this.diffuse = diffuse;
	}

	public void setSpecular(Color specular) {
		this.specular = specular;
	}

	public void setDirection(Vector3 direction) {
		this.direction[0] = -direction.x;
		this.direction[1] = -direction.y;
		this.direction[2] = -direction.z;
	}

	@Override
	public void enable(GL10 gl) {
		gl.glEnable(id);
		gl.glLightfv(id, GL10.GL_AMBIENT, ambient.toArray(), 0);
		gl.glLightfv(id, GL10.GL_DIFFUSE, diffuse.toArray(), 0);
		gl.glLightfv(id, GL10.GL_SPECULAR, specular.toArray(), 0);
		gl.glLightfv(id, GL10.GL_POSITION, direction, 0);
	}

	@Override
	public void disable(GL10 gl) {
		gl.glDisable(id);
	}
}
