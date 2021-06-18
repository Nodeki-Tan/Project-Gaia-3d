package gui;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GuiEntity {
	
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	
	public GuiEntity(int texture, Vector2f position, Vector2f scale) {
		super();
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	public void tick() {
		
	}

	public void render() {
		// TODO Auto-generated method stub
		
	}

}
