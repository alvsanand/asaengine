package es.alvsanand.asaengine.graphics.cameras;

import es.alvsanand.asaengine.graphics.Dynamic;
import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.Trajectory;

public abstract class Camera implements Dynamic {
	protected Vector3 position;
	protected float fieldOfView;
	protected float aspectRatio;
	protected float near;
	protected float far;
	protected Trajectory trajectory;
	
	protected Color fogColor = new Color(0.8f,0.8f,0.8f, 1f);
	
	protected final static float FOG_LENGTH = 20;

	public Camera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far, Trajectory trajectory) {
		super();
		this.position = position;
		this.fieldOfView = fieldOfView;
		this.aspectRatio = aspectRatio;
		this.near = near;
		this.far = far;
		this.trajectory = trajectory;
	}

	public Camera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far) {
		super();
		this.position = position;
		this.fieldOfView = fieldOfView;
		this.aspectRatio = aspectRatio;
		this.near = near;
		this.far = far;
	}

	public abstract void setMatrices();

	@Override
	public void updatePosition() {
		this.position = trajectory.getActualPosition(this.position);
	}

	@Override
	public void startOrResume() {
		if (trajectory == null)
			trajectory.startOrResume();
	}

	@Override
	public void pause() {
		if (trajectory == null)
			trajectory.pause();
	}

	@Override
	public boolean isRunning() {
		if (trajectory == null) {
			return false;
		} else {
			return trajectory.isRunning();
		}
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public float getFieldOfView() {
		return fieldOfView;
	}

	public void setFieldOfView(float fieldOfView) {
		this.fieldOfView = fieldOfView;
	}

	public float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public float getNear() {
		return near;
	}

	public void setNear(float near) {
		this.near = near;
	}

	public float getFar() {
		return far;
	}

	public void setFar(float far) {
		this.far = far;
	}

	public Trajectory getTrajectory() {
		return trajectory;
	}

	public void setTrajectory(Trajectory trajectory) {
		this.trajectory = trajectory;
	}

	public Color getFogColor() {
		return fogColor;
	}

	public void setFogColor(Color fogColor) {
		this.fogColor = fogColor;
	}
}
