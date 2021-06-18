package core;

import entities.Camera;
import managers.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import renderEngine.Renderer;
import toolbox.InputHandler;

public class RenderCore {
	
	private static Renderer renderer;
	private static GameCore core;
	static InputHandler input = new InputHandler();
	
	public static Camera camera;
	
	public static int frames = 0;
	
	public static void main(String[] args) throws LWJGLException {
		initRenderCore(1280,720, "Tales of Gaia!");

		Mouse.create();

		core = new GameCore();
		core.start();
		
		while(core.running){
			
			MeshingManager.render();
			
			renderer.prepare();
			//renderer.renderGUIs(GUIManager.getGuiList());
			renderer.renderEntities(EntityManager.getEntityList());
			renderer.renderMap(MapManager.getChunkModelList());
			DisplayManager.updateDisplay();
			frames++;
			
		}

		Mouse.destroy();
		AssetManager.cleanUp();
		EntityManager.cleanUp();
		GUIManager.cleanUp();
		MapManager.cleanUp();
		MeshingManager.cleanUp();                       
		DisplayManager.closeDisplay();
		
	}

	
	public static void initRenderCore(int width, int height, String name) {
		DisplayManager.createDisplay(width,height, name);
		
		AssetManager.initAssetsCore();
		renderer = new Renderer();
		camera = new Camera();
	}
	
}
