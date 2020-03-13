package engine.renderer;

import engine.entity.Entity;
import engine.entity.Player;
import engine.renderer.lighting.Light;
import engine.renderer.models.ModelType;
import engine.renderer.renderers.EntityRenderer;
import engine.renderer.renderers.Renderer;
import engine.renderer.shaders.StaticShader;
import engine.terrain.Terrain;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class Scene {
    private final Camera camera;
    private final Player player;
    private final StaticShader shader;
    private final Renderer renderer;
    private final EntityRenderer entityRenderer;
    private final ArrayList<Light> lights;

    public Scene(StaticShader shader, Renderer renderer) {
        this.shader = shader;
        this.renderer = renderer;
        this.player = new Player(ModelType.PERSON, new Vector3f(0,0,0));
        this.camera = new Camera(player);

        entityRenderer = new EntityRenderer();
        lights = new ArrayList<>();
    }

    public void initialize() {
        float size = 300;
            for(int i = 0; i < 50; i++) {
                Entity tree = new Entity(ModelType.TREE,
                                  new Vector3f(
                                          (float)((size)*Math.random()) - size/2,
                                          0,
                                          (float)((size)*Math.random()) - size/2
                                  ));
                Entity rock = new Entity(ModelType.ROCK,
                                  new Vector3f(
                                          (float)((size)*Math.random()) - size/2,
                                          0,
                                          (float)((size)*Math.random()) - size/2
                                  ));
                Entity duck = new Entity(ModelType.DUCK,
                                  new Vector3f(
                                          (float)((size)*Math.random()) - size/2,
                                          0,
                                          (float)((size)*Math.random()) - size/2
                                  ));
                Entity eagle = new Entity(ModelType.EAGLE,
                                  new Vector3f(
                                          (float)((size)*Math.random()) - size/2,
                                          10 + (float)((10)*Math.random()),
                                          (float)((size)*Math.random()) - size/2
                                  ));

                Entity brachiosaur = new Entity(ModelType.BRACHIOSAURUS,
                                  new Vector3f(
                                          (float)((size)*Math.random()) - size/2,
                                          0,
                                          (float)((size)*Math.random()) - size/2
                                  ), 2);

                tree.rotate(0,(float)Math.random()*180.0f,0);
                rock.rotate(0,(float)Math.random()*180.0f,0);
                duck.rotate(0,(float)Math.random()*180.0f,0);
                brachiosaur.rotate(0,(float)Math.random()*180.0f,0);
                eagle.rotate(15.0f + (float)(Math.random()*30.0f),(float)Math.random()*180.0f,0);

                entityRenderer.addEntity(tree);
                entityRenderer.addEntity(rock);
                entityRenderer.addEntity(duck);
                entityRenderer.addEntity(eagle);
                if(i % 10 == 0) entityRenderer.addEntity(brachiosaur);
            }

            entityRenderer.addEntity(new Terrain(new Vector3f(-200,-1,-200)));

            entityRenderer.addEntity(player);

            lights.add(new Light(new Vector3f(200, 200, 100), new Vector3f(1,1,1)));
    }

    public void update() {
        camera.move();
        player.move();
        entityRenderer.prepare(camera);
        renderer.prepare(camera);
    }

    public void render() {
        shader.start();

        for(int index = 0; index < lights.size(); index++)
            shader.loadLight(lights.get(index), index);

        entityRenderer.render(shader);

        shader.stop();
    }
}
