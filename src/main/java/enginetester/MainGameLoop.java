package enginetester;

import engine.render.DisplayManager;
import engine.render.Loader;
import engine.render.RawModel;
import engine.render.Renderer;
import engine.shader.ShaderProgram;
import engine.shader.StaticShader;
import org.lwjgl.opengl.Display;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 3:09 PM
 */
public class MainGameLoop {

    public static void main(final String[] args) {
        DisplayManager.createDisplay();
        final Loader loader = new Loader();
        final Renderer renderer = new Renderer();
        final ShaderProgram shaderProgram = new StaticShader();
        final float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
        };
        final int[] indices = {
                0, 1, 3,
                3, 1, 2
        };
        final RawModel model = loader.loadToVao(vertices, indices);
        while (!Display.isCloseRequested()) {
            // post render
            renderer.prepare();
            shaderProgram.start();
            // render
            renderer.render(model);
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
