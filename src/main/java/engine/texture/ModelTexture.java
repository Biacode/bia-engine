package engine.texture;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 8:39 PM
 */
public class ModelTexture {

    private int textureId;

    private float shineDamper = 1;

    private float reflectivity = 0;

    private boolean hasTransparency;

    private boolean useFakeLighting;

    public ModelTexture(final int textureId) {
        this.textureId = textureId;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(final int textureId) {
        this.textureId = textureId;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(final float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(final float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public boolean isHasTransparency() {
        return hasTransparency;
    }

    public void setHasTransparency(final boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public boolean isUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(final boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }
}
