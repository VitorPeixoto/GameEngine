#version 430

precision highp float;

in vec3 position;
in vec2 uv;
in vec3 normal;

out Vertex {
	vec3 position;
	vec3 normal;
	vec2 uv;
} out_vertex;

uniform mat4 normal_matrix;
uniform mat4 model_view_matrix;
uniform mat4 model_view_projection_matrix;

void main() {
	out_vertex.uv = uv;
    out_vertex.normal = normalize(normal_matrix * vec4(normal, 1)).xyz;
	out_vertex.position = vec3(model_view_matrix * vec4(position, 1));

    gl_Position = model_view_projection_matrix * vec4(position, 1);
}