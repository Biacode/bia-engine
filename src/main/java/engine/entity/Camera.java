package engine.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Arthur Asatryan
 * @since 2/13/17 1:48 AM
 */
public class Camera {

    private static final Logger LOGGER = LoggerFactory.getLogger(Camera.class);

    //region Properties
    private Vector3f position = new Vector3f(0.F, 0.F, 0.F);

    private final float cameraSpeed;

    private float pitch;

    private float yaw;

    private float roll;
    //endregion

    //region Constructors
    public Camera(final float cameraSpeed) {
        LOGGER.debug("Initializing");
        this.cameraSpeed = cameraSpeed;
    }
    //endregion

    //region Public API
    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= cameraSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= cameraSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += cameraSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += cameraSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            position.y -= cameraSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            position.y += cameraSpeed;
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
    //endregion
}
