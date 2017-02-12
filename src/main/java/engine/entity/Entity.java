package engine.entity;

import engine.model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author Arthur Asatryan
 * @since 2/13/17 12:21 AM
 */
public class Entity {

    //region Properties
    private TexturedModel model;

    private Vector3f position;

    private float rotX;
    private float rotY;
    private float rotZ;

    private float scale;
    //endregion

    //region Constructors
    public Entity(final TexturedModel model, final Vector3f position, final float rotX, final float rotY, final float rotZ, final float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }
    //endregion

    //region Public API
    public void translate(final float dx, final float dy, final float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void rotate(final float dx, final float dy, final float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public void scale(final float scale) {
        this.scale += scale;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(final TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(final float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(final float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(final float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(final float scale) {
        this.scale = scale;
    }
    //endregion
}
