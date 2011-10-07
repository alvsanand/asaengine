package es.alvsanand.asaengine.math.trajectory;

import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.Vector3Util;

public class PointsTrajectory extends Trajectory {
	protected int lastPoint;

	protected Vector3[] points;

	public PointsTrajectory(float acceleration, float initialSpeed, float maxSpeed, Vector3[] points) {
		super(acceleration, initialSpeed, maxSpeed);
		this.points = points;
		this.lastPoint = 0;
	}

	@Override
	public Vector3 getActualPosition(Vector3 lastPosition) {
		if(!running){
			return Vector3Util.cpy(lastPosition);
		}
		
		long now = System.currentTimeMillis();

		if (lastTimeCalculated == 0) {
			lastTimeCalculated = now;
		}

		double millisTime = now - this.lastTimeCalculated;
		
		float time = (float)millisTime / 1000;

		int actualPoint = lastPoint;

		float actualSpeed = maxSpeed;		
		
		float totalDistance = 0;
		
		if(this.speed == this.maxSpeed || acceleration == 0){
			actualSpeed = this.speed;
			totalDistance =  actualSpeed * time;
		}
		else{			
			actualSpeed = this.speed + time * this.acceleration;
			
			if(actualSpeed<=0){
				return Vector3Util.cpy(lastPosition);
			}
			
			if(actualSpeed >= this.maxSpeed)
			{
				actualSpeed = this.maxSpeed;
				
				float timeToMaxSpeed = (this.maxSpeed - this.speed) / this.acceleration;
				
				totalDistance = (this.acceleration * timeToMaxSpeed * timeToMaxSpeed + this.speed * timeToMaxSpeed) + this.maxSpeed * (time - timeToMaxSpeed);
			}
			else{
				totalDistance = this.acceleration * time * time + this.speed * time;
			}
		}
		
		float distance = totalDistance;

		if (distance == 0) {
			return Vector3Util.cpy(lastPosition);
		}
		
		Vector3 actualPointVector3 = points[actualPoint];
		Vector3 fromPointVector3 = Vector3Util.cpy(lastPosition);
		
		while (Vector3Util.dst(fromPointVector3, actualPointVector3) <= distance) {
			distance -= Vector3Util.dst(fromPointVector3, actualPointVector3);

			actualPoint = (actualPoint + 1 == points.length) ? 0 : actualPoint + 1;

			fromPointVector3 = Vector3Util.cpy(actualPointVector3);
			actualPointVector3 = points[actualPoint];
		}

		this.direction = Vector3Util.sub(Vector3Util.cpy(actualPointVector3), fromPointVector3);

		if (direction.x + direction.y + direction.z == 0) {
			return Vector3Util.cpy(lastPosition);
		}

		float t = distance / (this.direction.x + this.direction.y + this.direction.z);

		Vector3 a1 = new Vector3(fromPointVector3.x + this.direction.x * t, fromPointVector3.y + this.direction.y * t, fromPointVector3.z + this.direction.z * t);
		Vector3 a2 = new Vector3(fromPointVector3.x + this.direction.x * (-t), fromPointVector3.y + this.direction.y * (-t), fromPointVector3.z + this.direction.z * (-t));

		Vector3 actualPositionVector3;

		if (Vector3Util.dst(a1, actualPointVector3) > Vector3Util.dst(fromPointVector3, actualPointVector3)) {
			actualPositionVector3 = a2;
		} else {
			actualPositionVector3 = a1;
		}

		// Calculate parameters for next interaction
		this.speed = actualSpeed;

		this.lastTimeCalculated = now;
		
		this.lastPoint = actualPoint;

		return actualPositionVector3;
	}
}
