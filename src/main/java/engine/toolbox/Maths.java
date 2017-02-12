package engine.toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 11:01 PM
 */
public final class Maths {

    private Maths() {
    }

    //region Public API
    public static Matrix4f createTransformationMatrix(final Vector3f translation, final float rx, final float ry, final float rz, final float scale) {
        final Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }
    //endregion

}
