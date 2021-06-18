package managers;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import entities.ChunkRenderer;
import models.ChunkRawModel;
import renderEngine.Loader;

public class MeshingManager {
	
	private static List<ChunkRenderer> toDoMapModelsList = Collections.synchronizedList(new CopyOnWriteArrayList<ChunkRenderer>());
	
	private static List<ChunkRenderer> toLoadModelsList = Collections.synchronizedList(new CopyOnWriteArrayList<ChunkRenderer>());
	
	private static List<ChunkRenderer> toDeleteModelsList = Collections.synchronizedList(new CopyOnWriteArrayList<ChunkRenderer>());
	
	public static List<ChunkRenderer> getToLoadModelsList() {
		return toLoadModelsList;
	}
	
	public static List<ChunkRenderer> getToDoMapModelsList() {
		return toDoMapModelsList;
	}
	
	public static List<ChunkRenderer> getToDeleteModelsList() {
		return toDeleteModelsList;
	}
	
	public static void generateMap() {
		
		for(int i = 0; i < 16; i ++) {
			
			if(toDoMapModelsList.size() > 0) {
				
				ChunkRenderer model = toDoMapModelsList.get(0);
				ChunkRawModel rawModel = AssetManager.meshGenerator.generateChunkModel(model.getPosition());

				model.setRawModel(rawModel);

				toDoMapModelsList.remove(model);

				if(rawModel != null) {
					toLoadModelsList.add(model);
				}

			}
			
		}
		
	}
	
	public static void render() {
		
		for(int i = 0; i < 8; i ++) {
			
			if(toLoadModelsList.size() > 0) {
				
				ChunkRenderer model = toLoadModelsList.get(0);

				if(model.getRawModel() != null) {

					if (model.getChunkModel() != null) {

						Loader.cleanUpModel(
								model.getChunkModel().getVaoID(),
								model.getChunkModel().getPositionsVbo(),
								model.getChunkModel().getIndicesVbo());

					}

					model.setChunkModel(AssetManager.loader.loadToVAO(
							model.getRawModel().getDataList(),
							model.getRawModel().getIndicesList()));

					model.setRawModel(null);
				}

				toLoadModelsList.remove(model);
				
			}
			
		}
		
		for(int i = 0; i < 16; i ++) {
			
			if(toDeleteModelsList.size() > 0) {
				
				ChunkRenderer chunk = toDeleteModelsList.get(0);
				
				if(chunk.getChunkModel() != null) {
					
					Loader.cleanUpModel(
							chunk.getChunkModel().getVaoID(), 
							chunk.getChunkModel().getPositionsVbo(), 
							chunk.getChunkModel().getIndicesVbo());
					
					chunk.setChunkModel(null);
					chunk.setRawModel(null);
					
				}
				
				toDeleteModelsList.remove(chunk);
				
			}
			
		}
		
	}
	
	public static void cleanUp() {
		toDoMapModelsList.clear();
		toLoadModelsList.clear();
		toDeleteModelsList.clear();
	}
	
}
