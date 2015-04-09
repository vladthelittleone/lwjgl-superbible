package utils;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

/**
 * @author Skurishin Vladislav
 * @since 09.04.15
 */
public abstract class AbstractDisplay
{
    private boolean preRender = true;

    public AbstractDisplay(DisplayMode mode, PixelFormat pixelFormat, ContextAttribs contextAttribs) throws Exception
    {
        startup(mode, pixelFormat, contextAttribs);

        while (!Display.isCloseRequested())
        {
            if (withLoopRender() || preRender)
            {
                render();
                Display.update();
                preRender = false;
            }
        }

        Display.destroy();

        shutdown();
    }

    public AbstractDisplay(DisplayMode mode, ContextAttribs contextAttribs) throws Exception
    {
        this(mode, new PixelFormat(), contextAttribs);
    }

    protected void startup(DisplayMode mode, PixelFormat pixelFormat, ContextAttribs contextAttribs) throws Exception
    {
        try
        {
            Display.setDisplayMode(mode);
            Display.create(pixelFormat, contextAttribs);
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
            System.exit(0);
        }

        startup();
    }

    protected boolean withLoopRender()
    {
        return true;
    }

    protected abstract void startup() throws Exception;

    protected abstract void render() throws Exception;

    protected abstract void shutdown() throws Exception;
}
