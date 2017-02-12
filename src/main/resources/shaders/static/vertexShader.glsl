#version 400 core
// if this cause issue use 140

in vec3 position;

out vec3 colour;

void main(void) {
    gl_Position = vec4(position, 1.0);
    colour = vec3(position.x + 0.5, 1.0, position.y + 0.5);
}
