package es.alvsanand.asaengine.math.trajectory;

import es.alvsanand.asaengine.math.Vector3;

public abstract class Trajectory {
	protected long lastTimeCalculated;
	
	protected float acceleration;
	protected float initialSpeed;
	protected float maxSpeed;
	protected float speed;
	
	protected boolean running = false;

	public Trajectory(float acceleration, float initialSpeed, float maxSpeed) {
		this.acceleration = acceleration;
		this.initialSpeed = initialSpeed;
		this.speed = initialSpeed;
		this.maxSpeed = maxSpeed;
	}
	
	public void startOrResume(){
		running = true;
		
		lastTimeCalculated = 0;
	}
	public void pause(){
		running = false;
	}

	public boolean isRunning() {
		return running;
	}

	public abstract Vector3 getActualPosition(Vector3 lastPosition);
}
