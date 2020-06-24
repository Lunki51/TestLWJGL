package fr.lunki.testlwjgl.engine.objects.player;

import fr.lunki.testlwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.GameObject;
import fr.lunki.testlwjgl.engine.objects.TexturedGameObject;

public abstract class Playable extends TexturedGameObject {

    protected float headhight = 0;
    protected Vector3f currentSpeed;
    protected Vector3f currentRotation;
    protected boolean isCrouching;

    public Playable(Vector3f position, Vector3f rotation, Vector3f scale, TexturedMesh mesh) {
        super(position, rotation, scale, mesh);
    }

    public float getHeadhight() {
        return headhight;
    }

    public Vector3f getCurrentSpeed() {
        return currentSpeed;
    }

    public Vector3f getCurrentRotation() {
        return currentRotation;
    }

    public abstract void move();

    public boolean isCrouching() {
        return isCrouching;
    }
}
