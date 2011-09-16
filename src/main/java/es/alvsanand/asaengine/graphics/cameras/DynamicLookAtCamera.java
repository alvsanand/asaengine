package es.alvsanand.asaengine.graphics.cameras;

import es.alvsanand.asaengine.graphics.Dynamic;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.Trajectory;

public class DynamicLookAtCamera extends LookAtCamera implements Dynamic {
	protected Trajectory trajectory;

	public DynamicLookAtCamera(Vector3 position, float fieldOfView, float aspectRatio, float near, float far, Trajectory trajectory) {
		super(position, fieldOfView, aspectRatio, near, far);
		this.trajectory = trajectory;
	}

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
}
