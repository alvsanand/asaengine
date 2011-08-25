package es.alvsanand.asaengine.math.trajectory;

import es.alvsanand.asaengine.math.Vector3;

public abstract class Trajectory {
	protected long lastTimeCalculated;
	
	protected float acceleration;
	protected float initialSpeed;
	protected float speed;

	public Trajectory(float acceleration, float initialSpeed) {
		super();
		this.acceleration = acceleration;
		this.initialSpeed = initialSpeed;
		this.speed = initialSpeed;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getInitialSpeed() {
		return initialSpeed;
	}

	public void setInitialSpeed(float initialSpeed) {
		this.initialSpeed = initialSpeed;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public abstract Vector3 getActualPosition(Vector3 lastPosition);
}
