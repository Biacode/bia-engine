package engine.model;

import engine.texture.ModelTexture;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 8:40 PM
 */
public class TexturedModel {

    private final RawModel rawModel;

    private final ModelTexture texture;

    public TexturedModel(final RawModel rawModel, final ModelTexture texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
