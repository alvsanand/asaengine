package es.alvsanand.asaengine.math.util;

import android.opengl.Matrix;
import android.util.FloatMath;
import es.alvsanand.asaengine.math.Vector3;

public class Vector3Utils {
	public static Vector3 cpy(Vector3 a) {
		return new Vector3(a.x, a.y, a.z);
	}

	public static Vector3 add(Vector3 aTmp, float x, float y, float z) {
		Vector3 a = cpy(aTmp);

		a.x += x;
		a.y += y;
		a.z += z;
		
		return a;
	}

	public static Vector3 add(Vector3 aTmp, Vector3 b) {
		Vector3 a = cpy(aTmp);

		a.x += b.x;
		a.y += b.y;
		a.z += b.z;
		
		return a;
	}

	public static Vector3 sub(Vector3 aTmp, float x, float y, float z) {
		Vector3 a = cpy(aTmp);

		a.x -= x;
		a.y -= y;
		a.z -= z;
		
		return a;
	}

	public static Vector3 sub(Vector3 aTmp, Vector3 b) {
		Vector3 a = cpy(aTmp);

		a.x -= b.x;
		a.y -= b.y;
		a.z -= b.z;
		return a;
	}

	public static Vector3 mul(Vector3 aTmp, float scalar) {
		Vector3 a = cpy(aTmp);

		a.x *= scalar;
		a.y *= scalar;
		a.z *= scalar;
		
		return a;
	}

	public static float len(Vector3 a) {
		return FloatMath.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
	}

	public static Vector3 nor(Vector3 aTmp) {
		Vector3 a = cpy(aTmp);

		float len = len(a);
		if (len != 0) {
			a.x /= len;
			a.y /= len;
			a.z /= len;
		}

		return a;
	}

	public static Vector3 rotate(Vector3 aTmp, float angle, float axisX, float axisY, float axisZ) {
		Vector3 a = cpy(aTmp);

		float[] matrix = new float[16];
		float[] inVec = new float[4];
		float[] outVec = new float[4];

		inVec[0] = a.x;
		inVec[1] = a.y;
		inVec[2] = a.z;
		inVec[3] = 1;
		Matrix.setIdentityM(matrix, 0);
		Matrix.rotateM(matrix, 0, angle, axisX, axisY, axisZ);
		Matrix.multiplyMV(outVec, 0, matrix, 0, inVec, 0);
		a.x = outVec[0];
		a.y = outVec[1];
		a.z = outVec[2];

		return a;
	}

	public static float dist(Vector3 a, Vector3 b) {
		float distX = a.x - b.x;
		float distY = a.y - b.y;
		float distZ = a.z - b.z;
		return FloatMath.sqrt(distX * distX + distY * distY + distZ * distZ);
	}

	public static float dist(Vector3 a, float x, float y, float z) {
		float distX = a.x - x;
		float distY = a.y - y;
		float distZ = a.z - z;
		return FloatMath.sqrt(distX * distX + distY * distY + distZ * distZ);
	}

	public static float distSquared(Vector3 a, Vector3 b) {
		float distX = a.x - b.x;
		float distY = a.y - b.y;
		float distZ = a.z - b.z;
		return distX * distX + distY * distY + distZ * distZ;
	}

	public static float distSquared(Vector3 a, float x, float y, float z) {
		float distX = a.x - x;
		float distY = a.y - y;
		float distZ = a.z - z;
		return distX * distX + distY * distY + distZ * distZ;
	}

}
