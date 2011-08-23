package es.alvsanand.asaengine.graphics;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.cameras.Camera;
import es.alvsanand.asaengine.graphics.lights.Light;

public class World {
	GL10 gl;
	Camera camera;
	ArrayList<Light> lights;
	ArrayList<Object3D> object3ds;
	
	public World(GL10 gl, Camera camera, ArrayList<Light> lights, ArrayList<Object3D> object3ds) {
		super();
		this.gl = gl;
		this.camera = camera;
		this.lights = lights;
		this.object3ds = object3ds;
	}

	public void render() {		
		camera.setMatrices(gl);
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
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

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
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