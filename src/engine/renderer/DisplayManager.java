package engine.renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.*;

import java.awt.*;


public class DisplayManager  {
    private static int FPS_CAP = 120;

    private static long lastFrameTime;
    private static float delta;

    public static void create(final int width, final int height, final boolean fullscreen, final String title) throws LWJGLException {
        ContextAttribs attribs = new ContextAttribs(3, 2)
            .withForwardCompatible(fullscreen)
            .withProfileCore(true);

        Display.setFullscreen(true);
        Display.setDisplayModeAndFullscreen(new DisplayMode(width, height));
        Display.setTitle(title);
        Display.create(new PixelFormat(), attribs);

        GL11.glViewport(0, 0, width, height);

        lastFrameTime = getCurrentTime();
    }

    public static void create() throws LWJGLException {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        DisplayManager.create((int)screenSize.getWidth(), (int)screenSize.getHeight(), true, "Awesome Game Engine");
    }

    public static void update() {
        Display.sync(FPS_CAP);
        Display.update();

        long currentTime = getCurrentTime();
        delta = currentTime - lastFrameTime;
        lastFrameTime = currentTime;
    }

    public static float getFrameTime() {
        return (delta / 1000f);
    }

    public static void close() {
        Display.destroy();
    }

    public static boolean isClosing() {
        return Display.isCloseRequested();
    }

    private static long getCurrentTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
}
