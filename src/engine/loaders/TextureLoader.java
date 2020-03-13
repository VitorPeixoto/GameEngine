package engine.loaders;

import engine.ResourceManager;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import java.io.FileInputStream;
import java.io.IOException;

public class TextureLoader {
    private static final String parent = "/textures/";

    public static int load(String name) {
        Texture texture;

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        try {
            texture = org.newdawn.slick.opengl.TextureLoader.getTexture(
                    "PNG",
                    new FileInputStream(ResourceManager.get(parent, name))
            );
            return texture.getTextureID();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
