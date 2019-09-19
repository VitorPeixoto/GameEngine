package engine.loaders;

import engine.Constants;
import engine.renderer.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader extends WavefrontLoader {
    private static List<Integer> vaos     = new ArrayList<Integer>();
    private static List<Integer> vbos     = new ArrayList<Integer>();
    private static List<Integer> textures = new ArrayList<Integer>();

    public static Model loadModel(String fileName, String textureName) throws IOException {
        int vaoID = createVAO();

        WavefrontObject object = ModelLoader.read(fileName);

        IntBuffer indices = intToIntBuffer(object.getIndiceArray());

        bindIndexesBuffer(indices);
        storeDataIntoAttributeList(Constants.VERTEX_BUFFER_INDEX, 3, floatToFloatBuffer(object.getVerticeArray()));
        storeDataIntoAttributeList(Constants.UV_BUFFER_INDEX, 2, floatToFloatBuffer(object.getUvArray()));
        unbindVAO();

        return new Model(vaoID, indices.capacity(), new Texture(loadTexture(textureName)));
    }

    private static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);

        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private static int loadTexture(String name) {
        int textureID = TextureLoader.load(name);
        textures.add(textureID);
        return textureID;
    }

    private static void storeDataIntoAttributeList(int attributeListId, int coordinateSize, FloatBuffer buffer) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeListId, coordinateSize, GL11.GL_FLOAT, false, 0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private static void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private static void bindIndexesBuffer(IntBuffer indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    }

    private static IntBuffer intToIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static FloatBuffer floatToFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static void deleteAll() {
        vaos.forEach( (vao) -> GL30.glDeleteVertexArrays(vao));
        vbos.forEach( (vbo) -> GL15.glDeleteBuffers(vbo));
        textures.forEach( (tex) -> GL11.glDeleteTextures(tex));
    }
}
