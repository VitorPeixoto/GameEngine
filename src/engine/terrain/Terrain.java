package engine.terrain;

import engine.entity.Entity;
import engine.renderer.models.ModelType;
import org.lwjgl.util.vector.Vector3f;

public class Terrain extends Entity {

    public Terrain(Vector3f position) {
        super(ModelType.TERRAIN, position, 100);
    }
}
