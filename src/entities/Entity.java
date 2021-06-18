package entities;

import org.lwjgl.util.vector.Vector3f;

import models.Sprite;

public class Entity {

	protected Sprite[] tiles;
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;

	public Entity(Vector3f position, Vector3f scale, Sprite[] tiles)  {
		this.position = position;
		this.rotation = new Vector3f(30,30,30);
		this.scale = scale;
		this.tiles = tiles;
	}

	public Sprite[] getTiles() {
		return tiles;
	}

	public void setTiles(Sprite[] tiles) {
		this.tiles = tiles;
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotation.x += dx;
		this.rotation.y += dy;
		this.rotation.z += dz;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	public void tick() {
		for(int i = 0; i < tiles.length; i++) {
			tiles[i].tick();
		}
		
		increaseRotation(1, 1, 1);
	}
	
}
