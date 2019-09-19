package engine;

import engine.loaders.Model;
import engine.renderer.Texture;
import org.lwjgl.util.vector.Vector3f;

public class Entity extends Model {
    protected Vector3f position, rotation;
    protected float scale;

    public Entity(int vaoID, int vertexCount, Texture texture) {
        super(vaoID, vertexCount, texture);
        position = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);
        scale = 1.0f;
    }

    public Entity(Model model) {
        super(model.getVaoID(), model.getVertexCount(), model.getTexture());
        position = new Vector3f(0,0,-1);
        rotation = new Vector3f(0,0,0);
        scale = 1.0f;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
