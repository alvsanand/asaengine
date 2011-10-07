package es.alvsanand.asaengine.math;

import android.util.FloatMath;


public class Vector3Util{
	public static Vector3 set(Vector3 vectorA, float x, float y, float z) {
		vectorA.x = x;
		vectorA.y = y;
		vectorA.z = z;
		return vectorA;
	}

	public static Vector3 set(Vector3 vectorA, Vector3 vectorB) {
		return set(vectorA, vectorB.x, vectorB.y, vectorB.z);
	}

	public static Vector3 set(Vector3 vectorA, float[] values) {
		return set(vectorA, values[0], values[1], values[2]);
	}
	
	public static Vector3 cpy(Vector3 vectorA) {
		Vector3 newSVector = new Vector3();
		
		newSVector.x = vectorA.x;
		newSVector.y = vectorA.y;
		newSVector.z = vectorA.z;
		
		return newSVector;
	}

	public static Vector3 add(Vector3 vectorA, Vector3 vectorB) {
		vectorA.x += vectorB.x;
		vectorA.y += vectorB.y;
		vectorA.z += vectorB.z;
		
		return vectorA;
	}

	public static Vector3 add(Vector3 vectorA, float x, float y, float z) {
		vectorA.x += x;
		vectorA.y += y;
		vectorA.z += z;
		
		return vectorA;
	}

	public static Vector3 add(Vector3 vectorA, float value) {
		vectorA.x += value;
		vectorA.y += value;
		vectorA.z += value;
		
		return vectorA;
	}

	public static Vector3 sub(Vector3 vectorA, Vector3 vectorB) {
		vectorA.x -= vectorB.x;
		vectorA.y -= vectorB.y;
		vectorA.z -= vectorB.z;
		
		return vectorA;
	}

	public static Vector3 sub(Vector3 vectorA, float x, float y, float z) {
		vectorA.x -= x;
		vectorA.y -= y;
		vectorA.z -= z;
		
		return vectorA;
	}

	public static Vector3 sub(Vector3 vectorA, float value) {
		vectorA.x -= value;
		vectorA.y -= value;
		vectorA.z -= value;
		
		return vectorA;
	}

	public static Vector3 mul(Vector3 vectorA, float value) {
		vectorA.x = value * vectorA.x;
		vectorA.y = value * vectorA.y;
		vectorA.z = value * vectorA.z;
		
		return vectorA;
	}

	public static Vector3 div(Vector3 vectorA, float value) {
		float d = 1 / value;
		vectorA.x = d * vectorA.x;
		vectorA.y = d * vectorA.y;
		vectorA.z = d * vectorA.z;
		
		return vectorA;
	}

	public static float len(Vector3 vectorA) {
		return FloatMath.sqrt(vectorA.x * vectorA.x + vectorA.y * vectorA.y + vectorA.z * vectorA.z);
	}

	public static float len2(Vector3 vectorA) {
		return vectorA.x * vectorA.x + vectorA.y * vectorA.y + vectorA.z * vectorA.z;
	}

	public static boolean idt(Vector3 vectorA, Vector3 vectorB) {
		return vectorA.x == vectorB.x && vectorA.y == vectorB.y && vectorA.z == vectorB.z;
	}

	public static float dst(Vector3 vectorA, Vector3 vectorB) {
		float a = vectorB.x - vectorA.x;
		float b = vectorB.y - vectorA.y;
		float c = vectorB.z - vectorA.z;

		a *= a;
		b *= b;
		c *= c;

		return FloatMath.sqrt(a + b + c);
	}

	public static float dst(Vector3 vectorA, float x, float y, float z) {
		float a = x - vectorA.x;
		float b = y - vectorA.y;
		float c = z - vectorA.z;

		a *= a;
		b *= b;
		c *= c;

		return FloatMath.sqrt(a + b + c);
	}

	public static Vector3 nor(Vector3 vectorA) {
		if (vectorA.x == 0 && vectorA.y == 0 && vectorA.z == 0)
			return vectorA;
		else
			return Vector3Util.div(vectorA, Vector3Util.len(vectorA));
	}

	public static float dot(Vector3 vectorA, Vector3 vectorB) {
		return vectorA.x * vectorB.x + vectorA.y * vectorB.y + vectorA.z * vectorB.z;
	}

	public static Vector3 crs(Vector3 vectorA, Vector3 vectorB) {
		vectorA.x = vectorA.y * vectorB.z - vectorA.z * vectorB.y;
		vectorA.y = vectorA.z * vectorB.x - vectorA.x * vectorB.z;
		vectorA.z = vectorA.x * vectorB.y - vectorA.y * vectorB.x;
		
		return vectorA;
	}

	public static Vector3 crs(Vector3 vectorA, float x, float y, float z) {
		vectorA.x = vectorA.y * z - vectorA.z * y;
		vectorA.y = vectorA.z * x - vectorA.x * z;
		vectorA.z = vectorA.x * y - vectorA.y * x;
		
		return vectorA;
	}

	public static Vector3 mul(Vector3 vectorA, Matrix4 matrix) {
		float l_mat[] = matrix.val;
		
		vectorA.x = vectorA.x * l_mat[Matrix4.M00] + vectorA.y * l_mat[Matrix4.M01] + vectorA.z * l_mat[Matrix4.M02] + l_mat[Matrix4.M03];
		vectorA.y = vectorA.x * l_mat[Matrix4.M10] + vectorA.y * l_mat[Matrix4.M11] + vectorA.z * l_mat[Matrix4.M12] + l_mat[Matrix4.M13];
		vectorA.z = vectorA.x * l_mat[Matrix4.M20] + vectorA.y * l_mat[Matrix4.M21] + vectorA.z	* l_mat[Matrix4.M22] + l_mat[Matrix4.M23];		
		
		return vectorA;
	}

	public static Vector3 prj(Vector3 vectorA, Matrix4 matrix) {
		float l_mat[] = matrix.val;
		float l_w = vectorA.x * l_mat[Matrix4.M30] + vectorA.y * l_mat[Matrix4.M31] + vectorA.z * l_mat[Matrix4.M32] + l_mat[Matrix4.M33];

		
		vectorA.x = (vectorA.x * l_mat[Matrix4.M00] + vectorA.y * l_mat[Matrix4.M01] + vectorA.z * l_mat[Matrix4.M02] + l_mat[Matrix4.M03]) / l_w;
		vectorA.y = (vectorA.x * l_mat[Matrix4.M10] + vectorA.y * l_mat[Matrix4.M11] + vectorA.z * l_mat[Matrix4.M12] + l_mat[Matrix4.M13]) / l_w;
		vectorA.z = (vectorA.x * l_mat[Matrix4.M20] + vectorA.y * l_mat[Matrix4.M21] + vectorA.z * l_mat[Matrix4.M22] + l_mat[Matrix4.M23]) / l_w;
		
		return vectorA;
	}

	public static Vector3 rot(Vector3 vectorA, Matrix4 matrix) {
		float l_mat[] = matrix.val;

		
		vectorA.x = vectorA.x * l_mat[Matrix4.M00] + vectorA.y * l_mat[Matrix4.M01] + vectorA.z * l_mat[Matrix4.M02];
		vectorA.y = vectorA.x * l_mat[Matrix4.M10] + vectorA.y * l_mat[Matrix4.M11] + vectorA.z * l_mat[Matrix4.M12];
		vectorA.z = vectorA.x * l_mat[Matrix4.M20] + vectorA.y * l_mat[Matrix4.M21] + vectorA.z * l_mat[Matrix4.M22];
		
		return vectorA;
	}

	public static boolean isUnit(Vector3 vectorA) {
		return Vector3Util.len(vectorA) == 1;
	}

	public static boolean isZero(Vector3 vectorA) {
		return vectorA.x == 0 && vectorA.y == 0 && vectorA.z == 0;
	}

	public static Vector3 lerp(Vector3 vectorA, Vector3 target, float alpha) {
		Vector3 r = mul(vectorA, 1.0f - alpha);
		add(r, mul(cpy(vectorA), alpha));
		
		return r;
	}

	public static Vector3 slerp(Vector3 vectorA, Vector3 target, float alpha) {
		float dot = dot(vectorA, target);
		if (dot > 0.99995 || dot < 0.9995) {
			mul(sub(add(vectorA, cpy(target)), vectorA), alpha);
			nor(vectorA);
			return vectorA;
		}

		if (dot > 1)
			dot = 1;
		if (dot < -1)
			dot = -1;

		float theta0 = (float) Math.acos(dot);
		float theta = theta0 * alpha;
		Vector3 v2 = sub(cpy(target), vectorA.x * dot, vectorA.y * dot, vectorA.z * dot);
		nor(v2);
		
		return nor(add(mul(vectorA, FloatMath.cos(theta)), mul(v2, FloatMath.sin(theta))));
	}

	public static float dot(Vector3 vectorA, float x, float y, float z) {
		return vectorA.x * x + vectorA.y * y + vectorA.z * z;
	}

	public static float dst2(Vector3 vectorA, Vector3 vectorB) {
		float a = vectorB.x - vectorA.x;
		float b = vectorB.y - vectorA.y;
		float c = vectorB.z - vectorA.z;

		a *= a;
		b *= b;
		c *= c;

		return a + b + c;
	}

	public static float dst2(Vector3 vectorA, float x, float y, float z) {
		float a = x - vectorA.x;
		float b = y - vectorA.y;
		float c = z - vectorA.z;

		a *= a;
		b *= b;
		c *= c;

		return a + b + c;
	}

	public static Vector3 scale(Vector3 vectorA, float scalarX, float scalarY, float scalarZ) {
		vectorA.x *= scalarX;
		vectorA.y *= scalarY;
		vectorA.z *= scalarZ;
		return vectorA;
	}
	
	public static float angleBetween(Vector3 vectorA, Vector3 other) { 
		float angle; 
		
		float dot = dot(vectorA, other);
		
		float len1 = len(vectorA);
		float len2 = len(other);
		
		if(len1==0 && len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public static float angleBetween(Vector3 vectorA, float x, float y, float z) { 
		float angle; 
		
		float dot = dot(vectorA, x, y, z);
		
		float len1 = len(vectorA);
		float len2 = FloatMath.sqrt(x * x + y * y + z * z);
		
		if(len1==0 || len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public static float angleBetweenXY(Vector3 vectorA, float x, float y) { 
		float angle; 
		
		float dot = vectorA.x * x + vectorA.y * y;
		
		float len1 = FloatMath.sqrt(vectorA.x * vectorA.x + vectorA.y * vectorA.y);
		float len2 = FloatMath.sqrt(x * x + y * y);
		
		if(len1==0 || len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public static float angleBetweenXZ(Vector3 vectorA, float x, float z) { 
		float angle; 
		
		float dot = vectorA.x * x + vectorA.z * z;
		
		float len1 = FloatMath.sqrt(vectorA.x * vectorA.x + vectorA.z * vectorA.z);
		float len2 = FloatMath.sqrt(x * x + z * z);
		
		if(len1==0 || len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public static float angleBetweenYZ(Vector3 vectorA, float y, float z) { 
		float angle; 
		
		float dot = vectorA.y * y + vectorA.z * z;
		
		float len1 = FloatMath.sqrt(vectorA.y * vectorA.y + vectorA.z * vectorA.z);
		float len2 = FloatMath.sqrt(y * y + z * z);
		
		if(len1==0 || len2==0){
			return 0;
		}
		
		angle = (float)Math.acos(dot/(len1*len2));
		
		return angle; 
	}
	
	public static float[] toArray(Vector3 vectorA){
		return new float[]{vectorA.x, vectorA.y, vectorA.z};
	}
}