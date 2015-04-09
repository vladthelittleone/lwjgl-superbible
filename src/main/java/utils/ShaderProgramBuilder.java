package utils;

import org.lwjgl.opengl.Display;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * @author Skurishin Vladislav
 * @since 09.04.15
 */
public class ShaderProgramBuilder
{
    private Set<Integer> shaders = new HashSet<>();

    protected ShaderProgramBuilder()
    {}

    public static ShaderProgramBuilder program()
    {
        return new ShaderProgramBuilder();
    }

    public ShaderProgramBuilder compileShader(String shaderSource, int type)
    {
        if (shaderSource == null)
        {
            throw new IllegalArgumentException("Shader compile failed. Error: Shader source can't be null.");
        }

        int shader = glCreateShader(type);

        glShaderSource(shader, shaderSource);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE)
        {
            String log = glGetShaderInfoLog(shader, glGetShaderi(shader, GL_INFO_LOG_LENGTH));
            throw new IllegalStateException(format("Shader compile failed. Error: %s", log));
        }

        shaders.add(shader);

        return this;
    }

    // Create and compile fragment shader
    public ShaderProgramBuilder compileFragmentShader(String fragmentShaderSource)
    {
        return compileShader(fragmentShaderSource, GL_FRAGMENT_SHADER);
    }

    // Create and compile vertex shader
    public ShaderProgramBuilder compileVertexShader(String vertexShaderSource)
    {
        return compileShader(vertexShaderSource, GL_VERTEX_SHADER);
    }

    // Create program, attach shaders to it, and link it
    public ShaderProgram createProgram()
    {
        int programKey = glCreateProgram();

        attachShaders(programKey);

        glLinkProgram(programKey);

        deleteShaders();

        return new ShaderProgram(programKey);
    }

    private void attachShaders(int programKey)
    {
        for (Integer shader : shaders)
        {
            glAttachShader(programKey, shader);
        }
    }

    // Delete the shaders as the program has them now
    private void deleteShaders()
    {
        for (Integer shader : shaders)
        {
            glDeleteShader(shader);
        }
    }

    /**
     * @author Skurishin Vladislav
     * @since 09.04.15
     */
    public static class ShaderProgram
    {
        private final int program;

        private boolean isDeleted = false;

        private ShaderProgram(int program)
        {
            this.program = program;
        }

        public void delete()
        {
            glDeleteProgram(program);
            isDeleted = true;
        }

        public void use()
        {
            if (isDeleted)
            {
                System.err.println("Program have already deleted, please create new");
                Display.destroy();
            }

            glUseProgram(program);
        }
    }

}
