#version 430

precision highp float;

const int numlights = 1;

in Vertex {
	vec3 position;
	vec3 normal;
	vec2 uv;
} vertex;

struct Light
{
	vec3 position;
	vec3 ambient_light;
	vec3 diffuse_light;
	vec3 specular_light;
};

struct Material
{
	vec3 ambient_coefficient;
	vec3 diffuse_coefficient;
	vec3 specular_coefficient;
	float specular_shininess;
};

uniform Light lights[numlights];
uniform Material material;
uniform sampler2D sampler;

out vec4 fragment_color;

void calculate_light(int lightIndex, out vec3 ambient, out vec3 diffuse, out vec3 specular) {
	vec3 normal  = normalize(vertex.normal);
	vec3 light   = normalize(lights[lightIndex].position - vertex.position);
	vec3 view    = normalize(-vertex.position);
	vec3 reflect = reflect(-light, normal);

	ambient = lights[lightIndex].ambient_light * material.ambient_coefficient;

	float dot_light_normal = max(dot(light, normal), 0.0);
	diffuse = lights[lightIndex].diffuse_light * material.diffuse_coefficient * dot_light_normal;

	specular = lights[lightIndex].specular_light * material.specular_coefficient * pow(max(dot(reflect, view), 0.0), material.specular_shininess);
}

void main() {
	vec3 total_ambient = vec3(0);
	vec3 total_diffuse = vec3(0);
	vec3 total_specular = vec3(0);
	vec3 ambient, diffuse, specular;

	for(int i=0; i < numlights; i++) {
		calculate_light(i, ambient, diffuse, specular);

		total_ambient  += ambient;
		total_diffuse  += diffuse;
		total_specular += specular;
	}

	total_ambient /= numlights;

	vec4 texture_color = texture(sampler, vertex.uv);

    total_specular = clamp(total_specular, 0.0, 1.0);

	fragment_color = vec4(total_ambient + total_diffuse, 1) * texture_color + vec4(total_specular, 1);
}