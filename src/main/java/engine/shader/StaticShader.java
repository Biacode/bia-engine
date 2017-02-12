package engine.shader;

import engine.entity.Camera;
import engine.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;

import static engine.util.FileLoadUtils.getResourcePath;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 6:07 PM
 */
public class StaticShader extends ShaderProgram {

    //region Properties
    private static final String VERTEX_FILE = getResourcePath("shaders/static/vertexShader.glsl");
    private static final String FRAGMENT_FILE = getResourcePath("shaders/static/fragmentShader.glsl");
    //endregion

    //region Constructors
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    //endregion

    //region Public API
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() {
        super.viewMatrixLocation = super.getUniformLocation("viewMatrix");
        super.projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        super.transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
    }

    @Override
    public void loadViewMatrix(final Camera camera) {
        super.loadMatrix(super.viewMatrixLocation, Maths.createViewMatrix(camera));
    }

    @Override
    public void loadProjectionMatrix(final Matrix4f matrix) {
        super.loadMatrix(super.projectionMatrixLocation, matrix);
    }

    @Override
    public void loadTransformationMatrix(final Matrix4f matrix) {
        super.loadMatrix(super.transformationMatrixLocation, matrix);
    }
    //endregion
}
