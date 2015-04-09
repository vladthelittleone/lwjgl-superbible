import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import utils.ShaderProgramBuilder;
import utils.ShaderProgramBuilder.ShaderProgram;
import utils.Utils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * @author Skurishin Vladislav
 * @since 06.04.15
 * <p>
 * <p>
 * • glCreateShader() creates an empty shader object, ready to accept source code and be compiled.
 * • glShaderSource() hands shader source code to the shader object so that it can keep a copy of it.
 * • glCompileShader() compiles whatever source code is contained in the shader object.
 * • glCreateProgram() creates a program object to which you can attach shader objects.
 * • glAttachShader() attaches a shader object to a program object.
 * ￼• glLinkProgram() links all of the shader objects attached to a program object together.
 * • glDeleteShader() deletes a shader object. Once a shader has been linked into a program object, the program contains the binary code and the shader is no longer needed.
 */
public class Listening2_7
{
    // Source code for vertex shader
    private final String vertexShaderSource =
            "#version 410 core                              \n" +
            "void main(void)                                \n" +
            "{                                              \n" +
            "    gl_Position = vec4(0.0, 0.0, 0.5, 1.0);    \n" +
            "}                                              \n";

    // Source code for fragment shader
    private final String fragmentShaderSource =
            "#version 410 core                              \n" +
            "out vec4 color;                                \n" +
            "void main(void)                                \n" +
            "{                                              \n" +
            "    color = vec4(0.0, 0.8, 1.0, 1.0);          \n" +
            "}                                              \n";

    private ShaderProgram program;

    private int vao;

    public void start()
    {
        startup();

        render();

        Display.update();

        while (!Display.isCloseRequested())
        {
        }

        Display.destroy();

        shutdown();
    }

    private void render()
    {
        long currentTime = System.currentTimeMillis();

        // Simply clear the window with different colors
        glClearBuffer(GL_COLOR, 0, createClearFloatBuffer(currentTime));

        glPointSize(40f);

        // Use the program object we created earlier for rendering
        program.use();

        glDrawArrays(GL_POINTS, 0, 1);
    }

    private void shutdown()
    {
        program.delete();
        glDeleteVertexArrays(vao);
    }

    private void startup()
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

        program = ShaderProgramBuilder
                .program()
                .compileVertexShader(vertexShaderSource)
                .compileFragmentShader(fragmentShaderSource)
                .createProgram();

        vao = glGenVertexArrays();
        glBindVertexArray(vao);
    }

    private static FloatBuffer createClearFloatBuffer(long currentTime)
    {
        final float red[] = {
                (float) (Math.sin(currentTime) * 0.5f + 0.5f),
                (float) (Math.cos(currentTime) * 0.5f + 0.5f),
                0.0f,
                1.0f};

        return Utils.createFloatBuffer(red);
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
        Listening2_7 listening = new Listening2_7();
        listening.start();
    }
}
