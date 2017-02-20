#version 330 core

in vec2 outTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;

void main(void) {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);
    float nDot1 = dot(unitNormal, unitLightVector);
    // 0.2 - so there is no completely dark spots
    float brightness = max(nDot1, 0.2);
    vec3 diffuse = brightness * lightColour;
    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColour;
    outColor = vec4(diffuse, 1.0) * texture(textureSampler, outTextureCoords) + vec4(finalSpecular, 1.0);
}
