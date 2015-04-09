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

    public static FloatBuffer createFloatBuffer(float[] array)
    {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(array.length).put(array);
        floatBuffer.flip();
        return floatBuffer;
    }
}
