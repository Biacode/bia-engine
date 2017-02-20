package enginetester;

import engine.entity.Camera;
import engine.entity.Entity;
import engine.entity.Light;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.render.DisplayManager;
import engine.render.Loader;
import engine.render.Renderer;
import engine.shader.ShaderProgram;
import engine.shader.StaticShader;
import engine.texture.ModelTexture;
import engine.toolbox.ObjLoader;
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
        final Camera camera = new Camera(0.1F);

        // game objects
        final RawModel rawModel = ObjLoader.loadObjModel("dragon", loader);
        final ModelTexture texture = new ModelTexture(loader.loadTexture("white"));
        final TexturedModel texturedModel = new TexturedModel(rawModel, texture);
        texturedModel.getTexture().setShineDamper(10);
        texturedModel.getTexture().setReflectivity(1);
        final Entity entity = new Entity(
                texturedModel,
                new Vector3f(0, -3F, -30),
                0, 0, 0, 1
        );
        final Light light = new Light(
                new Vector3f(0, 30, 30),
                new Vector3f(1, 1, 1)
        );
        // game loop
        while (!Display.isCloseRequested()) {
            entity.translate(0.F, 0.F, 0.F);
            entity.rotate(0.F, 1.F, 0.F);
            camera.move();
            // post render
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
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

}
