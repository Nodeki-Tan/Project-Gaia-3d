package mapData;

import core.MapCore;
import org.lwjgl.util.vector.Vector3f;
import entities.ChunkRenderer;

public class Chunk {
	
	private short[] tiles;

	private boolean isSaved = true;
	
	private boolean render = false;

	private int airBlocks = 0;

	private Vector3f position;
	
	private ChunkRenderer model;
	
	public Chunk(short[] tiles, int x, int y, int z) {
		super();
		this.tiles = tiles;
		position = new Vector3f(x, y, z);
	}

	public short getTile(int x, int y, int z) {
		
		if(x <= -1) {
			x += MapCore.CHUNK_WIDTH;
		}
		
		if(y <= -1) {
			y += 1;
		}

		if(z <= -1) {
			z += MapCore.CHUNK_WIDTH;
		}
		
		//System.out.println("Tile in [" + x + "," + y + "]");
		
		int index = x + (y * (MapCore.CHUNK_WIDTH * MapCore.CHUNK_WIDTH)) + (z * MapCore.CHUNK_WIDTH);
		
		return tiles[index];
	}
	
	public void setTile(int x, int y, int z, short value) {
		
		if(x <= -1) {
			x += MapCore.CHUNK_WIDTH;
		}
		
		if(y <= -1) {
			y += 1;
		}

		if(z <= -1) {
			z += MapCore.CHUNK_WIDTH;
		}
		
		//System.out.println("Tile in [" + x + "," + y + "," + z +"]");
		
		int index = x + (y * (MapCore.CHUNK_WIDTH * MapCore.CHUNK_WIDTH)) + (z * MapCore.CHUNK_WIDTH);
		
		tiles[index] = value;

		if(value == 0){
			airBlocks++;
		} else if(tiles[index] == 0) {
			airBlocks--;
		}

		isSaved = false;

		if(model != null) {
			model.Update();
		}

	}
	
	public void setSaved() {
		isSaved = true;
	}
	
	public short[] getTiles() {
		return tiles;
	}

	public boolean getSaved() {
		return isSaved;
	}
	
	public boolean getRender() {
		return render;
	}

	public boolean getCanRender() {
		if (airBlocks >= 1)
			return true;

		return false;
	}

	public int getAirBlocks() {
		return airBlocks;
	}

	public void setAirBlocks(int airBlocks) {
		this.airBlocks = airBlocks;
	}

	public ChunkRenderer getModel() {
		return model;
	}

	public void setModel(ChunkRenderer model) {
		this.model = model;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void createModel() {

		model = new ChunkRenderer(new Vector3f(
				position.x * MapCore.CHUNK_WIDTH, 
				position.y * MapCore.CHUNK_WIDTH, 
				position.z * MapCore.CHUNK_WIDTH));
		
		render = true;
		
	}
	
	public void destroyModel() {
		model.destroySelf();
		render = false;
	}

	public void destroyData() {
		tiles = null;
	}
	
}
