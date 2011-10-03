package es.alvsanand.asaengine.graphics.objects.error;

import es.alvsanand.asaengine.util.io.error.ASAIOException;

public class MeshNotFoundException extends ASAIOException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2454807521733204171L;

	public MeshNotFoundException() {
		super();
	}

	public MeshNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MeshNotFoundException(String message) {
		super(message);
	}

	public MeshNotFoundException(Throwable cause) {
		super(cause);
	}
}