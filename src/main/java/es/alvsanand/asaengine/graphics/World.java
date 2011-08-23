package es.alvsanand.asaengine.graphics;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.lights.Light;
import es.alvsanand.asaengine.graphics.objects.Object3D;

public class World {
	GL10 gl;
	ArrayList<Light> lights;
	ArrayList<Object3D> object3ds;
	
	public World(GL10 gl, ArrayList<Light> lights, ArrayList<Object3D> object3ds) {
		super();
		this.gl = gl;
		this.lights = lights;
		this.object3ds = object3ds;
	}

	public void render() {		
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_LIGHTING);					
		gl.glEnable(GL10.GL_COLOR_MATERIAL);

		enableLights();
		
		renderObject3ds();

		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_DEPTH_TEST);		
	}

	void renderObject3ds() {
		for(Object3D object3d: object3ds){
			object3d.render(gl);
		}
	}

	void enableLights() {
		for(Light light: lights){
			light.enable(gl);
		}
	}
	
	public GL10 getGl() {
		return gl;
	}

	public void setGl(GL10 gl) {
		this.gl = gl;
	}

	public ArrayList<Light> getLights() {
		return lights;
	}

	public void setLights(ArrayList<Light> lights) {
		this.lights = lights;
	}

	public ArrayList<Object3D> getObject3ds() {
		return object3ds;
	}

	public void setObject3ds(ArrayList<Object3D> object3ds) {
		this.object3ds = object3ds;
	}
}