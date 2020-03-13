package engine.renderer.shaders;

import engine.Constants;
import engine.ResourceManager;
import engine.renderer.Camera;
import engine.renderer.Material;
import engine.renderer.lighting.Light;
import engine.util.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class StaticShader extends AbstractShaderProgram {
    private static final String parent = "/shaders/";

    private static final int numLights = 1;

    private int location_modelview_matrix,
                location_modelviewprojection_matrix,
                location_normal_matrix,

                location_light_color,

                location_ambient_color,
                location_diffuse_color,
                location_specular_color,

                location_material_Ka,
                location_material_Kd,
                location_material_Ks,
                location_material_Sh
    ;

    private int[] location_light_position,
                  location_light_La,
                  location_light_Ld,
                  location_light_Ls
    ;

    public StaticShader() {
        super(ResourceManager.get(parent, "vertex_shader.glsl"), ResourceManager.get(parent, "fragment_shader.glsl"));
    }

    public StaticShader(String vertexShaderPath, String fragmentShaderPath) {
        super(vertexShaderPath, fragmentShaderPath);
    }

    @Override
    protected void getAllUniformLocations() {
        location_light_position = new int[numLights];
        location_light_La = new int[numLights];
        location_light_Ld = new int[numLights];
        location_light_Ls = new int[numLights];

        location_modelview_matrix = super.getUniformLocation("model_view_matrix");
        location_modelviewprojection_matrix = super.getUniformLocation("model_view_projection_matrix");
        location_normal_matrix = super.getUniformLocation("normal_matrix");


        //location_light_position = super.getUniformLocation("light_position");
        location_light_color    = super.getUniformLocation("light_color");

        location_specular_color = super.getUniformLocation("material.specular_color");
        location_ambient_color  = super.getUniformLocation("material.ambient_color");
        location_diffuse_color  = super.getUniformLocation("material.diffuse_color");

        location_specular_color = super.getUniformLocation("material.specular_color");
        location_ambient_color  = super.getUniformLocation("material.ambient_color");
        location_diffuse_color  = super.getUniformLocation("material.diffuse_color");

        location_material_Ka = super.getUniformLocation("material.ambient_coefficient");
        location_material_Kd = super.getUniformLocation("material.diffuse_coefficient");
        location_material_Ks = super.getUniformLocation("material.specular_coefficient");
        location_material_Sh = super.getUniformLocation("material.specular_shininess");

        for(int i = 0; i < numLights; i++) {
            location_light_position[i] = super.getUniformLocation("lights["+i+"].position");
            location_light_La[i] = super.getUniformLocation("lights["+i+"].ambient_light");
            location_light_Ld[i] = super.getUniformLocation("lights["+i+"].diffuse_light");
            location_light_Ls[i] = super.getUniformLocation("lights["+i+"].specular_light");
        }
    }

    @Override
    protected void bindAttributes() {
        // Attribute 0 from the VAO
        super.bindAttribute(Constants.VERTEX_BUFFER_INDEX, "position");

        // Attribute 1 from the VAO
        super.bindAttribute(Constants.UV_BUFFER_INDEX, "uv");

        // Attribute 2 from the VAO
        super.bindAttribute(Constants.NORMAL_BUFFER_INDEX, "normal");
    }

    public void loadMatrices(Matrix4f model, Matrix4f view, Matrix4f projection) {
        Matrix4f modelView           = Matrix4f.mul(view, model, null);
        Matrix4f modelViewProjection = Matrix4f.mul(projection, modelView, null);

        Matrix4f normalMatrix = Matrix4f.transpose(Matrix4f.invert(modelView, null), null);

        super.loadMatrix(location_modelview_matrix, modelView);
        super.loadMatrix(location_modelviewprojection_matrix, modelViewProjection);
        super.loadMatrix(location_normal_matrix, normalMatrix);
    }

    public void loadLight(Light light, int index) {
        super.loadVec3f(location_light_position[index], light.getPosition());
        super.loadVec3f(location_light_La[index], new Vector3f(.5f,.5f,.5f));
        super.loadVec3f(location_light_Ld[index], new Vector3f(.5f,.5f,.5f));
        super.loadVec3f(location_light_Ls[index], new Vector3f(.5f,.5f,.5f));
    }

    public void loadMaterial(Material material) {
        super.loadVec3f(location_material_Ka, material.getAmbientColor());
        super.loadVec3f(location_material_Ks, material.getSpecularColor());
        super.loadVec3f(location_material_Kd, material.getDiffuseColor());
        super.loadFloat(location_material_Sh, material.getSpecularExponent());
    }

}
