package es.alvsanand.asaengine.graphics.objects.primitives;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.math.Vector3;

public abstract class PrimitiveObject extends Object3D {
	public Color fillColor;
	public Color borderColor;
	public boolean printBorder;

	public PrimitiveObject(Vector3 position, Color fillColor) {
		super(position);

		this.fillColor = fillColor;
	}

	public PrimitiveObject(Vector3 position, Color fillColor, Color borderColor) {
		super(position);
		this.fillColor = fillColor;
		this.borderColor = borderColor;
		this.printBorder = true;
	}
}
