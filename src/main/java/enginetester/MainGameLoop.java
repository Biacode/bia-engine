package enginetester;

import engine.entity.Camera;
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
        // graphics components
        final Loader loader = new Loader();
        final ShaderProgram shader = new StaticShader();
        final Renderer renderer = new Renderer(shader);
        final Camera camera = new Camera();

        // game objects
        final float[] vertices = buildVertices();
        final float[] textureCoords = buildTextures();
        final int[] indices = buildIndices();
        final RawModel rawModel = loader.loadToVao(vertices, textureCoords, indices);
        final ModelTexture texture = new ModelTexture(loader.loadTexture("Flag_Armenia"));
        final TexturedModel texturedModel = new TexturedModel(rawModel, texture);
        final Entity entity = new Entity(
                texturedModel,
                new Vector3f(0, 0, -2),
                0, 0, 0, 1
        );

        // game loop
        while (!Display.isCloseRequested()) {
            entity.translate(0.F, 0.F, -0.005F);
            entity.rotate(1, 1, 0.F);
            camera.move();
            // post render
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            // render
            renderer.render(entity, shader);
            // post stop and stop
            shader.stop();
            DisplayManager.updateDisplay();
        }
        // cleanup resources
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    //region Utility methods
    private static int[] buildIndices() {
        return new int[]{
                0, 1, 3,
                3, 1, 2,
                4, 5, 7,
                7, 5, 6,
                8, 9, 11,
                11, 9, 10,
                12, 13, 15,
                15, 13, 14,
                16, 17, 19,
                19, 17, 18,
                20, 21, 23,
                23, 21, 22
        };
    }

    private static float[] buildTextures() {
        return new float[]{
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
    }

    private static float[] buildVertices() {
        return new float[]{
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0,

                -0.5f, 0.5f, 1,
                -0.5f, -0.5f, 1,
                0.5f, -0.5f, 1,
                0.5f, 0.5f, 1,

                0.5f, 0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, -0.5f, 1,
                0.5f, 0.5f, 1,

                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                -0.5f, -0.5f, 1,
                -0.5f, 0.5f, 1,

                -0.5f, 0.5f, 1,
                -0.5f, 0.5f, 0,
                0.5f, 0.5f, 0,
                0.5f, 0.5f, 1,

                -0.5f, -0.5f, 1,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, -0.5f, 1
        };
    }
    //endregion

}
