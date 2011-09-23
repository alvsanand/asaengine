package es.alvsanand.asaengine.graphics.lights;

import javax.microedition.khronos.opengles.GL10;

import es.alvsanand.asaengine.graphics.Dynamic;
import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.renderer.OpenGLRenderer;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.Trajectory;

public class PointLight extends Light implements Dynamic {
	protected Color ambient = new Color(0.2f, 0.2f, 0.2f, 1.0f);
	protected Color diffuse = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	protected Color specular = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	protected Vector3 position;
	protected int id = 0;	
	protected Trajectory trajectory;
	
	public PointLight(){
	}
	
	public PointLight(Color ambient, Color diffuse, Color specular, Vector3 position, int id) {
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.position = position;
		this.id = id;
	}

	public Color getAmbient() {
		return ambient;
	}

	public void setAmbient(Color ambient) {
		this.ambient = ambient;
	}

	public Color getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Color diffuse) {
		this.diffuse = diffuse;
	}

	public Color getSpecular() {
		return specular;
	}

	public void setSpecular(Color specular) {
		this.specular = specular;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Trajectory getTrajectory() {
		return trajectory;
	}

	public void setTrajectory(Trajectory trajectory) {
		this.trajectory = trajectory;
	}

	@Override
	public void enable() {
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_AMBIENT, ambient.toArray(), 0);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_DIFFUSE, diffuse.toArray(), 0);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_SPECULAR, specular.toArray(), 0);
		OpenGLRenderer.gl.glLightfv(id, GL10.GL_POSITION, position.toArray(), 0);
		OpenGLRenderer.gl.glEnable(id);
	}

	@Override
	public void disable() {
		OpenGLRenderer.gl.glDisable(id);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void updatePosition() {
		this.position = trajectory.getActualPosition(this.position);
	}

	@Override
	public void startOrResume() {
		trajectory.startOrResume();
	}

	@Override
	public void pause() {
		trajectory.pause();
	}

	@Override
	public boolean isRunning() {
		if(trajectory==null){
			return false;
		}
		else{
			return trajectory.isRunning();
		}		
	}
}
