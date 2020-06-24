package fr.lunki.testlwjgl.engine.objects.primitives;

import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.GameObject;

public class RectangleObject3D extends GameObject {

    public RectangleObject3D(Vector3f position, Vector3f rotation, Vector3f scale, TexturedMesh texturedTexturedMesh) {
        super(position, rotation, scale, texturedTexturedMesh);
    }
}
