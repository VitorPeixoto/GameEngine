package engine.renderer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Camera {
    private Vector3f position;
    private float pitch, yaw = 0, roll;

    public Camera() {
        this.position = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position) {
        this.position = position;
    }

    public void move() {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= 0.02;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= 0.02;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += 0.02;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += 0.02;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LBRACKET)) {
            position.y += 0.02;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_RBRACKET)) {
            position.y -= 0.02;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            yaw++;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            yaw--;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            pitch++;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            pitch--;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_HOME)) {
            roll++;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_END)) {
            roll--;
        }

        if(Math.abs(yaw) > 360.0f) yaw = 0;
        if(Math.abs(pitch) > 360.0f) pitch = 0;
        if(Math.abs(roll) > 360.0f) roll = 0;

        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            System.exit(0);
        }

    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
