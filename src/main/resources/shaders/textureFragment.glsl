#version 460 core

in vec3 passColor;
in vec2 passTextureCoord;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 outColor;

uniform sampler2D tex;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main() {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal,unitLightVector);
    float brightness = max(nDot1,0.2);
    vec3 diffuse = brightness * lightColour ;

    vec3 unitToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection,surfaceNormal);

    float specularFactor = dot(reflectedLightDirection,unitToCamera);
    specularFactor = max(specularFactor,0.2);
    float dampedFactor = pow(specularFactor,shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColour ;

    vec4 textureColour = texture(tex, passTextureCoord);
    if(textureColour.a < 0.4){
        discard;
    }
    outColor= vec4(diffuse,1.0) * textureColour + vec4(finalSpecular,1.0);
    outColor = mix(vec4(skyColour,1.0),outColor,visibility);
}