package es.alvsanand.asaengine.graphics.objects;

import es.alvsanand.asaengine.graphics.Dynamic;
import es.alvsanand.asaengine.math.Vector3;
import es.alvsanand.asaengine.math.trajectory.Trajectory;
import es.alvsanand.asaengine.util.Disposable;

public abstract class Object3D implements Disposable, Dynamic, Renderable{
	protected Vector3 position;
	
	protected Trajectory trajectory;	

	protected float rx = 0;

	protected float ry = 0;

	protected float rz = 0;

	protected float sx = 0;

	protected float sy = 0;

	protected float sz = 0;

	public Object3D(Vector3 position) {
		this.position = position;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}

	public float getSx() {
		return sx;
	}

	public void setSx(float sx) {
		this.sx = sx;
	}

	public float getSy() {
		return sy;
	}

	public void setSy(float sy) {
		this.sy = sy;
	}

	public float getSz() {
		return sz;
	}

	public void setSz(float sz) {
		this.sz = sz;
	}

	public Trajectory getTrajectory() {
		return trajectory;
	}

	public void setTrajectory(Trajectory trajectory) {
		this.trajectory = trajectory;
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