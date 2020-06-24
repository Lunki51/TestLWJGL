package fr.lunki.testlwjgl.engine.objects;

import fr.lunki.testlwjgl.engine.graphics.material.Texture;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector3f;

public abstract class TexturedGameObject extends GameObject implements Textured {

    public TexturedGameObject(Vector3f position, Vector3f rotation, Vector3f scale, TexturedMesh texturedMesh) {
        super(position, rotation, scale, texturedMesh);
    }

    @Override
    public TexturedMesh getMesh() {
        return (TexturedMesh) super.getMesh();
    }

    @Override
    public void createMesh() {
        TexturedMesh mesh = getMesh();
        mesh.create();
        mesh.updateIndices(getPosition());
    }

    public void updateMesh() {

    }


    public void destroyMesh() {
        getMesh().destroy();
    }

}
