package engine.shader;

import engine.entity.Camera;
import engine.entity.Light;
import engine.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;

import static engine.util.FileLoadUtils.getResourcePath;

/**
 * @author Arthur Asatryan
 * @since 2/20/17 3:13 PM
 */
public class TerrainShader extends ShaderProgram {

    //region Properties
    private static final String VERTEX_FILE = getResourcePath("shaders/terrainVertexShader.glsl");
    private static final String FRAGMENT_FILE = getResourcePath("shaders/terrainFragmentShader.glsl");
    //endregion

    //region Constructors
    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    //endregion

    //region Public API
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        super.viewMatrixLocation = super.getUniformLocation("viewMatrix");
        super.projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        super.transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        super.lightPositionLocation = super.getUniformLocation("lightPosition");
        super.lightColourLocation = super.getUniformLocation("lightColour");
        super.shineDamperLocation = super.getUniformLocation("shineDamper");
        super.reflectivityLocation = super.getUniformLocation("reflectivity");
    }

    @Override
    public void loadShineVariables(final float damper, final float reflectivity) {
        super.loadFloat(this.shineDamperLocation, damper);
        super.loadFloat(this.reflectivityLocation, reflectivity);
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

    @Override
    public void loadLight(final Light light) {
        super.loadVector(lightPositionLocation, light.getPosition());
        super.loadVector(lightColourLocation, light.getColour());
    }

    @Override
    public void loadFakeLightingVariable(final boolean useFake) {
        super.loadBoolean(useFakeLightingLocation, useFake);
    }
    //endregion

}
