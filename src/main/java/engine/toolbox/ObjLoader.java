package engine.toolbox;

import engine.model.RawModel;
import engine.render.Loader;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static engine.util.FileLoadUtils.getResourcePath;

/**
 * @author Arthur Asatryan
 * @since 2/13/17 11:08 PM
 */
public final class ObjLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjLoader.class);

    private ObjLoader() {
    }

    //region Public API
    public static RawModel loadObjModel(final String fileName, final Loader loader) {
        LOGGER.debug("Loading obj file - {}", fileName);
        final List<Vector3f> vertices = new ArrayList<>();
        final List<Vector2f> textures = new ArrayList<>();
        final List<Vector3f> normals = new ArrayList<>();
        final List<Integer> indices = new ArrayList<>();

        final float[] verticesArray;
        float[] textureArray = null;
        float[] normalsArray = null;
        final int[] indicesArray;
        try (final FileReader fileReader = new FileReader(new File(getResourcePath("models/" + fileName + ".obj")))) {
            try (final BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        final String[] currentLine = line.split(" ");
                        if (line.startsWith("v ")) {
                            final Vector3f vertex = new Vector3f(
                                    Float.parseFloat(currentLine[1]),
                                    Float.parseFloat(currentLine[2]),
                                    Float.parseFloat(currentLine[3])
                            );
                            vertices.add(vertex);
                        } else if (line.startsWith("vt ")) {
                            final Vector2f texture = new Vector2f(
                                    Float.parseFloat(currentLine[1]),
                                    Float.parseFloat(currentLine[2])
                            );
                            textures.add(texture);
                        } else if (line.startsWith("vn ")) {
                            final Vector3f normal = new Vector3f(
                                    Float.parseFloat(currentLine[1]),
                                    Float.parseFloat(currentLine[2]),
                                    Float.parseFloat(currentLine[3])
                            );
                            normals.add(normal);
                        } else if (line.startsWith("f ")) {
                            textureArray = new float[vertices.size() * 2];
                            normalsArray = new float[vertices.size() * 3];
                            break;
                        }
                    }
                    while ((line = bufferedReader.readLine()) != null) {
                        final String[] currentLine = line.split(" ");
                        final String[] vertex1 = currentLine[1].split("/");
                        final String[] vertex2 = currentLine[2].split("/");
                        final String[] vertex3 = currentLine[3].split("/");
                        processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                        processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                        processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                    }
                } catch (final NumberFormatException ex) {
                    LOGGER.error("Failed to load obj file - {}", fileName);
                    throw new IllegalArgumentException("Failed to load obj file", ex);
                }
            }
        } catch (final FileNotFoundException ex) {
            LOGGER.error("Can not find file - {} exception - {}", fileName, ex);
        } catch (final IOException ex) {
            LOGGER.error("Unexpected IO exception occurred while loading obj file - {} exception - {}", fileName, ex);
        }
        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (final Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }
        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }
        return loader.loadToVao(verticesArray, textureArray, normalsArray, indicesArray);
    }
    //endregion

    //region Utility methods
    private static void processVertex(final String[] vertexData,
                                      final List<Integer> indices,
                                      final List<Vector2f> textures,
                                      final List<Vector3f> normals,
                                      final float[] textureArray,
                                      final float[] normalsArray) {
        final int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        final Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer * 2] = currentTexture.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTexture.y;
        final Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNormal.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNormal.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNormal.z;
    }
    //endregion

}
