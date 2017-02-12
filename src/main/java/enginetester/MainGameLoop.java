package enginetester;

import engine.entity.Entity;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.render.DisplayManager;
import engine.render.Loader;
import engine.render.Renderer;
import engine.shader.ShaderProgram;
import engine.shader.StaticShader;
import engine.texture.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 3:09 PM
 */
public final class MainGameLoop {

    private MainGameLoop() {
    }

    public static void main(final String[] args) {
        DisplayManager.createDisplay();
        final Loader loader = new Loader();
        final Renderer renderer = new Renderer();
        final ShaderProgram shaderProgram = new StaticShader();
        final float[] vertices = {
                -0.5f, 0.5f, 0f,  // V0
                -0.5f, -0.5f, 0f, // V1
                0.5f, -0.5f, 0f,  // V2
                0.5f, 0.5f, 0f,   // V3
        };
        final float[] textureCoords = {
                0, 0, // V0
                0, 1, // V1
                1, 1, // V2
                1, 0  // V3
        };
        final int[] indices = {
                0, 1, 3, // (V0, V1, V3)
                3, 1, 2  // (V3, V1, V2)
        };
        final RawModel rawModel = loader.loadToVao(vertices, textureCoords, indices);
        final ModelTexture texture = new ModelTexture(loader.loadTexture("Flag_Armenia"));
        final TexturedModel texturedModel = new TexturedModel(rawModel, texture);
        final Entity entity = new Entity(
                texturedModel,
                new Vector3f(-1, 0, 0),
                0, 0, 0, 1
        );
        while (!Display.isCloseRequested()) {
            entity.translate(0.002F, 0F, 0F);
            entity.rotate(1F, 0F, 0F);
            entity.scale(0.002F);
            // post render
            renderer.prepare();
            shaderProgram.start();
            // render
            renderer.render(entity, shaderProgram);
            // post stop and stop
            shaderProgram.stop();
            DisplayManager.updateDisplay();
        }
        // cleanup resources
        shaderProgram.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
