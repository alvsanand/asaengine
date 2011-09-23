package es.alvsanand.asaengine.graphics.lights;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.math.Vector3;

public class DirectionalLight extends Light {
	public Color ambient = new Color(0.2f, 0.2f, 0.2f, 1.0f);
	public Color diffuse = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public Color specular = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	public float[] direction = { 0, 0, -1, 0 };
	public int id = 0;
	
	public DirectionalLight() {
	}

	public void setDirection(Vector3 direction) {
		this.direction[0] = -direction.x;
		this.direction[1] = -direction.y;
		this.direction[2] = -direction.z;
	}

	@Override
	public void enable() {
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_AMBIENT, ambient.toArray(), 0);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_DIFFUSE, diffuse.toArray(), 0);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_SPECULAR, specular.toArray(), 0);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_POSITION, direction, 0);
		OpenGLRenderer.gl.glEnable(id);
	}

	@Override
	public void disable() {
		OpenGLRenderer.gl.glDisable(id);
	}

	@Override
	public void dispose() {
	}
}
