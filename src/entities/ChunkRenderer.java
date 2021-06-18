  
package entities;

import org.lwjgl.util.vector.Vector3f;

import core.MapCore;
import managers.MapManager;
import managers.MeshingManager;
import models.ChunkModel;
import models.ChunkRawModel;
import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class ChunkRenderer{
	
	private Vector3f position;
	private ChunkModel chunkModel;
	private ChunkRawModel rawModel;
	
	public ChunkRenderer(Vector3f position){
		this.position = position;
		
		MapManager.getChunkModelList().add(this);
		MeshingManager.getToDoMapModelsList().add(this);
		
	}
	
	public void setChunkModel(ChunkModel chunkModel) {
		this.chunkModel = chunkModel;
	}
	
	public ChunkRawModel getRawModel() {
		return rawModel;
	}

	public void setRawModel(ChunkRawModel rawModel) {
		this.rawModel = rawModel;
	}

	public ChunkModel getChunkModel() {
		return chunkModel;
	}

	public void Update() {

		MeshingManager.getToDoMapModelsList().add(this);

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
	
	public void destroySelf() {
		
		MeshingManager.getToDeleteModelsList().add(this);
		
		MeshingManager.getToDoMapModelsList().remove(this);
		
		MeshingManager.getToLoadModelsList().remove(this);
		
		MapManager.getChunkModelList().remove(this);
		
	}
	
}
