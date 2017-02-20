package enginetester;

import engine.entity.Camera;
import engine.entity.Entity;
import engine.entity.Light;
import engine.model.TexturedModel;
import engine.render.DisplayManager;
import engine.render.Loader;
import engine.render.MasterRenderer;
import engine.terrain.Terrain;
import engine.texture.ModelTexture;
import engine.toolbox.ObjLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 3:09 PM
 */
public final class MainGameLoop {

    private MainGameLoop() {
    }

    public static void main(final String[] args) {
        DisplayManager.createDisplay();
        final MasterRenderer renderer = new MasterRenderer();
        final Loader loader = new Loader();
        final Camera camera = new Camera(0.5F);
        camera.getPosition().setY(7);
        // Objects
        final List<Terrain> terrains = buildTerrains(loader);
        final List<Entity> entities = buildEntities(loader);
        // per-pixel light (aka. Sun)
        final Light sun = new Light(
                new Vector3f(-64, 128, 30),
                new Vector3f(1, 1, 1)
        );

        // game loop
        while (!Display.isCloseRequested()) {
            // post render
            camera.move();
            final Entity entity = entities.get(0);
            entity.rotate(0.F, 1.F, 0.F);
            terrains.forEach(renderer::processTerrain);
            entities.forEach(renderer::processEntity);
            // render
            renderer.render(sun, camera);
            // post stop and stop
            DisplayManager.updateDisplay();
        }

        // cleanup resources
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    //region Utility methods
    private static List<Terrain> buildTerrains(final Loader loader) {
        final ModelTexture texture = new ModelTexture(loader.loadTexture("grass"));
        return new ArrayList<>(Arrays.asList(
                new Terrain(0, -1, loader, texture),
                new Terrain(-1, -1, loader, texture)
        ));
    }

    private static List<Entity> buildEntities(final Loader loader) {
        final List<Entity> entities = new ArrayList<>();
        entities.addAll(buildDragonModels(loader));
        entities.addAll(buildStallModels(loader));
        entities.addAll(buildTreeModels(loader));
        entities.addAll(buildLowPolyTreeModels(loader));
        entities.addAll(buildGrassModels(loader));
        entities.addAll(buildFernObjects(loader));
        return entities;
    }

    private static List<Entity> buildDragonModels(final Loader loader) {
        final TexturedModel texturedModel = new TexturedModel(
                ObjLoader.loadObjModel("dragon", loader),
                new ModelTexture(loader.loadTexture("white"))
        );
        // entity's specular light
        texturedModel.getTexture().setShineDamper(10);
        texturedModel.getTexture().setReflectivity(1);
        return Collections.singletonList(new Entity(
                texturedModel,
                new Vector3f(0, 0, -30),
                0, 180, 0, 2
        ));
    }

    private static List<Entity> buildStallModels(final Loader loader) {
        final TexturedModel texturedModel = new TexturedModel(
                ObjLoader.loadObjModel("stall", loader),
                new ModelTexture(loader.loadTexture("stallTexture"))
        );
        // entity's specular light
        texturedModel.getTexture().setShineDamper(10);
        texturedModel.getTexture().setReflectivity(1);
        return Collections.singletonList(new Entity(
                texturedModel,
                new Vector3f(-25, 0, -30),
                0, 180, 0, 2
        ));
    }

    private static List<Entity> buildGrassModels(final Loader loader) {
        final TexturedModel texturedModel = new TexturedModel(
                ObjLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("grassTexture"))
        );
        texturedModel.getTexture().setHasTransparency(true);
        texturedModel.getTexture().setUseFakeLighting(true);
        final List<Entity> entities = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            entities.add(buildRandomTransformedEntity(texturedModel));
        }
        return entities;
    }

    private static List<Entity> buildTreeModels(final Loader loader) {
        final TexturedModel texturedModel = new TexturedModel(
                ObjLoader.loadObjModel("tree", loader),
                new ModelTexture(loader.loadTexture("tree"))
        );
        final List<Entity> entities = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            entities.add(buildRandomTransformedEntity(texturedModel));
        }
        entities.forEach(entity -> entity.scale(6));
        return entities;
    }

    private static List<Entity> buildLowPolyTreeModels(final Loader loader) {
        final TexturedModel texturedModel = new TexturedModel(
                ObjLoader.loadObjModel("lowPolyTree", loader),
                new ModelTexture(loader.loadTexture("lowPolyTree"))
        );
        final List<Entity> entities = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            entities.add(buildRandomTransformedEntity(texturedModel));
        }
        return entities;
    }

    private static List<Entity> buildFernObjects(final Loader loader) {
        final TexturedModel texturedModel = new TexturedModel(
                ObjLoader.loadObjModel("fern", loader),
                new ModelTexture(loader.loadTexture("flower"))
        );
        texturedModel.getTexture().setHasTransparency(true);
        texturedModel.getTexture().setUseFakeLighting(true);
        final List<Entity> entities = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            entities.add(buildRandomTransformedEntity(texturedModel));
        }
        return entities;
    }

    private static Entity buildRandomTransformedEntity(final TexturedModel texturedModel) {
        final Random random = new Random();
        final int i = random.nextInt(2500) + 1;
        int next = i % 2 == 0 ? i : -i;
        return new Entity(
                texturedModel,
                new Vector3f(-next * random.nextFloat(), 0, -next * random.nextFloat()),
                0, 0, 0, 1
        );
    }
    //endregion

}
