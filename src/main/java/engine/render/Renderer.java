package engine.render;

import engine.entity.Entity;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.shader.ShaderProgram;
import engine.texture.ModelTexture;
import engine.toolbox.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 4:09 PM
 */
public class Renderer {

    //region Properties
    // The field of view
    private static final float FOV = 70.F;

    private static final float NEAR_PLANE = 0.1F;

    private static final float FAR_PLANE = 1000.F;

    private Matrix4f projectionMatrix;
    //endregion

    //region Constructors
    public Renderer(final ShaderProgram shader) {
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    //endregion

    //region Public API
    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.33F, 0.33F, 0.33F, 1);
    }

    public void render(final Entity entity, final ShaderProgram shader) {
        // unwrap arguments
        final TexturedModel texturedModel = entity.getModel();
        final RawModel rawModel = texturedModel.getRawModel();
        final ModelTexture texture = texturedModel.getTexture();
        GL30.glBindVertexArray(rawModel.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        final Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                entity.getPosition(),
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale()
        );
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    //endregion

    //region Utility methods
    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustumLength = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = xScale;
        projectionMatrix.m11 = yScale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
        projectionMatrix.m33 = 0;
    }
    //endregion

}
