package engine.shader;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 6:07 PM
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = getPluginsPath() + "shaders/static/vertexShader.glsl";
    private static final String FRAGMENT_FILE = getPluginsPath() + "shaders/static/fragmentShader.glsl";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    private static String getPluginsPath() {
        return ClassLoader.getSystemResource("").getPath();
    }
}
