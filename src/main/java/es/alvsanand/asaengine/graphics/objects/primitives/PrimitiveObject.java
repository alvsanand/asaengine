package es.alvsanand.asaengine.graphics.objects.primitives;

import es.alvsanand.asaengine.graphics.color.Color;
import es.alvsanand.asaengine.graphics.objects.Object3D;
import es.alvsanand.asaengine.math.Vector3;

public abstract class PrimitiveObject extends Object3D {
	Color color;
	
	public PrimitiveObject(Vector3 position, Color color) {
		super(position);
		
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
