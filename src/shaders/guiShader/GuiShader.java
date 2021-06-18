package shaders.guiShader;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class GuiShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "/shaders/guiShader/guiVertexShader.txt";
	private static final String GEOMETRY_FILE = "/shaders/guiShader/guiGeometryShader.txt";
	private static final String FRAGMENT_FILE = "/shaders/guiShader/guiFragmentShader.txt";
	
	private int location_projectionViewMatrix;

	public GuiShader() {
		super(VERTEX_FILE, GEOMETRY_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionViewMatrix = super.getUniformLocation("projectionViewMatrix");
	}
	
	public void loadProjectionViewMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionViewMatrix, matrix);
	}
	
}
