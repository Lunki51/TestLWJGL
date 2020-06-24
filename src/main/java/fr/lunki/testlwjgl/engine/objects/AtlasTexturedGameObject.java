package fr.lunki.testlwjgl.engine.objects;

import fr.lunki.testlwjgl.engine.graphics.material.AtlasMaterial;
import fr.lunki.testlwjgl.engine.graphics.meshes.AtlasTexturedMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector3f;

public abstract class AtlasTexturedGameObject extends TexturedGameObject {

    private int index;

    public AtlasTexturedGameObject(Vector3f position, Vector3f rotation, Vector3f scale, AtlasTexturedMesh texturedMesh,int index) {
        super(position, rotation, scale, texturedMesh);
        this.index=index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public AtlasTexturedMesh getMesh(){
        return (AtlasTexturedMesh) super.getMesh();
    }

    public float getTextureXOffset(){
        AtlasTexturedMesh atlasMesh = this.getMesh();
        AtlasMaterial atlasMaterial = (AtlasMaterial) atlasMesh.getMaterial();
        int column = getIndex()%atlasMaterial.getAtlasSize();
        return (float)column/(float)atlasMaterial.getAtlasSize();
    }

    public float getTextureYOffset(){
        AtlasTexturedMesh atlasMesh = this.getMesh();
        AtlasMaterial atlasMaterial = (AtlasMaterial) atlasMesh.getMaterial();
        int row = getIndex()/atlasMaterial.getAtlasSize();
        return (float)row/(float)atlasMaterial.getAtlasSize();
    }


}
