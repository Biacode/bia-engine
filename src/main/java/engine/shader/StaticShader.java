package engine.shader;

import static engine.util.FileLoadUtils.getResourcePath;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 6:07 PM
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = getResourcePath("shaders/static/vertexShader.glsl");
    private static final String FRAGMENT_FILE = getResourcePath("shaders/static/fragmentShader.glsl");

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }
}
