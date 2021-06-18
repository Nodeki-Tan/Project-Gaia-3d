package managers;

import mapData.Tile;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import shaders.chunkShader.ChunkShader;
import shaders.guiShader.GuiShader;
import shaders.spriteShader.SpriteShader;
import textures.ModelTexture;
import toolbox.MeshGenerator;

public class AssetManager {

	public static Loader loader;
	public static MeshGenerator meshGenerator;
	
	public static GuiShader guiShader;
	public static SpriteShader entityShader;
	public static ChunkShader chunkShader;
	
	public static RawModel spriteModel;
	
	public static ModelTexture[] textureAtlas;
	public static ModelTexture GUItexture;
	
	public static void initAssetsCore(){

		TileManager.loadTiles();

		loader = new Loader();
		meshGenerator = new MeshGenerator();
		
		guiShader = new GuiShader();
		entityShader = new SpriteShader();
		chunkShader = new ChunkShader();
		
		float[] pos= {
				0,0,0
				};
		
		textureAtlas = new ModelTexture[16];
		
		for(int i = 0; i < textureAtlas.length; i++) {
			
			textureAtlas[i] = new ModelTexture(loader.loadTexture("Atlas-" + i));
		}
		
		GUItexture = new ModelTexture(loader.loadTexture("GUI"));
		
		spriteModel = loader.loadToVAO(pos);
		
	}
	
	public static void cleanUp() {

		guiShader.cleanUp();
		entityShader.cleanUp();
		chunkShader.cleanUp();
		loader.cleanUp();
		
	}
	
}
