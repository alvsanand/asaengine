package es.alvsanand.asaengine.graphics.lights;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.color.Color;

public class AmbientLight extends Light{
	Color color = new Color(0.2f, 0.2f, 0.2f, 1);
	
	public AmbientLight(Color color) {
		super();
		this.color = color;
	}
	
	@Override
	public void enable(GL10 gl) {
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, color.toArray(), 0);
		
		gl.glEnable(GL10.GL_LIGHT_MODEL_AMBIENT);
	}

	@Override
	public void disable(GL10 gl) {	
		gl.glDisable(GL10.GL_LIGHT_MODEL_AMBIENT);
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;		
	}
}
