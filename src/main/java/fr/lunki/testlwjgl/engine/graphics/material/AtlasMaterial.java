package fr.lunki.testlwjgl.engine.graphics.material;

public class AtlasMaterial extends Material {

    int atlasSize;

    public AtlasMaterial(Material from,int atlasSize){
        super(from.getShineDamper(),from.getReflectivity(),from.getTexture());
        this.atlasSize=atlasSize;
    }

    public AtlasMaterial(float shineDamper, float reflectivity, Texture texture,int atlasSize) {
        super(shineDamper, reflectivity, texture);
        this.atlasSize=atlasSize;
    }

    public int getAtlasSize() {
        return atlasSize;
    }
}
