package engine;

import engine.loaders.ModelLoader;
import engine.renderer.Camera;
import engine.renderer.DisplayManager;
import engine.renderer.Renderer;
import engine.renderer.lighting.Light;
import engine.renderer.shaders.StaticShader;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DisplayManager.create();

            StaticShader shader = new StaticShader();
            Renderer renderer = new Renderer();
            Camera camera = new Camera(new Vector3f(0,0,10));

            Entity entity = new Entity(ModelLoader.loadModel("sphere"), new Vector3f(0,0,0));

            Light light = new Light(new Vector3f(200, 200, 100), new Vector3f(1,1,1));
            //Light light = new Light(new Vector3f(0, 0, -10), new Vector3f(1,1,1));

            while(!DisplayManager.isClosing()) {
                camera.move();
                entity.rotate(0, .1f, 0);

                renderer.prepare(camera);

                shader.start();
                shader.loadLight(light);

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
