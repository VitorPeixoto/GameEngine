package engine.renderer.renderers;

import engine.Constants;
import engine.entity.Entity;
import engine.loaders.Model;
import engine.renderer.models.ModelType;
import engine.renderer.shaders.StaticShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityRenderer extends Renderer {
    private HashMap<ModelType, ArrayList<Entity>> entities;

    public EntityRenderer() {
        entities = new HashMap<>();
    }

    public void addEntity(Entity entity) {
        ModelType type = entity.getType();
        if(!entities.containsKey(type)) {
            entities.put(type, new ArrayList<>());
        }
        entities.get(type).add(entity);
    }

    public void render(StaticShader shader) {
        for(ModelType type : entities.keySet()) {
            this.render(type, entities.get(type), shader);
        }
    }

    public HashMap<ModelType, ArrayList<Entity>> getEntities() {
        return entities;
    }

    private void render(ModelType type, List<Entity> entities, StaticShader shader) {
        Model model = type.getModel();

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(Constants.VERTEX_BUFFER_INDEX);
        GL20.glEnableVertexAttribArray(Constants.UV_BUFFER_INDEX);
        GL20.glEnableVertexAttribArray(Constants.NORMAL_BUFFER_INDEX);

        shader.loadMaterial(model.getMaterial());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMaterial().getTexture().getId());

        for(Entity entity : entities) {
            Matrix4f transformation = entity.getTransformationMatrix();
            shader.loadMatrices(transformation, viewMatrix, projectionMatrix);
            GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        }

        GL20.glDisableVertexAttribArray(Constants.UV_BUFFER_INDEX);
        GL20.glDisableVertexAttribArray(Constants.VERTEX_BUFFER_INDEX);
        GL20.glDisableVertexAttribArray(Constants.NORMAL_BUFFER_INDEX);
        GL30.glBindVertexArray(0);
    }
}
