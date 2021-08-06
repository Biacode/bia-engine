package engine.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 3:01 PM
 */
public final class DisplayManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayManager.class);

    //region Properties
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;
    private static final String WINDOW_TITLE = "BiaEngine";
    //endregion

    private DisplayManager() {
    }

    //region Public API
    public static void createDisplay() {
        final ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle(WINDOW_TITLE);
        } catch (final LWJGLException ex) {
            LOGGER.error("Exception occurred while creating display", ex);
        }
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
    }

    public static void closeDisplay() {
        Display.destroy();
    }
    //endregion

}
