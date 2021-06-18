package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.ChunkModel;
import models.RawModel;

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	//for the universal sprite model
	public RawModel loadToVAO(float[] positions){
		int vaoID = createVAO();
		storeDataInAttributeList(0,3,positions);
		unbindVAO();
		return new RawModel(vaoID, positions.length/3);
	}

	//for common 3d models
	public RawModel loadToVAO(float[] positions, int[] indices, float[] uvs) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, uvs);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	//for the chunk's 3d models
	public ChunkModel loadToVAO(int[] positions, int[] indices) {
		int vaoID = createVAO();
		int indicesVbo = intBindIndicesBuffer(indices);
		
		int positionsVbo = intStoreDataInAttributeList(0, 3, positions);
		
		unbindVAO();
		return new ChunkModel(vaoID, positionsVbo, indicesVbo, indices.length);
	}
	
	//for instancing!!
	public int createEmptyVBO(int floatCount) {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4, GL15.GL_STREAM_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		return vbo;
	}
	
	//for instancing!!
	public void addIntancedAttributte(int vao, int vbo, int attribute, int dataSize, int instancedDataLength, int offset) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(attribute, dataSize, GL11.GL_FLOAT, false, instancedDataLength * 4, offset * 4);
		GL33.glVertexAttribDivisor(attribute, 1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	//for instancing!!
	public void updateVbo(int vbo, float[] data, FloatBuffer buffer) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_STREAM_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture= TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	public void cleanUp(){
		for(int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures){
			GL11.glDeleteTextures(texture);
		}
	}
	
	//for cleaning models
	public static void cleanUpModel(int vao, int positionsVbo, int indicesVbo){
		GL30.glDeleteVertexArrays(vao);
		
		GL15.glDeleteBuffers(positionsVbo);
		GL15.glDeleteBuffers(indicesVbo);
	}
	
	private int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	/**
	 * Creates an index buffer, binds the index buffer to the currently active
	 * VAO, and then fills it with our indices.
	 * 
	 * The index buffer is different from other data that we might store in the
	 * attributes of the VAO. When we stored the positions we were storing data
	 * about each vertex. The positions were "attributes" of each vertex. Data
	 * like that is stored in an attribute list of the VAO.
	 * 
	 * The index buffer however does not contain data about each vertex. Instead
	 * it tells OpenGL how the vertices should be connected. Each VAO can only
	 * have one index buffer associated with it. This is why we don't store the
	 * index buffer in a certain attribute of the VAO; each VAO has one special
	 * "slot" for an index buffer and simply binding the index buffer binds it
	 * to the currently active VAO. When the VAO is rendered it will use the
	 * index buffer that is bound to it.
	 * 
	 * This is also why we don't unbind the index buffer, as that would unbind
	 * it from the VAO.
	 * 
	 * Note that we tell OpenGL that this is an index buffer by using
	 * "GL_ELEMENT_ARRAY_BUFFER" instead of "GL_ARRAY_BUFFER". This is how
	 * OpenGL knows to bind it as the index buffer for the current VAO.
	 * 
	 * @param indices
	 */
	private void bindIndicesBuffer(int[] indices) {
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize,float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,coordinateSize,GL11.GL_FLOAT,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, byte[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		ByteBuffer buffer = storeDataInByteBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,coordinateSize,GL11.GL_BYTE,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, int[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,coordinateSize,GL11.GL_INT,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	//the return types
	private int intBindIndicesBuffer(int[] indices) {
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		return vboId;
	}
	
	private int intStoreDataInAttributeList(int attributeNumber, int coordinateSize,float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,coordinateSize,GL11.GL_FLOAT,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		return vboID;
	}
	
	private int intStoreDataInAttributeList(int attributeNumber, int coordinateSize, byte[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		ByteBuffer buffer = storeDataInByteBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,coordinateSize,GL11.GL_BYTE,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		return vboID;
	}
	
	private int intStoreDataInAttributeList(int attributeNumber, int coordinateSize, int[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL30.glVertexAttribIPointer(attributeNumber,coordinateSize,GL11.GL_INT,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		return vboID;
	}
	
	/**
	 * Converts the indices from an int array to an IntBuffer so that they can
	 * be stored in a VBO. Very similar to the storeDataInFloatBuffer() method
	 * below.
	 * 
	 * @param data
	 *            - The indices in an int[].
	 * @return The indices in a buffer.
	 */
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private ByteBuffer storeDataInByteBuffer(byte[] data) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
}
