package renderEngine;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.MapCore;
import core.RenderCore;
import entities.Camera;
import entities.ChunkRenderer;
import entities.Entity;
import managers.AssetManager;
import models.GuiImage;
import shaders.ShaderProgram;
import textures.ModelTexture;
import toolbox.Maths;

public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	public static final int TILE_SIZE = 32;
	
	public static Matrix4f projectionMatrix;
	
	private int entitiesVbo;
	private static final int MAX_INSTANCES = 100000;
	private static final int INSTANCE_DATA_LENGTH = 18;
	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(INSTANCE_DATA_LENGTH * MAX_INSTANCES);
	private int pointer = 0;
	
	int[] tex = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	
	public Renderer(){
		createProjectionMatrix();
		
		entitiesVbo = AssetManager.loader.createEmptyVBO(INSTANCE_DATA_LENGTH * MAX_INSTANCES);
		
		AssetManager.loader.addIntancedAttributte(
				AssetManager.spriteModel.getVaoID(), 
				entitiesVbo, 1, 1, INSTANCE_DATA_LENGTH, 0);
		
		AssetManager.loader.addIntancedAttributte(
				AssetManager.spriteModel.getVaoID(), 
				entitiesVbo, 2, 1, INSTANCE_DATA_LENGTH, 1);
		
		AssetManager.loader.addIntancedAttributte(
				AssetManager.spriteModel.getVaoID(), 
				entitiesVbo, 3, 4, INSTANCE_DATA_LENGTH, 2);
		AssetManager.loader.addIntancedAttributte(
				AssetManager.spriteModel.getVaoID(), 
				entitiesVbo, 4, 4, INSTANCE_DATA_LENGTH, 6);
		AssetManager.loader.addIntancedAttributte(
				AssetManager.spriteModel.getVaoID(), 
				entitiesVbo, 5, 4, INSTANCE_DATA_LENGTH, 10);
		AssetManager.loader.addIntancedAttributte(
				AssetManager.spriteModel.getVaoID(), 
				entitiesVbo, 6, 4, INSTANCE_DATA_LENGTH, 14);
		
		//Setup texture atlases once
		for(int i = 0; i < AssetManager.textureAtlas.length; i++) {

			GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, AssetManager.textureAtlas[i].getID());

			//Setup filtering, i.e. how OpenGL will interpolate the pixels when scaling up or down
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		}

		//Setup Backface culling
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
	}
	
	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.125f, 0.839f, 0.78f, 1f);
		GL11.glEnable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
	}
	
	public void renderGUIs(List<GuiImage> list) {
		AssetManager.guiShader.start();
			
		for(GuiImage gui: list) {
			
			//Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			//AssetManager.guiShader.loadProjectionViewMatrix(matrix);
			GL11.glDrawArrays(GL11.GL_POINTS, 0, AssetManager.spriteModel.getVertexCount());
		}
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		AssetManager.guiShader.stop();
	}

	//Particle or maybe "entities" rendering
	public void renderEntities(List<Entity> list) {
		AssetManager.entityShader.start();

		GL30.glBindVertexArray(AssetManager.spriteModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		GL20.glEnableVertexAttribArray(4);
		GL20.glEnableVertexAttribArray(5);
		GL20.glEnableVertexAttribArray(6);
		
		pointer = 0;
		float[] vboData = new float[list.size() * INSTANCE_DATA_LENGTH];
		
		for(Entity actor: list) {
			if(pointer + 1 <= vboData.length) {
				
				for(int i = 0; i < actor.getScale().x; i++) {
					for(int j = 0; j < actor.getScale().y; j++) {
					
						vboData[pointer++] = actor.getTiles()[(int) (i + (j * actor.getScale().y))].getCurrentFrame();
						
						vboData[pointer++] = actor.getTiles()[(int) (i + (j * actor.getScale().y))].getTexture();
						
						loadEntityProjectionViewMatrix(new Vector3f(actor.getPosition().x + i, actor.getPosition().y + j, actor.getPosition().z), actor.getRotation(), vboData);
						
						
					}
				}
				
			}
		}
		AssetManager.loader.updateVbo(entitiesVbo, vboData, buffer);
		
		AssetManager.entityShader.loadProjectionViewMatrix(Matrix4f.mul(projectionMatrix, Maths.createViewMatrix(RenderCore.camera), null));

		AssetManager.entityShader.loadTextures(tex);
		
		GL31.glDrawArraysInstanced(GL11.GL_POINTS, 0, AssetManager.spriteModel.getVertexCount(), list.size());
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(4);
		GL20.glDisableVertexAttribArray(5);
		GL20.glDisableVertexAttribArray(6);
		
		GL30.glBindVertexArray(0);
			
		AssetManager.entityShader.stop();
	}
	
	//Map rendering
	public void renderMap(List<ChunkRenderer> list) {
		AssetManager.chunkShader.start();

		AssetManager.chunkShader.loadTextures(tex);

		for(ChunkRenderer chunk: list) {
			
			if(chunk.getChunkModel() != null) {
				GL30.glBindVertexArray(chunk.getChunkModel().getVaoID());
				
				GL20.glEnableVertexAttribArray(0);
				
				loadProjectionViewMatrix(chunk.getPosition(), Maths.Vector3Zero, AssetManager.chunkShader);

				GL11.glDrawElements(GL11.GL_TRIANGLES, chunk.getChunkModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

				GL20.glDisableVertexAttribArray(0);
				
				GL30.glBindVertexArray(0);
			}
			
		}

		
		AssetManager.chunkShader.stop();
	}
	
	/*
	 * old 2d rendering projection matrix
    private void createProjectionMatrix(){
    	
    	
    	projectionMatrix = Maths.orthoMatrix(
    			(-Display.getWidth()/2)/TILE_SIZE, (Display.getWidth()/2)/TILE_SIZE, 
    			(-Display.getHeight()/2)/TILE_SIZE, (Display.getHeight()/2)/TILE_SIZE, 
    			-1, 1);
       
    }
    */
	
    private void createProjectionMatrix(){
    	projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
    }

    public static Matrix4f getProjectionMatrix(){
    	 return projectionMatrix;
	}

    //for instanced rendering
    private void loadEntityProjectionViewMatrix(Vector3f pos, Vector3f rot, float[] vboData){
    	Matrix4f transformationMatrix = Maths.createTransformationMatrix
    			(pos, rot.x, rot.y, rot.z, 1);
    	
    	storeMatrixData(transformationMatrix, vboData);
    }
    
    //for common model rendering, like chunks
    private void loadProjectionViewMatrix(Vector3f pos, Vector3f rot, ShaderProgram shader){
    	Matrix4f transformationMatrix = Maths.createTransformationMatrix
    			(pos, rot.x, rot.y, rot.z, 1);
    	Matrix4f viewMat = Matrix4f.mul(Maths.createViewMatrix(RenderCore.camera), transformationMatrix, null);
    	Matrix4f projView = Matrix4f.mul(projectionMatrix, viewMat, null);
    	shader.loadProjectionViewMatrix(projView);
    }
    
    //more instanced rendering stuff
	private void storeMatrixData(Matrix4f matrix,  float[] vboData){
		vboData[pointer++] = matrix.m00;
		vboData[pointer++] = matrix.m01;
		vboData[pointer++] = matrix.m02;
		vboData[pointer++] = matrix.m03;
		vboData[pointer++] = matrix.m10;
		vboData[pointer++] = matrix.m11;
		vboData[pointer++] = matrix.m12;
		vboData[pointer++] = matrix.m13;
		vboData[pointer++] = matrix.m20;
		vboData[pointer++] = matrix.m21;
		vboData[pointer++] = matrix.m22;
		vboData[pointer++] = matrix.m23;
		vboData[pointer++] = matrix.m30;
		vboData[pointer++] = matrix.m31;
		vboData[pointer++] = matrix.m32;
		vboData[pointer++] = matrix.m33;
	}
	
}
