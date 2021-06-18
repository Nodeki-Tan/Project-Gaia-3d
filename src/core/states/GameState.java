package core.states;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.MapCore;
import core.MeshingCore;
import core.RenderCore;
import entities.Entity;
import managers.AssetManager;
import managers.EntityManager;
import managers.GUIManager;
import managers.MapManager;
import models.GuiImage;
import models.Image;
import models.Sprite;

public class GameState extends State{
	
	private static MapCore mapCore;
	private static MeshingCore meshingCore;
	
	public GameState() {
	}
	
	public void init() {
		
		meshingCore = new MeshingCore();
		meshingCore.start();
		
		mapCore = new MapCore();
		mapCore.start();
		
		Sprite SoldierSpr = new Sprite(0, 8, 9, 0.5f);
		
		Sprite[] entitySoldierSpr= {
				SoldierSpr
				};
		
		Entity soldier1 = new Entity(new Vector3f(0, 96, 0), new Vector3f(1, 1, 1), entitySoldierSpr);
		
		Sprite SoldierSpr2 = new Sprite(0, 8, 9, 1f);
		
		Sprite[] entitySoldierSpr2= {
				SoldierSpr2
				};
		
		Entity soldier2 = new Entity(new Vector3f(1, 96, 0), new Vector3f(1, 1, 1), entitySoldierSpr2);
		
		/* ALL OF THE GUI CODE IS PLACEHOLDER!!!
			REMEMBER TO CHANGE IT TO USE TILES OR SPRITES!!! 
		
		Sprite guiSpr = new Sprite(AssetManager.GUItexture.getID(), 0, 0, 0f);
		
		Sprite[] gui= {
				guiSpr
				};
		
		GuiImage mainGui = new GuiImage(new Vector2f(0,0), new Vector2f(106.6f,48), gui);
		
		*/
		
		//GUIManager.getGuiList().add(mainGui);
		
		EntityManager.getEntityList().add(soldier1);
		EntityManager.getEntityList().add(soldier2);
	}
	
	@Override
	public void tick() {
		
		RenderCore.camera.tick();
		EntityManager.tick();
		GUIManager.tick();
		
	}
	
}
