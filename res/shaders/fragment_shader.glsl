#version 400 core

in vec2 uv_coordinates;

out vec4 pixel_color;

uniform sampler2D texture_sampler;

void main(void) {
    pixel_color = texture(texture_sampler, uv_coordinates);
}