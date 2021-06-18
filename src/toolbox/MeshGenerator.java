package toolbox;

import java.util.ArrayList;
import java.util.List;

import managers.TileManager;
import mapData.Chunk;
import org.lwjgl.util.vector.Vector3f;

import core.MapCore;
import managers.AssetManager;
import models.ChunkModel;
import models.ChunkRawModel;
import models.RawModel;
import models.VoxelModel;

public class MeshGenerator {

	private List<Integer> dataList = new ArrayList<Integer>();
	private List<Integer> indicesList = new ArrayList<Integer>();

    public static final int CHUNK_WIDTH = MapCore.CHUNK_WIDTH;
    public static final int CHUNK_VIEW_HEIGHT = MapCore.CHUNK_VIEW_HEIGHT;

	void buildChunk(short[] data, Vector3f pos) {

        boolean n = false, s = false, e = false, w = false, u = false, d = false;

        short[] north, south, east, west, up, down;

        Chunk cnorth, csouth, ceast, cwest, cup, cdown;


        cnorth = MapCore.getMapData((int) pos.x / CHUNK_WIDTH, (int) pos.y / CHUNK_WIDTH, ((int) pos.z / CHUNK_WIDTH) + 1);
        csouth = MapCore.getMapData((int) pos.x / CHUNK_WIDTH, (int) pos.y / CHUNK_WIDTH, ((int) pos.z / CHUNK_WIDTH) - 1);

        ceast = MapCore.getMapData(((int) pos.x / CHUNK_WIDTH) + 1, (int) pos.y / CHUNK_WIDTH, (int) pos.z / CHUNK_WIDTH);
        cwest = MapCore.getMapData(((int) pos.x / CHUNK_WIDTH) - 1, (int) pos.y / CHUNK_WIDTH, (int) pos.z / CHUNK_WIDTH);

        cup = MapCore.getMapData((int) pos.x / CHUNK_WIDTH, ((int) pos.y / CHUNK_WIDTH) + 1, (int) pos.z / CHUNK_WIDTH);
        cdown = MapCore.getMapData((int) pos.x / CHUNK_WIDTH, ((int) pos.y / CHUNK_WIDTH) - 1, (int) pos.z / CHUNK_WIDTH);

        if (cnorth != null) {
            north = cnorth.getTiles();
            n = cnorth.getCanRender();
        } else {
            north = null;
        }

        if (csouth != null) {
            south = csouth.getTiles();
            s = csouth.getCanRender();
        } else {
            south = null;
        }

        if (ceast != null) {
            east = ceast.getTiles();
            e = ceast.getCanRender();
        } else {
            east = null;
        }

        if (cwest != null) {
            west = cwest.getTiles();
            w = cwest.getCanRender();
        } else {
            west = null;
        }

        if (cup != null) {
            up = cup.getTiles();
            u = cup.getCanRender();
        } else {
            up = null;
        }

        if (cdown != null) {
            down = cdown.getTiles();
            d = cdown.getCanRender();
        } else {
            down = null;
        }

        if(n
        || s
        || e
        || w
        || u
        || d) {

            for (int i = 0; i < data.length; i++) {

                short tileI = data[i];

                if (tileI != 0) {

                    int y = i / (CHUNK_WIDTH * CHUNK_WIDTH);
                    int z = (i - (y * (CHUNK_WIDTH * CHUNK_WIDTH))) / CHUNK_WIDTH;
                    int x = (i - (y * (CHUNK_WIDTH * CHUNK_WIDTH))) - (z * CHUNK_WIDTH);
                    //System.out.println("Tile in [" + x + "," + y + "," + z + "]");

                    int globalY = (int) (y + pos.y);

                    //X axis
                    if (x + 1 < CHUNK_WIDTH) {
                        if (data[(x + 1) + (y * (CHUNK_WIDTH * CHUNK_WIDTH)) + (z * CHUNK_WIDTH)] == 0) {

                            generateQuadFace(x, y, z, data[i], 4);

                        }
                    }
                    else if (east != null){

                        if (east[((x - CHUNK_WIDTH) + 1) + (y * (CHUNK_WIDTH * CHUNK_WIDTH)) + (z * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 4);
                        }

                    }

                    if (x - 1 >= 0) {

                        if (data[(x - 1) + (y * (CHUNK_WIDTH * CHUNK_WIDTH)) + (z * CHUNK_WIDTH)] == 0) {

                            generateQuadFace(x, y, z, data[i], 5);

                        }

                    }
                    else if (west != null){

                        if (west[((x + CHUNK_WIDTH) - 1) + (y * (CHUNK_WIDTH * CHUNK_WIDTH)) + (z * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 5);
                        }

                    }


                    //Y axis
                    if (y + 1 < CHUNK_WIDTH) {

                        if (data[x + ((y + 1) * (CHUNK_WIDTH * CHUNK_WIDTH)) + (z * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 0);
                        }

                    }
                    else if (globalY + 1 < CHUNK_VIEW_HEIGHT * CHUNK_WIDTH && up != null) {

                        if (up[x  + (0) + (z * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 0);
                        }

                    }

                    if (y - 1 >= 0) {

                        if (data[x + ((y - 1) * (CHUNK_WIDTH * CHUNK_WIDTH)) + (z * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 1);
                        }

                    }
                    else if (globalY - 1 > 0 && down != null) {

                        if (down[x  + ((y + (CHUNK_WIDTH * CHUNK_WIDTH)) - 1) + (z * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 1);
                        }

                    }


                    //Z axis
                    if (z + 1 < CHUNK_WIDTH) {

                        if (data[x + (y * (CHUNK_WIDTH * CHUNK_WIDTH)) + ((z + 1) * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 2);
                        }

                    }
                    else if (north != null){

                        if (north[x + (y * (CHUNK_WIDTH * CHUNK_WIDTH)) + (((z - CHUNK_WIDTH) + 1) * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 2);
                        }

                    }

                    if (z - 1 >= 0) {

                        if (data[x + (y * (CHUNK_WIDTH * CHUNK_WIDTH)) + ((z - 1) * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 3);
                        }

                    }
                    else if (south != null){

                        if (south[x + (y * (CHUNK_WIDTH * CHUNK_WIDTH)) + (((z + CHUNK_WIDTH) - 1) * CHUNK_WIDTH)] == 0) {
                            generateQuadFace(x, y, z, data[i], 3);
                        }

                    }

                }

            }


        }

		// packed as:
		//
		// 2bytes for xz pos
		// 2bytes for y pos
		// as 1 int (XZY)
		//
		// 2bytes for uv coords
		// 1byte for frame index
		// 1byte for texture sheet index
		// as 1 int (UVFT)
		//
		// 4bytes for color data
		// as 1 int (RGBA)
		//
		// for a total of 12bytes of data
		// as a ivec3( XZY, UVFT, RGBA )
		//
		// 4bytes for indices
		// as 1 int
		//
		// for a total if 16bytes sent per vertice
		// as 1 ivec3 and 1 int
	}

	public ChunkRawModel generateChunkModel(Vector3f pos) {

		short[] data = MapCore.getTileData((int)pos.x / MapCore.CHUNK_WIDTH ,  (int)pos.y / MapCore.CHUNK_WIDTH,(int)pos.z / MapCore.CHUNK_WIDTH);

		buildChunk(data, pos);

    	if(dataList.size() >= 1) {
    	
	    	int[] datas = new int[dataList.size()];
	    	
	    	int[] indices = new int[indicesList.size()];
	    	
	    	for(int i = 0; i < dataList.size(); i++) {
	        	
	    		datas[i] = dataList.get(i);
	    		
	        }
	    	
	    	for(int i = 0; i < indicesList.size(); i++) {
	        	
	    		indices[i] = indicesList.get(i);
	        }
	    	
	    	cleanUP();
	    	
	    	//System.out.println("created the Chunk model  (" + pos + ")");
	    	
			return new ChunkRawModel(datas, indices);
    	}
    	
    	cleanUP();
    	
    	return null;
    	
    }
	
	//PROVIDED BY @Hazurl, modified by @Nodeki
	private static int packPosition(int x, int y, int z) {
        
        return
        (x & 0xff) << 0    |
        (z & 0xff) << 8    |
        (y & 0xffff) << 16 ;
        
    }
	
	//PROVIDED BY @Hazurl, modified by @Nodeki
	private static int packTexture(int u, int v, int f, int t) {
        
        return
        (u & 0xff) << 0  |
        (v & 0xff) << 8  |
        (f & 0xff) << 16 |
        (t & 0xff) << 24 ;
        
    }
	
	//PROVIDED BY @Hazurl
	private static int packColor(Vector3f base, int a) {
        
        return
        ((int)base.x & 0xff) << 0  |
        ((int)base.y & 0xff) << 8  |
        ((int)base.z & 0xff) << 16 |
        (a & 0xff) << 24 ;
        
    }

    private static int frame(int type, int side) {

        return TileManager.TILE_LIST.get(type).getFrame()[side];

    }

    private static int texture(int type) {

        return TileManager.TILE_LIST.get(type).getTextuereID();

    }

    private static Vector3f ilumination(int type) {

        return TileManager.TILE_LIST.get(type).getLuminocity();

    }

    void generateQuadFace(int x, int y, int z, int type, int side){

	    switch (side){

            case 0:

                for (int v = 0; v < 4; v++) {

                    dataList.add(packPosition(
                            x + (int) VoxelModel.PY_POS[v].x,
                            y + (int) VoxelModel.PY_POS[v].y,
                            z + (int) VoxelModel.PY_POS[v].z));

                    dataList.add(packTexture(
                            (int) VoxelModel.UV[v].x,
                            (int) VoxelModel.UV[v].y,
                            frame(type, side),
                            texture(type)));

                    dataList.add(packColor(
                            ilumination(type),
                            255));

                }

                break;

            case 1:

                for (int v = 0; v < 4; v++) {

                    dataList.add(packPosition(
                            x + (int) VoxelModel.NY_POS[v].x,
                            y + (int) VoxelModel.NY_POS[v].y,
                            z + (int) VoxelModel.NY_POS[v].z));

                    dataList.add(packTexture(
                            (int) VoxelModel.UV[v].x,
                            (int) VoxelModel.UV[v].y,
                            frame(type, side),
                            texture(type)));

                    dataList.add(packColor(
                            ilumination(type),
                            255));

                }

                break;

            case 2:

                for (int v = 0; v < 4; v++) {

                    dataList.add(packPosition(
                            x + (int) VoxelModel.PZ_POS[v].x,
                            y + (int) VoxelModel.PZ_POS[v].y,
                            z + (int) VoxelModel.PZ_POS[v].z));

                    dataList.add(packTexture(
                            (int) VoxelModel.UV[v].x,
                            (int) VoxelModel.UV[v].y,
                            frame(type, side),
                            texture(type)));

                    dataList.add(packColor(
                            ilumination(type),
                            255));

                }

                break;

            case 3:

                for (int v = 0; v < 4; v++) {

                    dataList.add(packPosition(
                            x + (int) VoxelModel.NZ_POS[v].x,
                            y + (int) VoxelModel.NZ_POS[v].y,
                            z + (int) VoxelModel.NZ_POS[v].z));

                    dataList.add(packTexture(
                            (int) VoxelModel.UV[v].x,
                            (int) VoxelModel.UV[v].y,
                            frame(type, side),
                            texture(type)));

                    dataList.add(packColor(
                            ilumination(type),
                            255));

                }

                break;

            case 4:

                for (int v = 0; v < 4; v++) {

                    dataList.add(packPosition(
                            x + (int) VoxelModel.PX_POS[v].x,
                            y + (int) VoxelModel.PX_POS[v].y,
                            z + (int) VoxelModel.PX_POS[v].z));

                    dataList.add(packTexture(
                            (int) VoxelModel.UV[v].x,
                            (int) VoxelModel.UV[v].y,
                            frame(type, side),
                            texture(type)));

                    dataList.add(packColor(
                            ilumination(type),
                            255));

                }

                break;

            case 5:

                for (int v = 0; v < 4; v++) {

                    dataList.add(packPosition(
                            x + (int) VoxelModel.NX_POS[v].x,
                            y + (int) VoxelModel.NX_POS[v].y,
                            z + (int) VoxelModel.NX_POS[v].z));

                    dataList.add(packTexture(
                            (int) VoxelModel.UV[v].x,
                            (int) VoxelModel.UV[v].y,
                            frame(type, 5),
                            texture(type)));

                    dataList.add(packColor(
                            ilumination(type),
                            255));

                }

                break;

        }

        generateIndices();

    }

    void generateIndices(){

        int offset = indicesList.size() / 3;

        final int[] indexes = new int[]{
                0 + (offset * 2), 1 + (offset * 2), 2 + (offset * 2),

                2 + (offset * 2), 1 + (offset * 2), 3 + (offset * 2)};

        for (int index : indexes) {

            indicesList.add(index);

        }

    }

    void cleanUP(){
    	
    	dataList.clear();
    	indicesList.clear();
    	
    }
	
}