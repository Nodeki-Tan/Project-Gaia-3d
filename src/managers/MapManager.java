package managers;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import entities.ChunkRenderer;

public class MapManager {
	
	private static List<ChunkRenderer> chunkList = Collections.synchronizedList(new CopyOnWriteArrayList<ChunkRenderer>());
	
	public static List<ChunkRenderer> getChunkModelList() {
		return chunkList;
	}
	
	public static void cleanUp() {
		chunkList = null;
	}
	
}
