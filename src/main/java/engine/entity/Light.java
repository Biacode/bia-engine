package engine.entity;

import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Arthur Asatryan
 * @since 2/15/17 12:44 AM
 */
public class Light {

    private static final Logger LOGGER = LoggerFactory.getLogger(Light.class);

    //region Properties
    private Vector3f position;

    private Vector3f colour;
    //endregion

    //region Constructors
    public Light(final Vector3f position, final Vector3f colour) {
        LOGGER.debug("Initializing the lighting for position - {} and colour - {}", position, colour);
        this.position = position;
        this.colour = colour;
    }
    //endregion

    //region Public API
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(final Vector3f colour) {
        this.colour = colour;
    }
    //endregion
}
