#version 410 core

// Input from the vertex shader
in vec4 vs_color;

// Output to the framebuffer
out vec4 color;

// Source code for fragment shader
void main(void)
{
    // Simply assign the color we were given by the vertex shader
    // to our output
    color = vs_color;
}
