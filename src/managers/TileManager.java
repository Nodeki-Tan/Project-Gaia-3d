package managers;

import mapData.Tile;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;

import java.util.ArrayList;
import java.util.List;

public class TileManager {

    public static final List<Tile> TILE_LIST = new ArrayList<Tile>();

    public static  void loadTiles(){

        TILE_LIST.add(0, new Tile(
                true,
                false,
                0,
                0,
                0,
                Maths.Vector3Zero,
                new short[]{0, 0, 0, 0, 0, 0},
                0,
                -1
        ));

        TILE_LIST.add(1, new Tile(
                false,
                false,
                0,
                1,
                10,
                Maths.Vector3Zero,
                new short[]{0, 2, 1, 1, 1, 1},
                0,
                -1
        ));

        TILE_LIST.add(2, new Tile(
                false,
                false,
                1,
                5,
                15,
                Maths.Vector3Zero,
                new short[]{2, 2, 2, 2, 2, 2},
                0,
                -1
        ));

        TILE_LIST.add(3, new Tile(
                false,
                false,
                2,
                10,
                20,
                Maths.Vector3Zero,
                new short[]{3, 3, 3, 3, 3, 3},
                0,
                -1
        ));

        TILE_LIST.add(4, new Tile(
                false,
                false,
                3,
                5,
                5,
                Maths.Vector3Zero,
                new short[]{12, 12, 12, 12, 12, 12},
                0,
                -1
        ));

        TILE_LIST.add(5, new Tile(
                false,
                false,
                4,
                5,
                5,
                Maths.Vector3Zero,
                new short[]{10, 12, 11, 11, 11, 11},
                0,
                -1
        ));

        TILE_LIST.add(6, new Tile(
                true,
                false,
                5,
                5,
                5,
                Maths.Vector3Zero,
                new short[]{13, 13, 13, 13, 13, 13},
                0,
                -1
        ));

        TILE_LIST.add(7, new Tile(
                false,
                false,
                5,
                5,
                5,
                Maths.Vector3Zero,
                new short[]{4, 4, 4, 4, 4, 4},
                0,
                -1
        ));

        TILE_LIST.add(8, new Tile(
                false,
                false,
                5,
                5,
                5,
                Maths.Vector3Zero,
                new short[]{5, 5, 5, 5, 5, 5},
                0,
                -1
        ));

        TILE_LIST.add(9, new Tile(
                false,
                false,
                5,
                5,
                5,
                Maths.Vector3Zero,
                new short[]{6, 6, 6, 6, 6, 6},
                0,
                -1
        ));

        TILE_LIST.add(10, new Tile(
                false,
                false,
                5,
                5,
                5,
                Maths.Vector3Zero,
                new short[]{7, 7, 7, 7, 7, 7},
                0,
                -1
        ));

        TILE_LIST.add(11, new Tile(
                false,
                false,
                5,
                5,
                5,
                Maths.Vector3Zero,
                new short[]{14, 16, 15, 15, 15, 15},
                0,
                -1
        ));

        TILE_LIST.add(12, new Tile(
                false,
                false,
                5,
                5,
                5,
                Maths.Vector3Zero,
                new short[]{16, 16, 16, 16, 16, 16},
                0,
                -1
        ));

    }

}
