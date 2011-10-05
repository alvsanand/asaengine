package es.alvsanand.asaengine.graphics.objects;

import es.alvsanand.asaengine.graphics.Dynamic;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.Trajectory;
import es.alvsanand.asaengine.util.Disposable;

public abstract class Object3D implements Disposable, Dynamic, Renderable{
	public Vector3 position;
	
	public Trajectory trajectory;	

	public float rx = 0;

	public float ry = 0;

	public float rz = 0;

	public float sx = 0;

	public float sy = 0;

	public float sz = 0;

	public Object3D(Vector3 position) {
		this.position = position;
	}

	@Override
	public void updatePosition() {
		if (trajectory != null)
			this.position = trajectory.getActualPosition(this.position);
	}

	@Override
	public void startOrResume() {
		if (trajectory != null)
			trajectory.startOrResume();
	}

	@Override
	public void pause() {
		if (trajectory != null)
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
	
	protected abstract void renderPosition();
}