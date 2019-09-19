package engine.loaders;

import engine.ResourceManager;
import org.newdawn.slick.opengl.Texture;

import java.io.FileInputStream;
import java.io.IOException;

public class TextureLoader {
    private static final String parent = "/textures/";

    public static int load(String name) {
        Texture texture;

        try {
            texture = org.newdawn.slick.opengl.TextureLoader.getTexture(
                    "PNG",
                    new FileInputStream(ResourceManager.get(parent, name, ".png"))
            );
            return texture.getTextureID();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
