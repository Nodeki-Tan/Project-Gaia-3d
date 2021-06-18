package models;

public class ChunkModel {
	
	private int vaoID;
	private int vertexCount;
	
	private int positionsVbo;
	private int indicesVbo;
	
	public ChunkModel(int vaoID, int positionsVbo, int indicesVbo, int vertexCount){
		this.vaoID = vaoID;
		this.positionsVbo = positionsVbo;
		this.indicesVbo = indicesVbo;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public int getPositionsVbo() {
		return positionsVbo;
	}

	public int getIndicesVbo() {
		return indicesVbo;
	}
	
}
