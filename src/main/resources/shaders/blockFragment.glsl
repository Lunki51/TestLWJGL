#version 460 core

in vec3 passColor;
in vec2 passTextureCoord;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 outColor;

uniform sampler2D tex;
uniform vec3 lightColour;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal,unitLightVector);
    float brightness = max(nDot1,0.0);
    vec3 diffuse = brightness * lightColour;

    vec4 c = vec4(diffuse,1.0) * texture(tex, passTextureCoord);
    if(passColor.y!=0){
        c.rgb*=passColor;
    }
    outColor=c;
}