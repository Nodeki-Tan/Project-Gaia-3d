package toolbox;

import core.RenderCore;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths {
	
	public static Vector3f Vector3Zero = new Vector3f(0,0,0);
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry,
			float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,
				viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public static Matrix4f orthoMatrix(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f matrix = new Matrix4f();
        float x_orth = 2 / (right - left);
        float y_orth = 2 / (top - bottom);
        float z_orth = -2 / (far - near);

        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);

        matrix.m00 = x_orth;
        matrix.m10 = 0;
        matrix.m20 = 0;
        matrix.m30 = 0;
        matrix.m01 = 0;
        matrix.m11 = y_orth;
        matrix.m21 = 0;
        matrix.m31 = 0;
        matrix.m02 = 0;
        matrix.m12 = 0;
        matrix.m22 = z_orth;
        matrix.m32 = 0;
        matrix.m03 = tx;
        matrix.m13 = ty;
        matrix.m23 = tz;
        matrix.m33 = 1;

        return matrix;
    }

    public static float Normaliced(float val, float max, float min) {
	    return (val - min) / (max - min);
	}

}
