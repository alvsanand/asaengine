package es.alvsanand.asaengine.graphics.lights;

import es.alvsanand.asaengine.util.Disposable;

public abstract class Light implements Disposable{	
	public abstract void enable();
	public abstract void disable();
}
