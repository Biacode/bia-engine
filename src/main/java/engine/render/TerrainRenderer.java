package engine.render;

import engine.shader.TerrainShader;
import engine.terrain.Terrain;
import engine.toolbox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

/**
 * @author Arthur Asatryan
 * @since 2/20/17 3:15 PM
 */
public final class TerrainRenderer {

    //region Properties
    private final TerrainShader shader;
    //endregion

    //region Constructors
    public TerrainRenderer(final TerrainShader shader, final Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    //endregion

    //region Public API
    public void render(final List<Terrain> terrains) {
        terrains.forEach(terrain -> {
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(
                    GL11.GL_TRIANGLES,
                    terrain.getModel().getVertexCount(),
                    GL11.GL_UNSIGNED_INT,
                    0
            );
            unbindTexturedModel();
        });
    }
    //endregion

    //region Utility methods
    private void prepareTerrain(final Terrain terrain) {
        GL30.glBindVertexArray(terrain.getModel().getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.loadShineVariables(
                terrain.getTexture().getShineDamper(),
                terrain.getTexture().getReflectivity()
        );
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexture().getTextureId());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(final Terrain terrain) {
        final Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector3f(terrain.getX(), 0, terrain.getZ()),
                0, 0, 0, 1
        );
        shader.loadTransformationMatrix(transformationMatrix);
    }
    //endregion
}
