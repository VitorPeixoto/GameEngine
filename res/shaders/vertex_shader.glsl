#version 400 core

in vec3 position;
in vec2 in_uv_coordinates;

out vec2 uv_coordinates;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

void main(void) {
    gl_Position = projection_matrix * view_matrix * transformation_matrix * vec4(position, 1.0);
    uv_coordinates = in_uv_coordinates;
}