package models;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class VoxelModel {


	public static Vector3f[] PX_POS = {
			
			new Vector3f(1, 1, 1),
			new Vector3f(1, 0, 1),
			new Vector3f(1, 1, 0),
			new Vector3f(1, 0, 0)
			
	};
	
	public static Vector3f[] NX_POS = {
			
			new Vector3f(0, 1, 0),
			new Vector3f(0, 0, 0),
			new Vector3f(0, 1, 1),
			new Vector3f(0, 0, 1)
			
	};
	
	public static Vector3f[] PY_POS = {
			
			new Vector3f(1, 1, 1),
			new Vector3f(1, 1, 0),
			new Vector3f(0, 1, 1),
			new Vector3f(0, 1, 0)
			
	};
	
	public static Vector3f[] NY_POS = {
			
			new Vector3f(0, 0, 1),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 0, 1),
			new Vector3f(1, 0, 0)
			
	};
	
	public static Vector3f[] PZ_POS = {
			
			new Vector3f(0, 1, 1),
			new Vector3f(0, 0, 1),
			new Vector3f(1, 1, 1),
			new Vector3f(1, 0, 1)
			
	};
	
	public static Vector3f[] NZ_POS = {
			
			new Vector3f(1, 1, 0),
			new Vector3f(1, 0, 0),
			new Vector3f(0, 1, 0),
			new Vector3f(0, 0, 0)
			
	};
	
	public static Vector2f[] UV = {
			
			new Vector2f(0, 0),
			new Vector2f(0, 1),
			new Vector2f(1, 0),
			new Vector2f(1, 1)
			
	};
	
}
