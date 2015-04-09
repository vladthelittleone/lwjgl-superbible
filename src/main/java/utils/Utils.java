package utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextAttribs;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Skurishin Vladislav
 * @since 09.04.15
 */
public class Utils
{
    public static FloatBuffer createClearFloatBuffer(long currentTime)
    {
        final float red[] = {
                (float) (Math.sin(currentTime) * 0.5f + 0.5f),
                (float) (Math.cos(currentTime) * 0.5f + 0.5f),
                0.0f,
                1.0f};

        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(red.length).put(red);
        floatBuffer.flip();
        return floatBuffer;
    }

    public static String readFromFile(String file) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    /**
     * Settings for mac os.
     */
    public static ContextAttribs createContextAttribsForMac()
    {
        return new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
    }
}
