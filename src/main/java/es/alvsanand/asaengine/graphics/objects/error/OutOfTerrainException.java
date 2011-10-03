package es.alvsanand.asaengine.graphics.objects.error;

import es.alvsanand.asaengine.util.io.error.ASAIOException;

public class OutOfTerrainException extends ASAIOException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2454807521733204171L;

	public OutOfTerrainException() {
		super();
	}

	public OutOfTerrainException(String message, Throwable cause) {
		super(message, cause);
	}

	public OutOfTerrainException(String message) {
		super(message);
	}

	public OutOfTerrainException(Throwable cause) {
		super(cause);
	}
}