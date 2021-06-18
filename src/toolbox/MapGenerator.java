package toolbox;

import core.MapCore;
import mapData.Chunk;

public class MapGenerator {

    public static final int CHUNK_WIDTH = MapCore.CHUNK_WIDTH;
    public static final int CHUNK_VIEW_HEIGHT = MapCore.CHUNK_VIEW_HEIGHT;

    static int waterLevel = 256 + 64;

    public static Chunk generateData(int x, int y, int z, float[] values, float[] biomes){

        short[] data = new short[CHUNK_WIDTH*CHUNK_WIDTH*CHUNK_WIDTH];

        int airBlocks = 0;

        for(int i = 0; i < CHUNK_WIDTH; i++) {
            for(int o = 0; o < CHUNK_WIDTH; o++) {
                for(int j = 0; j < CHUNK_WIDTH; j++) {

                    int yPos = j + (y * CHUNK_WIDTH);

                    data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 0;

                    if (biomes[i + (o * CHUNK_WIDTH)] < 1f) {

                        if (yPos <= values[i + (o * CHUNK_WIDTH)] && yPos >= values[i + (o * CHUNK_WIDTH)] - 1) {

                            data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 5;

                        } else if (yPos < values[i + (o * CHUNK_WIDTH)] - 1 && yPos >= values[i + (o * CHUNK_WIDTH)] - 4) {

                            data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 4;

                        } else if (yPos < values[i + (o * CHUNK_WIDTH)] - 4) {

                            data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 3;

                        }

                    }
                    else if (biomes[i + (o * CHUNK_WIDTH)] >= 1f && biomes[i + (o * CHUNK_WIDTH)] < 2) {

                        if (yPos <= values[i + (o * CHUNK_WIDTH)] && yPos >= values[i + (o * CHUNK_WIDTH)] - 1) {


                            if (yPos <= waterLevel + 4) {

                                data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 5;

                            }else {
                                data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 1;
                            }

                        } else if (yPos < values[i + (o * CHUNK_WIDTH)] - 1 && yPos >= values[i + (o * CHUNK_WIDTH)] - 4) {

                            if (yPos <= waterLevel + 3) {

                                data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 4;

                            }else {

                                data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 2;

                            }

                        } else if (yPos < values[i + (o * CHUNK_WIDTH)] - 4) {

                            data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 3;

                        }

                    }
                    else if (biomes[i + (o * CHUNK_WIDTH)] >= 2) {

                        if (yPos <= values[i + (o * CHUNK_WIDTH)] && yPos >= values[i + (o * CHUNK_WIDTH)] - 1) {


                            if (yPos <= waterLevel + 4) {

                                data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 5;

                            }else {
                                data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 11;
                            }

                        } else if (yPos < values[i + (o * CHUNK_WIDTH)] - 1 && yPos >= values[i + (o * CHUNK_WIDTH)] - 4) {

                            if (yPos <= waterLevel + 3) {

                                data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 4;

                            }else {

                                data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 12;

                            }

                        } else if (yPos < values[i + (o * CHUNK_WIDTH)] - 4) {

                            data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 3;

                        }

                    }

                    if (data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] == 0) {
                        if (yPos <= waterLevel){
                            data[i + (j * (CHUNK_WIDTH * CHUNK_WIDTH)) + (o * CHUNK_WIDTH)] = 6;
                        }else {
                        airBlocks++;
                        }
                    }

                }
            }
        }

        Chunk chunk = new Chunk(data, x, y, z);
        chunk.setAirBlocks(airBlocks);

        return chunk;

    }

    static boolean checkCave(float x, float y, float z){

        float value = HeightGenerator.Evaluate3dPoint(x,y,z);

        if(value <= 0.3f){
            return true;
        }
        return false;

    }

}
