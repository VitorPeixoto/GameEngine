package engine.renderer.renderers;

import engine.Constants;
import engine.entity.Entity;
import engine.loaders.Model;
import engine.renderer.Camera;
import engine.renderer.models.ModelType;
import engine.renderer.shaders.StaticShader;
import engine.util.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 100000.0f;
    protected static Matrix4f projectionMatrix;
    protected Matrix4f viewMatrix;

    public Renderer() {
        GL11.glEnable(GL_CULL_FACE);
        GL11.glCullFace(GL_BACK);
        createProjectionMatrix();
    }

    public void prepare(Camera camera) {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClearColor(0, 0.85f, 0.86f, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        this.viewMatrix = Maths.createViewMatrix(camera);
    }

    public void render(Entity entity, StaticShader shader) {
        Model model = entity.getType().getModel();

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(Constants.VERTEX_BUFFER_INDEX);
        GL20.glEnableVertexAttribArray(Constants.UV_BUFFER_INDEX);
        GL20.glEnableVertexAttribArray(Constants.NORMAL_BUFFER_INDEX);

        Matrix4f transformation = entity.getTransformationMatrix();
        shader.loadMatrices(transformation, viewMatrix, projectionMatrix);

        shader.loadMaterial(model.getMaterial());

        if(model.getMaterial().getTexture() != null) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMaterial().getTexture().getId());
        }

        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(Constants.UV_BUFFER_INDEX);
        GL20.glDisableVertexAttribArray(Constants.VERTEX_BUFFER_INDEX);
        GL20.glDisableVertexAttribArray(Constants.NORMAL_BUFFER_INDEX);
        GL30.glBindVertexArray(0);
    }

    private static void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}
