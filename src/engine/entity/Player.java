package engine.entity;

import engine.renderer.DisplayManager;
import engine.renderer.models.ModelType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -20;
    private static final float JUMP_POWER = 10;

    private float currentRunSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardSpeed = 0;

    private boolean onAir = false;

    public Player(ModelType type, Vector3f position) {
        super(type, position);
    }

    public void move() {
        checkInputs();

        super.rotate(0, currentTurnSpeed * DisplayManager.getFrameTime(), 0);

        float distance = currentRunSpeed * DisplayManager.getFrameTime();
        float dx = (float) ( distance * Math.sin(Math.toRadians(super.getRotation().getY())));
        float dz = (float) ( distance * Math.cos(Math.toRadians(super.getRotation().getY())));

        if(super.getPosition().getY() > 0) {
            upwardSpeed += GRAVITY * DisplayManager.getFrameTime();
        }
        if(super.getPosition().getY() < 0) {
            this.getPosition().setY(0);
            upwardSpeed = 0;
            onAir = false;
        }

        super.translate(dx, upwardSpeed * DisplayManager.getFrameTime(), dz);
    }

    private void checkInputs() {
        this.currentRunSpeed = 0;
        this.currentTurnSpeed = 0;

        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentRunSpeed += RUN_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed += TURN_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentRunSpeed -= RUN_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentTurnSpeed -= TURN_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !onAir) {
            this.upwardSpeed = JUMP_POWER;
            onAir = true;
        }


        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            System.exit(0);
        }
    }
}
