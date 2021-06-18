package managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import models.GuiImage;
import models.Image;

public class GUIManager {
		
	private static List<GuiImage> guiList = Collections.synchronizedList(new CopyOnWriteArrayList<GuiImage>());
	
	
	public static List<GuiImage> getGuiList() {
		return guiList;
	}
	
	public static void tick() {
		for(GuiImage guiEntity: guiList) {
			guiEntity.tick();
		}
	}
	
	public static void cleanUp() {
		guiList.clear();
	}
	
}
