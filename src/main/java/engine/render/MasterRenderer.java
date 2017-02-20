package engine.render;

import engine.entity.Camera;
import engine.entity.Entity;
import engine.entity.Light;
import engine.model.TexturedModel;
import engine.shader.ShaderProgram;
import engine.shader.StaticShader;
import engine.shader.TerrainShader;
import engine.terrain.Terrain;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.*;

/**
 * @author Arthur Asatryan
 * @since 2/20/17 2:07 PM
 */
public class MasterRenderer {

    // The field of view
    private static final float FOV = 70.F;

    private static final float NEAR_PLANE = 0.1F;

    private static final float FAR_PLANE = 1000.F;

    private Matrix4f projectionMatrix;

    private final ShaderProgram shader = new StaticShader();

    private final EntityRenderer entityRenderer;

    private final TerrainRenderer terrainRenderer;

    private final TerrainShader terrainShader = new TerrainShader();

    private final Map<TexturedModel, List<Entity>> entities = new HashMap<>();

    private final List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer() {
        enableCulling();
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.33F, 0.33F, 0.33F, 1);
    }

    public void render(final Light sun, final Camera camera) {
        prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        shader.stop();
        terrainShader.start();
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();
        entities.clear();
    }

    public void processTerrain(final Terrain terrain) {
        terrains.add(terrain);
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
        terrainShader.cleanUp();
    }

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
}
