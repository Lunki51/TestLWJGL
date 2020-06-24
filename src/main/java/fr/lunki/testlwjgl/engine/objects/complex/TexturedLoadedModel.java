package fr.lunki.testlwjgl.engine.objects.complex;


import fr.lunki.testlwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.io.ModelLoader;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.TexturedGameObject;

public class TexturedLoadedModel extends TexturedGameObject {
    public TexturedLoadedModel(Vector3f position, Vector3f rotation, Vector3f scale, TexturedMesh mesh) {
        super(position, rotation, scale,mesh);
    }


    @Override
    public void updateMesh() {
        getMesh().updateIndices(getPosition());
    }


}
