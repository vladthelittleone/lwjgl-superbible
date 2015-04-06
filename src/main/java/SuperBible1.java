import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;

/**
 * @author Skurishin Vladislav
 * @since 06.04.15
 */
public class SuperBible1
{
    public void start()
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create(new PixelFormat(), createContextAttribs());
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
            System.exit(0);
        }

        long currentTime = System.currentTimeMillis();

        // Simply clear the window with different colors
        GL30.glClearBuffer(GL11.GL_COLOR, 0, createClearFloatBuffer(currentTime));

        Display.update();

        while (!Display.isCloseRequested()) { }

        Display.destroy();
    }

    private FloatBuffer createClearFloatBuffer(long currentTime)
    {
        final float red[] = {
                (float) (Math.sin(currentTime) * 0.5f + 0.5f),
                (float) (Math.cos(currentTime) * 0.5f + 0.5f),
                0.0f,
                1.0f };

        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(red.length).put(red);
        floatBuffer.flip();
        return floatBuffer;
    }

    /**
     * Settings for mac os.
     */
    private ContextAttribs createContextAttribs()
    {
        return new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
    }

    public static void main(String[] argv)
    {
        SuperBible1 superBible1 = new SuperBible1();
        superBible1.start();
    }
}
