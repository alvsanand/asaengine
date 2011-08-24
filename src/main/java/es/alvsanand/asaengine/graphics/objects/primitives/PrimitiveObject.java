package es.alvsanand.asaengine.graphics.objects.primitives;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.math.Vector3;

public abstract class PrimitiveObject extends Object3D {
	protected Color fillColor;
	protected Color borderColor;
	protected boolean printBorder;

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

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public boolean isPrintBorder() {
		return printBorder;
	}

	public void setPrintBorder(boolean printBorder) {
		this.printBorder = printBorder;
	}
}
