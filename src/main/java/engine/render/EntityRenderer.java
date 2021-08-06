package engine.render;

import engine.entity.Entity;
import engine.model.TexturedModel;
import engine.shader.ShaderProgram;
import engine.toolbox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 4:09 PM
 */
public final class EntityRenderer {

    //region Properties
    private final ShaderProgram shader;
    //endregion

    //region Constructors
    public EntityRenderer(final ShaderProgram shader, final Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    //endregion

    //region Public API
    public void render(final Map<TexturedModel, List<Entity>> entities) {
        entities.forEach((key, value) -> {
            prepareTexturedModel(key);
            value.forEach(entity -> {
                prepareInstance(entity);
                GL11.glDrawElements(
                        GL11.GL_TRIANGLES,
                        key.getRawModel().getVertexCount(),
                        GL11.GL_UNSIGNED_INT,
                        0
                );
            });
            unbindTexturedModel();
        });
    }
    //endregion

    //region Utility methods
    private void prepareTexturedModel(final TexturedModel model) {
        GL30.glBindVertexArray(model.getRawModel().getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        if (model.getTexture().isHasTransparency()) {
            MasterRenderer.disableCulling();
        }
        shader.loadFakeLightingVariable(model.getTexture().isUseFakeLighting());
        shader.loadShineVariables(
                model.getTexture().getShineDamper(),
                model.getTexture().getReflectivity()
        );
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureId());
    }

    private void unbindTexturedModel() {
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(final Entity entity) {
        final Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                entity.getPosition(),
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale()
        );
        shader.loadTransformationMatrix(transformationMatrix);
    }
    //endregion

}
