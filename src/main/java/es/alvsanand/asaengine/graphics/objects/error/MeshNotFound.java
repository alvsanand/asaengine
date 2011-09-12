package es.alvsanand.asaengine.graphics.objects.error;

import es.alvsanand.asaengine.util.io.error.ASAIOException;

public class MeshNotFound extends ASAIOException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2454807521733204171L;

	public MeshNotFound() {
		super();
	}

	public MeshNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public MeshNotFound(String message) {
		super(message);
	}

	public MeshNotFound(Throwable cause) {
		super(cause);
	}
}