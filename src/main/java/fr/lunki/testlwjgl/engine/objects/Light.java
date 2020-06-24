package fr.lunki.testlwjgl.engine.objects;

import fr.lunki.testlwjgl.engine.maths.Vector3f;

public class Light {
    private Vector3f position;
    private Vector3f colour;

    public Light(Vector3f postion, Vector3f colour) {
        this.position = postion;
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
