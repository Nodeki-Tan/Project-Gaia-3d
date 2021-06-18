package managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import entities.Entity;
import models.Sprite;

public class EntityManager {

	private static List<Entity> entityList = Collections.synchronizedList(new CopyOnWriteArrayList<Entity>());
	
	public static List<Entity> getEntityList() {
		return entityList;
	}
	
	public static void tick() {
		for(Entity entity: entityList) {
			entity.tick();
		}
	}
	
	public static void cleanUp() {
		entityList.clear();
	}
	
}
