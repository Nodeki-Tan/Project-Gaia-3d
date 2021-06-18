package mapData;

import org.lwjgl.util.vector.Vector3f;

public class Tile {

    boolean transparent;
    boolean special;

    int itemDropID;
    int expDrop;

    float resistance;

    Vector3f luminocity;

    short[] frame;
    int textuereID;
    int modelID;

    public Tile(
            boolean transparent,
            boolean special,
            int itemDropID,
            int expDrop,
            float resistance,
            Vector3f luminocity,
            short[] frame,
            int textuereID,
            int modelID) {
        this.transparent = transparent;
        this.special = special;
        this.itemDropID = itemDropID;
        this.expDrop = expDrop;
        this.resistance = resistance;
        this.luminocity = luminocity;
        this.frame = frame;
        this.textuereID = textuereID;
        this.modelID = modelID;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public boolean isSpecial() {
        return special;
    }

    public int getItemDropID() {
        return itemDropID;
    }

    public int getExpDrop() {
        return expDrop;
    }

    public float getResistance() {
        return resistance;
    }

    public Vector3f getLuminocity() {
        return luminocity;
    }

    public short[] getFrame() {
        return frame;
    }

    public int getTextuereID() {
        return textuereID;
    }

    public int getModelID() {
        return modelID;
    }

}
