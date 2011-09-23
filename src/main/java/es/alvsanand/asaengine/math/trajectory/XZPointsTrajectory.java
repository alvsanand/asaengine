package es.alvsanand.asaengine.math.trajectory;

import es.alvsanand.asaengine.math.Vector2;
import es.alvsanand.asaengine.math.Vector3;

public class XZPointsTrajectory extends Trajectory {
	protected int lastPoint;

	protected Vector2[] points;

	public XZPointsTrajectory(float acceleration, float initialSpeed, float maxSpeed, Vector2[] points) {
		super(acceleration, initialSpeed, maxSpeed);
		this.points = points;
		this.lastPoint = 0;
	}

	@Override
	public Vector3 getActualPosition(Vector3 lastPosition) {
		float y = lastPosition.y;
		
		if(!running){
			return new Vector3(lastPosition);
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
				return new Vector3(lastPosition);
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
			return new Vector3(lastPosition);
		}
		
		Vector2 actualPointVector2 = points[actualPoint];
		Vector2 fromPointVector2 = new Vector2(lastPosition.x, lastPosition.z);
		
		while (fromPointVector2.dst(actualPointVector2) <= distance) {
			distance -= fromPointVector2.dst(actualPointVector2);

			actualPoint = (actualPoint + 1 == points.length) ? 0 : actualPoint + 1;

			fromPointVector2 = new Vector2(actualPointVector2);
			actualPointVector2 = points[actualPoint];
		}

		Vector2 n = (new Vector2(actualPointVector2)).sub(fromPointVector2);

		if (n.x + n.y == 0) {
			return new Vector3(lastPosition);
		}

		float t = distance / (n.x + n.y);

		Vector2 a1 = new Vector2(fromPointVector2.x + n.x * t, fromPointVector2.y + n.y * t);
		Vector2 a2 = new Vector2(fromPointVector2.x + n.x * (-t), fromPointVector2.y + n.y * (-t));

		Vector2 actualPositionVector2;

		if (a1.dst(actualPointVector2) > fromPointVector2.dst(actualPointVector2)) {
			actualPositionVector2 = a2;
		} else {
			actualPositionVector2 = a1;
		}

		// Calculate parameters for next interaction
		this.speed = actualSpeed;

		this.lastTimeCalculated = now;
		
		this.lastPoint = actualPoint;
		
		this.direction = new Vector3(n.x, 0, n.y);
		
		return new Vector3(actualPositionVector2.x, y, actualPositionVector2.y);
	}
}
