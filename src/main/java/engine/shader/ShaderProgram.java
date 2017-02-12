package engine.shader;

import engine.util.FileLoadUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.FloatBuffer;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 5:50 PM
 */
public abstract class ShaderProgram {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShaderProgram.class);

    //region Properties
    private final int programId;

    private final int vertShaderId;

    private final int fragShaderId;

    private static final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    //endregion

    //region Public API
    public ShaderProgram(final String vertexFile, final String fragmentFle) {
        LOGGER.debug("Initializing shader program for vertex - {} and fragment - {}", vertexFile, fragmentFle);
        vertShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragShaderId = loadShader(fragmentFle, GL20.GL_FRAGMENT_SHADER);
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertShaderId);
        GL20.glAttachShader(programId, fragShaderId);
        bindAttributes();
        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);
        LOGGER.debug("Initialized shader - {} vertex - {} fragment - {}", programId, vertShaderId, fragShaderId);
        getAllUniformLocations();
    }

    public void start() {
        GL20.glUseProgram(programId);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        LOGGER.debug("Cleaning resources for program - {} vertex - {} fragment - {}", programId, vertShaderId, fragShaderId);
        stop();
        GL20.glDetachShader(programId, vertShaderId);
        GL20.glDetachShader(programId, fragShaderId);
        GL20.glDeleteShader(vertShaderId);
        GL20.glDeleteShader(fragShaderId);
        GL20.glDeleteProgram(programId);
    }
    //endregion

    //region Developer API
    protected abstract void bindAttributes();

    protected abstract void getAllUniformLocations();

    public abstract void loadTransformationMatrix(final Matrix4f matrix);

    protected void bindAttribute(final int attribute, final String variableName) {
        GL20.glBindAttribLocation(programId, attribute, variableName);
    }

    protected int getUniformLocation(final String uniformName) {
        return GL20.glGetUniformLocation(programId, uniformName);
    }

    protected void loadFloat(final int location, final float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(final int location, final Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadBoolean(final int location, final boolean value) {
        float toLoad = 0;
        if (value) {
            toLoad = 1;
        }
        GL20.glUniform1f(location, toLoad);
    }

    protected void loadMatrix(final int location, final Matrix4f matrix) {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }
    //endregion

    //region Utility methods
    private static int loadShader(String file, int type) {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, FileLoadUtils.loadFileSource(file));
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            final String glGetShaderInfoLog = GL20.glGetShaderInfoLog(shaderID, 500);
            LOGGER.error("Can not compile shader - {}", glGetShaderInfoLog);
            throw new IllegalArgumentException("Can not compile shader - " + glGetShaderInfoLog);
        }
        return shaderID;
    }
    //endregion

}
