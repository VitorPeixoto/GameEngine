package engine;

import engine.loaders.Model;
import engine.renderer.Material;
import org.lwjgl.util.vector.Vector3f;

public class Entity extends Model {
    protected Vector3f position, rotation;
    protected float scale;

    public Entity(Model model, Vector3f position) {
        super(model.getVaoID(), model.getVertexCount(), model.getMaterial());
        this.position = position;
        rotation = new Vector3f(0,0,0);
        scale = 1.0f;
    }

    public void translate(float dx, float dy, float dz) {
        position.translate(dx, dy, dz);
    }

    public void rotate(float dx, float dy, float dz) {
        rotation.translate(dx, dy, dz);
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
