import org.lwjgl.opengl.DisplayMode;
import utils.AbstractDisplay;
import utils.ShaderProgramBuilder;
import utils.Utils;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * @author Skurishin Vladislav
 * @since 09.04.15
 */
public class Listening2_9 extends AbstractDisplay
{
    private ShaderProgramBuilder.ShaderProgram program;

    private int vao;

    public Listening2_9() throws Exception
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
                .compileVertexShader(Utils.readFromFile("src/main/shaders/listening2.9.vert"))
                .compileFragmentShader(Utils.readFromFile("src/main/shaders/listening2.9.frag"))
                .createProgram();

        vao = glGenVertexArrays();
        glBindVertexArray(vao);
    }

    @Override
    protected void render()
    {
        long currentTime = System.currentTimeMillis();

        // Simply clear the window with different colors
        glClearBuffer(GL_COLOR, 0, Utils.createClearFloatBuffer(currentTime));

        glPointSize(40f);

        // Use the program object we created earlier for rendering
        program.use();

        // Draw one triangle
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    @Override
    protected boolean withLoopRender()
    {
        return false;
    }

    public static void main(String[] argv) throws Exception
    {
        new Listening2_9();
    }
}
