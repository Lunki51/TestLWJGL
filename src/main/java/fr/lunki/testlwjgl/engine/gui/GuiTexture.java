package fr.lunki.testlwjgl.engine.gui;

import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;

public class GuiTexture extends TexturedMesh {

    private Vector2f position;
    private Vector2f scale;
    private static final Vector2f[] GUITEXTCOORD = new Vector2f[]{new Vector2f(-1,-1),new Vector2f(1,-1),new Vector2f(1,-1),new Vector2f(1,1)};

    public GuiTexture(Vector2f position, Vector2f scale, Material material) {
        super(getPositions(position,scale), new Vector3f[0], new Vector3f[0], new int[]{0,1,2,3}, GUITEXTCOORD, material);
    }

    public static Vector3f[] getPositions(Vector2f position,Vector2f scale){
        Vector3f[] out = new Vector3f[4];
        out[0] = new Vector3f(position.getX(),position.getY(),0);
        out[1] = new Vector3f(position.getX()+scale.getX(),position.getY(),0);
        out[2] = new Vector3f(position.getX(),position.getY()+position.getY(),0);
        out[3] = new Vector3f(position.getX()+scale.getX(),position.getY()+scale.getY(),0);
        return out;
    }
}
