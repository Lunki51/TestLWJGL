package fr.lunki.testlwjgl.engine.objects.primitives;

import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.TexturedGameObject;

public class RectangleObject2D extends TexturedGameObject {

    public RectangleObject2D(Vector3f position, Vector3f rotation, Vector3f scale, int sizeX, int sizeY, int sizeZ) {
        super(position, rotation, scale, null);
    }

}
