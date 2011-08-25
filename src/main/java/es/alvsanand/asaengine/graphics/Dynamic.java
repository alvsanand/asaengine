package es.alvsanand.asaengine.graphics;

public interface Dynamic {
	public void updatePosition();
	public boolean isRunning();
	public void startOrResume();
	public void pause();
}
