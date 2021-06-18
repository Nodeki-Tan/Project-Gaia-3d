package shaders.chunkShader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

import java.nio.IntBuffer;

public class ChunkShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "/shaders/chunkShader/chunkVertexShader.txt";
	private static final String FRAGMENT_FILE = "/shaders/chunkShader/chunkFragmentShader.txt";

	private int location_projectionViewMatrix;
	private int location_tex;

	private IntBuffer idsBuffer = BufferUtils.createIntBuffer(16);

	public ChunkShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "data");
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
