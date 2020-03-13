package engine.entity;

import engine.renderer.models.ModelType;
import engine.util.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Entity {
    protected Vector3f position, rotation;
    protected float scale;
    protected ModelType type;

    protected Matrix4f transformationMatrix;
    protected boolean dirty = false;

    public Entity(ModelType type, Vector3f position) {
        this.type = type;
        this.position = position;
        rotation = new Vector3f(0,0,0);
        scale = 1.0f;
        transformationMatrix = Maths.createTransformMatrix(position, rotation, scale);
    }

    public Entity(ModelType type, Vector3f position, float scale) {
        this.type = type;
        this.position = position;
        rotation = new Vector3f(0,0,0);
        this.scale = scale;
        transformationMatrix = Maths.createTransformMatrix(position, rotation, scale);
    }

    public ModelType getType() {
        return type;
    }

    public void setType(ModelType type) {
        this.type = type;
    }

    public void translate(float dx, float dy, float dz) {
        position.translate(dx, dy, dz);
        dirty = true;
    }

    public void rotate(float dx, float dy, float dz) {
        rotation.translate(dx, dy, dz);
        dirty = true;
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

    public Matrix4f getTransformationMatrix() {
        if (dirty) {
            transformationMatrix = Maths.createTransformMatrix(position, rotation, scale);
            dirty = false;
        }
        return transformationMatrix;
    }
}
