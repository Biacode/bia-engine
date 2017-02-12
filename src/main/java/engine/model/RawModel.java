package engine.model;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 3:48 PM
 */
public class RawModel {

    private final int vaoId;
    private final int vertexCount;

    public RawModel(final int vaoId, final int vertexCount) {
        this.vaoId = vaoId;
        this.vertexCount = vertexCount;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
