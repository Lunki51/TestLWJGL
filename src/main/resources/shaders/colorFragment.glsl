#version 460 core

in vec3 passColor;
in vec2 passTextureCoord;

out vec4 outColor;

uniform sampler2D tex;

void main() {
    vec4 c = texture(tex, passTextureCoord);
    c.rgb=passColor;
    outColor=c;
}