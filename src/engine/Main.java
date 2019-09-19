package engine;

import engine.loaders.ModelLoader;
import engine.renderer.Camera;
import engine.renderer.DisplayManager;
import engine.renderer.Renderer;
import engine.renderer.shaders.StaticShader;
import org.lwjgl.LWJGLException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DisplayManager.create();

            StaticShader shader = new StaticShader();
            Renderer renderer = new Renderer(shader);
            Camera camera = new Camera();

            Entity entity = new Entity(ModelLoader.loadModel("Earth3", "texture"));

            while(!DisplayManager.isClosing()) {
                camera.move();

                renderer.prepare();

                shader.start();
                shader.loadViewMatrix(camera);

                renderer.render(entity, shader);

                shader.stop();

                DisplayManager.update();
            }

            shader.detachAll();
            ModelLoader.deleteAll();
            DisplayManager.close();

        } catch (IOException | LWJGLException e) {
            e.printStackTrace();
        }
    }
}
