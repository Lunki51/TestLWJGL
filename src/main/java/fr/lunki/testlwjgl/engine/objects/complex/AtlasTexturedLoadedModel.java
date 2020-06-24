package fr.lunki.testlwjgl.engine.objects.complex;

import fr.lunki.testlwjgl.engine.graphics.material.AtlasMaterial;
import fr.lunki.testlwjgl.engine.graphics.meshes.AtlasTexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.AtlasTexturedGameObject;

public class AtlasTexturedLoadedModel extends AtlasTexturedGameObject {


    public AtlasTexturedLoadedModel(Vector3f position, Vector3f rotation, Vector3f scale, AtlasTexturedMesh atlasTexturedMesh,int index) {
        super(position, rotation, scale, atlasTexturedMesh,index);
    }

    @Override
    public void updateMesh() {
        getMesh().updateIndices(getPosition());
    }
}
