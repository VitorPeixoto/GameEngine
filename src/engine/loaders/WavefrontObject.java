package engine.loaders;

public class WavefrontObject {
    private final int[]   indiceArray;
    private final float[] verticeArray, normalArray, uvArray;

    public WavefrontObject(int[] indiceArray, float[] verticeArray, float[] normalArray, float[] uvArray) {
        this.indiceArray = indiceArray;
        this.verticeArray = verticeArray;
        this.normalArray = normalArray;
        this.uvArray = uvArray;
    }

    public int[] getIndiceArray() {
        return indiceArray;
    }

    public float[] getVerticeArray() {
        return verticeArray;
    }

    public float[] getNormalArray() {
        return normalArray;
    }

    public float[] getUvArray() {
        return uvArray;
    }
}
