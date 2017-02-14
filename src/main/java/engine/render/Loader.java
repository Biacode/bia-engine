package engine.render;

import engine.model.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static engine.util.FileLoadUtils.getResourcePath;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 3:49 PM
 */
public class Loader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Loader.class);

    //region Properties
    private List<Integer> vaos = new ArrayList<>();

    private List<Integer> vbos = new ArrayList<>();

    private List<Integer> textures = new ArrayList<>();
    //endregion

    //region Public API
    public RawModel loadToVao(final float[] positions, final float[] textureCoords, final float[] normals, final int[] indices) {
        int vaoId = createVao();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);
        unbindVao();
        return new RawModel(vaoId, indices.length);
    }

    public int loadTexture(final String fileName) {
        Texture texture = null;
        try (final FileInputStream inputStream = new FileInputStream(getResourcePath("textures/" + fileName + ".png"))) {
            texture = TextureLoader.getTexture("PNG", inputStream);
        } catch (final IOException ex) {
            LOGGER.error("Exception occurred - {} while loading texture file - {}", ex, fileName);
        }
        assertTextureNotNullForFileName(texture, fileName);
        final int textureId = texture.getTextureID();
        textures.add(textureId);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        return textureId;
    }

    public void cleanUp() {
        vaos.forEach(GL30::glDeleteVertexArrays);
        vbos.forEach(GL15::glDeleteBuffers);
        textures.forEach(GL11::glDeleteTextures);
    }
    //endregion

    //region Utility methods
    private int createVao() {
        int vaoId = GL30.glGenVertexArrays();
        vaos.add(vaoId);
        GL30.glBindVertexArray(vaoId);
        return vaoId;
    }

    private void storeDataInAttributeList(final int attributeNumber, final int coordinateSize, final float[] data) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        final FloatBuffer floatBuffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVao() {
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(final int[] indices) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
        final IntBuffer intBuffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(final int[] data) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(data.length);
        intBuffer.put(data);
        intBuffer.flip();
        return intBuffer;
    }

    private FloatBuffer storeDataInFloatBuffer(final float[] data) {
        return (FloatBuffer) BufferUtils
                .createFloatBuffer(data.length)
                .put(data)
                .flip();
    }

    private void assertTextureNotNullForFileName(final Texture texture, final String fileName) {
        if (texture == null) {
            LOGGER.error("Can not load texture for file - {}", fileName);
            throw new IllegalArgumentException("Can not load texture for file - " + fileName);
        }
    }
    //endregion
}
