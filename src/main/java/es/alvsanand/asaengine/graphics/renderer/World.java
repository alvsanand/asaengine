package es.alvsanand.asaengine.graphics.renderer;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.lights.Light;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.graphics.objects.Terrain;

public class World {
	public ArrayList<Light> lights;
	public ArrayList<Object3D> object3ds;
	public Terrain terrain;
	
	public World(ArrayList<Light> lights, ArrayList<Object3D> object3ds, Terrain terrain) {
		this.lights = lights;
		this.object3ds = object3ds;
		this.terrain = terrain;
	}

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
		OpenGLRenderer.gl.glEnable(GL10.GL_LIGHTING);					
		OpenGLRenderer.gl.glEnable(GL10.GL_COLOR_MATERIAL);

		OpenGLRenderer.gl.glEnable(GL10.GL_BLEND); 
		OpenGLRenderer.gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA); 
		
		enableLights();
		
		renderObject3ds();

		OpenGLRenderer.gl.glDisable(GL10.GL_BLEND); 
		OpenGLRenderer.gl.glDisable(GL10.GL_COLOR_MATERIAL);
		OpenGLRenderer.gl.glDisable(GL10.GL_LIGHTING);
		OpenGLRenderer.gl.glDisable(GL10.GL_DEPTH_TEST);		
	}

	void renderObject3ds() {
		if(terrain!=null){
			terrain.render();
		}
		
		int objectSize = object3ds.size();
		
		for(int i=0; i<objectSize; i++){
			Object3D object3d = object3ds.get(i);
			
			object3d.render();
		}
	}

	void enableLights() {		
		int lightSize = lights.size();
		
		for(int i=0; i<lightSize; i++){
			Light light = lights.get(i);
			
			light.enable();
		}
	}
}