package engine.renderer;

import engine.entity.Player;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position;

    private float zoom = 50;
    private float horizontalAngle = 0;
    private float pitch = 20, yaw = 0, roll;

    private Player target;

    public Camera(Player player) {
        target = player;
        this.position = new Vector3f();
        this.move();
    }

    public void move() {
        this.calculateZoom();
        this.calculatePitch();
        this.calculateHorizontalAngle();

        this.calculatePosition();

        /*if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
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
        }*/

    }

    private void calculatePosition() {
        float horizontalDistance = getHorizontalDistance();
        float verticalDistance = getVerticalDistance();

        float totalHorizontalAngle = this.horizontalAngle - target.getRotation().getY();

        this.position.set(
                (float)(target.getPosition().getX() + (horizontalDistance * Math.sin(Math.toRadians(totalHorizontalAngle)))),
                target.getPosition().getY() + verticalDistance,
                (float)(target.getPosition().getZ() - (horizontalDistance * Math.cos(Math.toRadians(totalHorizontalAngle))))
        );

        this.yaw = 180 + totalHorizontalAngle;
    }

    private void calculateZoom() {
        this.zoom -= Mouse.getDWheel() * 0.1f;
    }

    private void calculatePitch() {
        if(Mouse.isButtonDown(1)) {
            this.pitch -= Mouse.getDY() * 0.1f;
        }
    }

    private void calculateHorizontalAngle() {
        if(Mouse.isButtonDown(0)) {
            this.horizontalAngle -= Mouse.getDX() * 0.3f;
        }
    }

    private float getHorizontalDistance() {
        return (float) (this.zoom * Math.cos(Math.toRadians(this.pitch)));
    }

    private float getVerticalDistance() {
        return (float) (this.zoom * Math.sin(Math.toRadians(this.pitch)));
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
