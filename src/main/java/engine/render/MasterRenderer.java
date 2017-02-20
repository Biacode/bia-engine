package engine.render;

import engine.entity.Camera;
import engine.entity.Entity;
import engine.entity.Light;
import engine.model.TexturedModel;
import engine.shader.ShaderProgram;
import engine.shader.StaticShader;

import java.util.*;

/**
 * @author Arthur Asatryan
 * @since 2/20/17 2:07 PM
 */
public class MasterRenderer {

    private final ShaderProgram shader = new StaticShader();

    private final Renderer renderer = new Renderer(shader);

    private final Map<TexturedModel, List<Entity>> entities = new HashMap<>();

    public void render(final Light sun, final Camera camera) {
        renderer.prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        entities.clear();
    }

    public void processEntity(final Entity entity) {
        if (entities.get(entity.getModel()) != null) {
            entities.get(entity.getModel()).add(entity);
        } else {
            entities.put(entity.getModel(), new ArrayList<>(Arrays.asList(entity)));
        }
    }

    public void cleanUp() {
        shader.cleanUp();
    }
}
