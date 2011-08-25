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
		trajectory.startOrResume();
	}

	@Override
	public void pause() {
		trajectory.pause();
	}

	@Override
	public boolean isRunning() {
		return trajectory.isRunning();
	}
}
