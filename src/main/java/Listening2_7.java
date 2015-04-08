import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import java.nio.FloatBuffer;

import static java.lang.String.format;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
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

    private int renderingProgram;

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
        glUseProgram(renderingProgram);

        glDrawArrays(GL_POINTS, 0, 1);
    }

    private void shutdown()
    {
        glDeleteProgram(renderingProgram);
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

        renderingProgram = compileShaders(vertexShaderSource, fragmentShaderSource);
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
    }

    private int compileShaders(String vertexShaderSource, String fragmentShaderSource)
    {
        // Create and compile vertex shader
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);

        if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            String log = glGetShaderInfoLog(vertexShader, glGetShaderi(vertexShader, GL_INFO_LOG_LENGTH));
            System.err.println(format("Failure in compiling vertex shader. Error log: %s", log));
            Display.destroy();
        }

        // Create and compile fragment shader
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);

        if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
            String log = glGetShaderInfoLog(fragmentShader, glGetShaderi(fragmentShader, GL_INFO_LOG_LENGTH));
            System.err.println(format("Failure in compiling fragment shader. Error log: %s", log));
            Display.destroy();
        }

        // Create program, attach shaders to it, and link it
        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);

        // Delete the shaders as the program has them now
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        return program;
    }

    private FloatBuffer createClearFloatBuffer(long currentTime)
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
