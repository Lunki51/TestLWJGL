#version 460 core

in vec3 position;
in vec3 color;
in vec2 textureCoord;
in vec3 normal;

out vec3 passColor;
out vec2 passTextureCoord;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;
out float visibility;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 lightPosition;

const float density = 0.0035;
const float gradient = 3;


void main() {
    vec4 worldPosition =model*vec4(position, 1.0);

    vec4 positionRelativeToCam = view * worldPosition ;
    float distance = length(positionRelativeToCam.xyz);
    visibility = exp(-pow((distance*density),gradient));
    visibility = clamp(visibility,0.0,1.0);

    gl_Position = projection*view*worldPosition;

    passColor = color;
    passTextureCoord = textureCoord;
    surfaceNormal = (model * vec4(normal,0.0)).xyz;
    toLightVector = lightPosition - worldPosition.xyz;
    toCameraVector = (inverse(view) * vec4(0,0,0,1)).xyz - worldPosition.xyz;
}