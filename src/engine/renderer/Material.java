package engine.renderer;

import org.lwjgl.util.vector.Vector3f;

public class Material {
    private Vector3f ambientColor, diffuseColor, specularColor, emissiveColor;
    private float specularExponent, opticalDensity, dissolveFactor;

    private String textureName;
    private Texture texture;

    public Material(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, Vector3f emissiveColor, float specularExponent, float opticalDensity, float dissolveFactor, String textureName) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.emissiveColor = emissiveColor;
        this.specularExponent = specularExponent;
        this.opticalDensity = opticalDensity;
        this.dissolveFactor = dissolveFactor;
        this.textureName = textureName;
    }

    public Vector3f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector3f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector3f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector3f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector3f getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector3f specularColor) {
        this.specularColor = specularColor;
    }

    public Vector3f getEmissiveColor() {
        return emissiveColor;
    }

    public void setEmissiveColor(Vector3f emissiveColor) {
        this.emissiveColor = emissiveColor;
    }

    public float getSpecularExponent() {
        return specularExponent;
    }

    public void setSpecularExponent(float specularExponent) {
        this.specularExponent = specularExponent;
    }

    public float getOpticalDensity() {
        return opticalDensity;
    }

    public void setOpticalDensity(float opticalDensity) {
        this.opticalDensity = opticalDensity;
    }

    public float getDissolveFactor() {
        return dissolveFactor;
    }

    public void setDissolveFactor(float dissolveFactor) {
        this.dissolveFactor = dissolveFactor;
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
