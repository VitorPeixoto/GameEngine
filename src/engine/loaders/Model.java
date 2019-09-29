package engine.loaders;

import engine.renderer.Material;

public class Model {
    private int vaoID;
    private int vertexCount;
    private Material material;

    public Model(int vaoID, int vertexCount, Material texture) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.material = texture;
    }

    public int getVaoID() {
        return vaoID;
    }

    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
