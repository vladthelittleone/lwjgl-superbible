import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class ColorMaterialExample
{

    public void start()
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        // Включаем свет
        GL11.glEnable(GL11.GL_LIGHTING);

        // Включаем глубину
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Устранение невидимых поверхностей
        GL11.glEnable(GL11.GL_CULL_FACE);

        while (!Display.isCloseRequested())
        {
            // Clear the screen and depth buffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // set the color of the quad (R,G,B,A)
            GL11.glColor3f(0.5f, 0.5f, 1.0f);

            float array[] = { 0.75f, 0.75f, 0.75f, 1.0f};
            FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length).put(array);
            buffer.flip();

            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, buffer);
            // draw quad
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(100, 100);
                GL11.glVertex2f(100 + 200, 100);
                GL11.glVertex2f(100 + 200, 100 + 200);
                GL11.glVertex2f(100, 100 + 200);
            GL11.glEnd();

            Display.update();
        }

        Display.destroy();
    }

    public static void main(String[] argv)
    {
        ColorMaterialExample colorMaterialExample = new ColorMaterialExample();
        colorMaterialExample.start();
    }
}
