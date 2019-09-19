package engine.renderer.shaders;

import engine.Constants;
import engine.ResourceManager;
import engine.renderer.Camera;
import engine.util.Maths;
import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends AbstractShaderProgram {
    private static final String parent = "/shaders/";

    private int location_transformation_matrix,
                location_projection_matrix,
                location_view_matrix;

    public StaticShader() {
        super(ResourceManager.get(parent, "vertex_shader.glsl"), ResourceManager.get(parent, "fragment_shader.glsl"));
    }

    public StaticShader(String vertexShaderPath, String fragmentShaderPath) {
        super(vertexShaderPath, fragmentShaderPath);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformation_matrix = super.getUniformLocation("transformation_matrix");
        location_projection_matrix     = super.getUniformLocation("projection_matrix");
        location_view_matrix           = super.getUniformLocation("view_matrix");
    }

    @Override
    protected void bindAttributes() {
        // Attribute 0 from the VAO
        super.bindAttribute(Constants.VERTEX_BUFFER_INDEX, "position");

        // Attribute 1 from the VAO
        super.bindAttribute(Constants.UV_BUFFER_INDEX, "in_uv_coordinates");
    }

    public void loadTransformationMatrix(Matrix4f matrix4f) {
        super.loadMatrix(location_transformation_matrix, matrix4f);
    }

    public void loadProjectionMatrix(Matrix4f matrix4f) {
        super.loadMatrix(location_projection_matrix, matrix4f);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_view_matrix, Maths.createViewMatrix(camera));
    }

}
