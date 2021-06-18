package models;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Image {

	private int texture;

	public Image(int texture) {
		this.texture = texture;
	}
	
	public int getTexture() {
		return texture;
	}
	
}
