package es.alvsanand.asaengine.graphics.lights;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.math.Vector3;

public class PointLight extends Light {
	public Color ambient = new Color(0.2f, 0.2f, 0.2f, 1.0f);
	public Color diffuse = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public Color specular = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	public float[] position = { 0, 0, 0, 1 };
	public int id = 0;
	
	public PointLight(){
	}
	
	public PointLight(Color ambient, Color diffuse, Color specular, Vector3 position, int id) {
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		setPosition(position);
		this.id = id;
	}

	public void setPosition(Vector3 position) {
		this.position[0] = position.x;
		this.position[1] = position.y;
		this.position[2] = position.z;
	}

	@Override
	public void enable() {
		OpenGLRenderer.gl.glEnable(id);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_AMBIENT, ambient.toArray(), 0);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_DIFFUSE, diffuse.toArray(), 0);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_SPECULAR, specular.toArray(), 0);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_POSITION, position, 0);
	}

	@Override
	public void disable() {
		OpenGLRenderer.gl.glDisable(id);
	}

	@Override
	public void dispose() {
	}
}
