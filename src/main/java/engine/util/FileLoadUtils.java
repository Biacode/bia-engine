package engine.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Arthur Asatryan
 * @since 2/12/17 6:29 PM
 */
public final class FileLoadUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileLoadUtils.class);

    private FileLoadUtils() {
    }

    public static String loadFileSource(final String file) {
        LOGGER.debug("Loading file - {}", file);
        final StringBuilder shaderSource = new StringBuilder();
        try (
                final FileReader fileReader = new FileReader(file);
                final BufferedReader reader = new BufferedReader(fileReader)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
        } catch (final IOException ex) {
            LOGGER.error("Exception occurred - {} while loading file - {}", ex, file);
            System.exit(-1);
        }
        final String shaderSourceString = shaderSource.toString();
        LOGGER.debug("Loaded source - {}", shaderSourceString);
        return shaderSourceString;
    }
}
