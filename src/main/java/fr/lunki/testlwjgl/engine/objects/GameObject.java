package fr.lunki.testlwjgl.engine.objects;

import fr.lunki.testlwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.testlwjgl.engine.maths.Vector3f;

public abstract class GameObject extends AbstractGameObject{
    private Vector3f position, rotation, scale;
    protected RawMesh mesh;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, RawMesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public RawMesh getMesh() {
        return mesh;
    }

    public void createMesh() {
        RawMesh mesh = getMesh();
        mesh.create();
        mesh.updateIndices(position);
    }

    public void updateMesh() {

    }


    public void destroyMesh() {
        getMesh().destroy();
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
