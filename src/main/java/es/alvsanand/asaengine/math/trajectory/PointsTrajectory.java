package es.alvsanand.asaengine.math.trajectory;

import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.util.Vector3Utils;

public class PointsTrajectory extends Trajectory {
	protected int lastPoint;

	protected Vector3[] points;

	public PointsTrajectory(float acceleration, float initialSpeed, Vector3[] points) {
		super(acceleration, initialSpeed);
		this.points = points;
		this.lastPoint = 0;
	}

	@Override
	public Vector3 getActualPosition(Vector3 lastPosition) {
		long now = System.currentTimeMillis();

		if (lastTimeCalculated == 0) {
			lastTimeCalculated = now;
		}

		int time = (int) ((now - this.lastTimeCalculated) / 1000);

		int nextPoint = (lastPoint == points.length) ? 0 : lastPoint + 1;

		float totalDistance = this.acceleration * time * time + this.speed * time;
		float distance = totalDistance;

		if (distance == 0) {
			return Vector3Utils.cpy(lastPosition);
		}

		Vector3 nextPointVector3 = points[nextPoint];
		Vector3 fromPointVector3 = Vector3Utils.cpy(lastPosition);

		while (Vector3Utils.dist(fromPointVector3, nextPointVector3) < distance) {
			distance -= Vector3Utils.dist(fromPointVector3, nextPointVector3);

			nextPoint = (nextPoint + 1 == points.length) ? 0 : nextPoint + 1;

			fromPointVector3 = Vector3Utils.cpy(nextPointVector3);
			nextPointVector3 = points[nextPoint];
		}

		Vector3 n = Vector3Utils.sub(nextPointVector3, fromPointVector3);

		if (n.x + n.y + n.z == 0) {
			return null;
		}

		float t = distance / (n.x + n.y + n.z);

		Vector3 a1 = new Vector3(fromPointVector3.x + n.x * t, fromPointVector3.y + n.y * t, fromPointVector3.z + n.z * t);
		Vector3 a2 = new Vector3(fromPointVector3.x + n.x * (-t), fromPointVector3.y + n.y * (-t), fromPointVector3.z + n.z * (-t));

		Vector3 actualPositionVector3;

		if (Vector3Utils.dist(a1, nextPointVector3) > Vector3Utils.dist(fromPointVector3, nextPointVector3)) {
			actualPositionVector3 = a2;
		} else {
			actualPositionVector3 = a1;
		}

		// Calculate parameters for next interaction
		this.speed = this.speed + time * this.acceleration;

		this.lastTimeCalculated = now;
		
		this.lastPoint = nextPoint;

		return actualPositionVector3;
	}

	public static void main() {
		Vector3[] points = new Vector3[] { new Vector3(0, 0, 0), new Vector3(0, 0, 1), new Vector3(0, 1, 1), new Vector3(0, 1, 0) };

		PointsTrajectory pointsTrayectory = new PointsTrajectory(0, 0.5f, points);

		Vector3 lastPosition = new Vector3(0, 0, 0);

		while (true) {
			lastPosition = pointsTrayectory.getActualPosition(lastPosition);
			System.out.println(lastPosition);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
