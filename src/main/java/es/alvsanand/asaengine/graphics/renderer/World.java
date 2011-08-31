package es.alvsanand.asaengine.graphics.renderer;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.Dynamic;
import es.alvsanand.asaengine.graphics.lights.Light;
import es.alvsanand.asaengine.graphics.objects.Object3D;

public class World {
	protected ArrayList<Light> lights;
	protected ArrayList<Object3D> object3ds;
	
	public World(ArrayList<Light> lights, ArrayList<Object3D> object3ds) {
		this.lights = lights;
		this.object3ds = object3ds;
	}

	public void render() {		
		// Set the background color to black ( rgba ).
		OpenGLRenderer.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// Enable Smooth Shading, default not really needed.
		OpenGLRenderer.gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		OpenGLRenderer.gl.glClearDepthf(1.0f);
		// The type of depth testing to do.
		OpenGLRenderer.gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		OpenGLRenderer.gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		OpenGLRenderer.gl.glEnable(GL10.GL_DEPTH_TEST);
		OpenGLRenderer.gl.glEnable(GL10.GL_TEXTURE_2D);
		OpenGLRenderer.gl.glEnable(GL10.GL_LIGHTING);					
		OpenGLRenderer.gl.glEnable(GL10.GL_COLOR_MATERIAL);

		enableLights();
		
		renderObject3ds();

		OpenGLRenderer.gl.glDisable(GL10.GL_TEXTURE_2D);
		OpenGLRenderer.gl.glDisable(GL10.GL_COLOR_MATERIAL);
		OpenGLRenderer.gl.glDisable(GL10.GL_LIGHTING);
		OpenGLRenderer.gl.glDisable(GL10.GL_DEPTH_TEST);		
	}

	void renderObject3ds() {
		for(Object3D object3d: object3ds){		
			if(object3d instanceof Dynamic){
				((Dynamic) object3d).updatePosition();
			}
			
			object3d.render();
		}
	}

	void enableLights() {
		for(Light light: lights){			
			if(light instanceof Dynamic){
				((Dynamic) light).updatePosition();
			}
			
			light.enable();
		}
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