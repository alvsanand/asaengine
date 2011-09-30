package es.alvsanand.asaengine.math;

import java.io.Serializable;

public class Vector3 extends SVector3 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1893122555619789092L;
	
	private static Vector3 tmp = new Vector3();
	private static Vector3 tmp2 = new Vector3();
	private static Vector3 tmp3 = new Vector3();

	public Vector3() {
	}

	public Vector3(float x, float y, float z) {
		this.set(x, y, z);
	}

	public Vector3(Vector3 vector) {
		this.set(vector);
	}

	public Vector3(float[] values) {
		this.set(values[0], values[1], values[2]);
	}

	public Vector3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3 set(Vector3 vector) {
		return this.set(vector.x, vector.y, vector.z);
	}

	public Vector3 set(float[] values) {
		return this.set(values[0], values[1], values[2]);
	}

	public Vector3 cpy() {
		return new Vector3(this);
	}

	public Vector3 tmp() {
		return tmp.set(this);
	}

	public Vector3 tmp2() {
		return tmp2.set(this);
	}

	Vector3 tmp3() {
		return tmp3.set(this);
	}

	public Vector3 add(Vector3 vector) {
		return this.add(vector.x, vector.y, vector.z);
	}

	public Vector3 add(float x, float y, float z) {
		return this.set(this.x + x, this.y + y, this.z + z);
	}

	public Vector3 add(float values) {
		return this.set(this.x + values, this.y + values, this.z + values);
	}

	public Vector3 sub(Vector3 a_vec) {
		return this.sub(a_vec.x, a_vec.y, a_vec.z);
	}

	public Vector3 sub(float x, float y, float z) {
		return this.set(this.x - x, this.y - y, this.z - z);
	}

	public Vector3 sub(float value) {
		return this.set(this.x - value, this.y - value, this.z - value);
	}

	public Vector3 mul(float value) {
		return this.set(this.x * value, this.y * value, this.z * value);
	}

	public Vector3 div(float value) {
		float d = 1 / value;
		return this.set(this.x * d, this.y * d, this.z * d);
	}

	public float len() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public float len2() {
		return x * x + y * y + z * z;
	}

	public boolean idt(Vector3 vector) {
		return x == vector.x && y == vector.y && z == vector.z;
	}

	public float dst(Vector3 vector) {
		float a = vector.x - x;
		float b = vector.y - y;
		float c = vector.z - z;

		a *= a;
		b *= b;
		c *= c;

		return (float) Math.sqrt(a + b + c);
	}

	public Vector3 nor() {
		if (x == 0 && y == 0 && z == 0)
			return this;
		else
			return this.div(this.len());
	}

	public float dot(Vector3 vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	public Vector3 crs(Vector3 vector) {
		return this.set(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x);
	}

	public Vector3 crs(float x, float y, float z) {
		return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
	}

	public Vector3 mul(Matrix4 matrix) {
		float l_mat[] = matrix.val;
		return this.set(x * l_mat[Matrix4.M00] + y * l_mat[Matrix4.M01] + z * l_mat[Matrix4.M02] + l_mat[Matrix4.M03], x * l_mat[Matrix4.M10] + y
				* l_mat[Matrix4.M11] + z * l_mat[Matrix4.M12] + l_mat[Matrix4.M13], x * l_mat[Matrix4.M20] + y * l_mat[Matrix4.M21] + z
				* l_mat[Matrix4.M22] + l_mat[Matrix4.M23]);
	}

	public Vector3 prj(Matrix4 matrix) {
		float l_mat[] = matrix.val;
		float l_w = x * l_mat[Matrix4.M30] + y * l_mat[Matrix4.M31] + z * l_mat[Matrix4.M32] + l_mat[Matrix4.M33];
		return this.set((x * l_mat[Matrix4.M00] + y * l_mat[Matrix4.M01] + z * l_mat[Matrix4.M02] + l_mat[Matrix4.M03]) / l_w, (x
				* l_mat[Matrix4.M10] + y * l_mat[Matrix4.M11] + z * l_mat[Matrix4.M12] + l_mat[Matrix4.M13])
				/ l_w, (x * l_mat[Matrix4.M20] + y * l_mat[Matrix4.M21] + z * l_mat[Matrix4.M22] + l_mat[Matrix4.M23]) / l_w);
	}

	public Vector3 rot(Matrix4 matrix) {
		float l_mat[] = matrix.val;
		return this.set(x * l_mat[Matrix4.M00] + y * l_mat[Matrix4.M01] + z * l_mat[Matrix4.M02], x * l_mat[Matrix4.M10] + y * l_mat[Matrix4.M11] + z
				* l_mat[Matrix4.M12], x * l_mat[Matrix4.M20] + y * l_mat[Matrix4.M21] + z * l_mat[Matrix4.M22]);
	}

	public boolean isUnit() {
		return this.len() == 1;
	}

	public boolean isZero() {
		return x == 0 && y == 0 && z == 0;
	}

	public Vector3 lerp(Vector3 target, float alpha) {
		Vector3 r = this.mul(1.0f - alpha);
		r.add(target.tmp().mul(alpha));
		return r;
	}

	public Vector3 slerp(Vector3 target, float alpha) {
		float dot = dot(target);
		if (dot > 0.99995 || dot < 0.9995) {
			this.add(target.tmp().sub(this).mul(alpha));
			this.nor();
			return this;
		}

		if (dot > 1)
			dot = 1;
		if (dot < -1)
			dot = -1;

		float theta0 = (float) Math.acos(dot);
		float theta = theta0 * alpha;
		Vector3 v2 = target.tmp().sub(x * dot, y * dot, z * dot);
		v2.nor();
		return this.mul((float) Math.cos(theta)).add(v2.mul((float) Math.sin(theta))).nor();
	}

	public float dot(float x, float y, float z) {
		return this.x * x + this.y * y + this.z * z;
	}

	public float dst2(Vector3 point) {

		float a = point.x - x;
		float b = point.y - y;
		float c = point.z - z;

		a *= a;
		b *= b;
		c *= c;

		return a + b + c;
	}

	public float dst2(float x, float y, float z) {
		float a = x - this.x;
		float b = y - this.y;
		float c = z - this.z;

		a *= a;
		b *= b;
		c *= c;

		return a + b + c;
	}

	public float dst(float x, float y, float z) {
		return (float) Math.sqrt(dst2(x, y, z));
	}

	public Vector3 scale(float scalarX, float scalarY, float scalarZ) {
		x *= scalarX;
		y *= scalarY;
		z *= scalarZ;
		return this;
	}
	
	public float angleBetween(Vector3 other) { 
		float angle; 
		
		float dot = this.dot(other);
		
		float len1 = this.len();
		float len2 = other.len();
		
		if(len1==0 && len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public float angleBetween(float x, float y, float z) { 
		float angle; 
		
		float dot = this.dot(x, y, z);
		
		float len1 = this.len();
		float len2 = (float) Math.sqrt(x * x + y * y + z * z);
		
		if(len1==0 || len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public float angleBetweenXY(float x, float y) { 
		float angle; 
		
		float dot = this.x * x + this.y * y;
		
		float len1 = (float) Math.sqrt(this.x * this.x + this.y * this.y);
		float len2 = (float) Math.sqrt(x * x + y * y);
		
		if(len1==0 || len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public float angleBetweenXZ(float x, float z) { 
		float angle; 
		
		float dot = this.x * x + this.z * z;
		
		float len1 = (float) Math.sqrt(this.x * this.x + this.z * this.z);
		float len2 = (float) Math.sqrt(x * x + z * z);
		
		if(len1==0 || len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public float angleBetweenYZ(float y, float z) { 
		float angle; 
		
		float dot = this.y * y + this.z * z;
		
		float len1 = (float) Math.sqrt(this.y * this.y + this.z * this.z);
		float len2 = (float) Math.sqrt(y * y + z * z);
		
		if(len1==0 || len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public float[] toArray(){
		return new float[]{x, y, z};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3 other = (Vector3) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%.4f", x) + ", " + String.format("%.4f", y) + ", " + String.format("%.4f", z);
	}
}