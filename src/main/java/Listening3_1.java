import org.lwjgl.opengl.DisplayMode;
import utils.AbstractDisplay;
import utils.ShaderProgramBuilder;
import utils.Utils;

import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glVertexAttrib4f;
import static org.lwjgl.opengl.GL30.*;

/**
 * @author Skurishin Vladislav
 * @since 10.04.15
 */
public class Listening3_1 extends AbstractDisplay
{
    private ShaderProgramBuilder.ShaderProgram program;

    private int vao;

    public Listening3_1() throws Exception
    {
        super(new DisplayMode(800, 600), Utils.createContextAttribsForMac());
    }

    @Override
    protected void shutdown()
    {
        program.delete();
        glDeleteVertexArrays(vao);
    }

    @Override
    protected void startup() throws IOException
    {
        program = ShaderProgramBuilder
                .program()
                .compileVertexShader(Utils.readFromFile("src/main/shaders/listening3.1.vert"))
                .compileFragmentShader(Utils.readFromFile("src/main/shaders/listening3.1.frag"))
                .createProgram();

        vao = glGenVertexArrays();
        glBindVertexArray(vao);
    }

    @Override
    protected void render()
    {
        long currentTime = System.currentTimeMillis();

        // Simply clear the window with different colors
        glClearBuffer(GL_COLOR, 0, createClearFloatBuffer(currentTime));

        glPointSize(40f);

        // Use the program object we created earlier for rendering
        program.use();


        // Update the value of input attribute 0
        glVertexAttrib4f(0, (float) (Math.sin(currentTime) * 0.5f),
                (float) (Math.cos(currentTime) * 0.6f),
                0.0f,
                0.0f);

        glVertexAttrib4f(1, 0.0f, 0.8f, 1.0f, 1.0f);

        // Draw one triangle
        glDrawArrays(GL_TRIANGLES, 0, 3);
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

    public static void main(String[] argv) throws Exception
    {
        new Listening3_1();
    }
}

