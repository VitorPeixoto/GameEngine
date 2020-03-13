package engine.renderer.models;

import engine.loaders.Model;
import engine.loaders.ModelLoader;
import engine.terrain.TerrainGenerator;

import java.io.IOException;

public enum ModelType {
    SPHERE("sphere"),
    CUBE("cube"),
    TERRAIN("terrain"),
    SPACESHIP("spaceship"),
    PERSON("person"),
    TREE("tree"),
    ROCK("rock"),
    DUCK("duck"),
    EAGLE("eagle"),
    BRACHIOSAURUS("brachiosaurus"),
    ;

    ModelType(String name) {
        this.modelName = name;
    }

    public void load() {
        if(model != null) return;
        try {
            if(this == TERRAIN)
                this.model = TerrainGenerator.generateTerrain(128, 10);
            else
                this.model = ModelLoader.loadModel(this.modelName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Model getModel() {
        load();
        return model;
    }

    private final String modelName;
    private Model model = null;
}
