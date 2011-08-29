package es.alvsanand.asaengine.graphics.lights;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;

public class AmbientLight extends Light{
	public Color color = new Color(0.2f, 0.2f, 0.2f, 1);
	
	public AmbientLight(Color color) {
		this.color = color;
	}
	
	@Override
	public void enable() {
		OpenGLRenderer.gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, color.toArray(), 0);
		
		OpenGLRenderer.gl.glEnable(GL10.GL_LIGHT_MODEL_AMBIENT);
	}

	@Override
	public void disable() {	
		OpenGLRenderer.gl.glDisable(GL10.GL_LIGHT_MODEL_AMBIENT);
	}

	@Override
	public void dispose() {
	}
}
