package shaders.spriteShader;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class SpriteShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "/shaders/spriteShader/spriteVertexShader.txt";
	private static final String GEOMETRY_FILE = "/shaders/spriteShader/spriteGeometryShader.txt";
	private static final String FRAGMENT_FILE = "/shaders/spriteShader/spriteFragmentShader.txt";
	
	private int location_projectionViewMatrix;
	private int location_tex;

	private IntBuffer idsBuffer = BufferUtils.createIntBuffer(16);
	
	public SpriteShader() {
		super(VERTEX_FILE, GEOMETRY_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "frame");
		super.bindAttribute(2, "textureSlot");
		super.bindAttribute(3, "transformationMatrix");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionViewMatrix = super.getUniformLocation("projectionViewMatrix");
		location_tex = super.getUniformLocation("textureSampler");
	}
	
	public void loadProjectionViewMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionViewMatrix, matrix);
	}
	
	public void loadTextures(int[] tex){
		loadIntArray(location_tex, tex);
	}
	
	protected void loadIntArray(int location, int[] value){
		idsBuffer.clear();
		idsBuffer.put(value);
		idsBuffer.flip();
		GL20.glUniform1(location, idsBuffer);
	}
	
}
