package engine;

import engine.loaders.ModelLoader;
import engine.renderer.DisplayManager;
import engine.renderer.renderers.Renderer;
import engine.renderer.Scene;
import engine.renderer.shaders.StaticShader;
import org.lwjgl.LWJGLException;

public class Main {
    public static void main(String[] args) {
        try {
            DisplayManager.create();

            StaticShader shader = new StaticShader();
            Renderer renderer = new Renderer();

            Scene scene = new Scene(shader, renderer);
            scene.initialize();

            while(!DisplayManager.isClosing()) {
                scene.update();
                scene.render();

                DisplayManager.update();
            }

            shader.detachAll();
            ModelLoader.deleteAll();
            DisplayManager.close();

        } catch (LWJGLException e) {
            e.printStackTrace();
        }

    }
}
