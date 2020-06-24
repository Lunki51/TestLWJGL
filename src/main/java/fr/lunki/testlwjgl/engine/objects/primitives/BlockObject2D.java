package fr.lunki.testlwjgl.engine.objects.primitives;

import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.TexturedGameObject;
import fr.lunki.testlwjgl.engine.utils.Enums;

public class BlockObject2D extends TexturedGameObject {

    public BlockObject2D(Vector3f position, Vector3f rotation, Vector3f scale, Material material, Enums.orientation side) {
        super(position, rotation, scale, new TexturedMesh(getPositionFromSide(side), new int[]{0, 1, 2, 1, 2, 3}, getTextCoordFromSide(side), material));
    }

    public static Vector3f[] getPositionFromSide(Enums.orientation side) {
        switch (side) {
            case SOUTH:
            case NORTH:
                return new Vector3f[]{
                        new Vector3f(0, -0.5f, -0.5f),
                        new Vector3f(0, -0.5f, 0.5f),
                        new Vector3f(0, 0.5f, -0.5f),
                        new Vector3f(0, 0.5f, 0.5f)
                };
            case WEST:
            case EAST:
                return new Vector3f[]{
                        new Vector3f(-0.5f, -0.5f, 0),
                        new Vector3f(-0.5f, 0.5f, 0),
                        new Vector3f(0.5f, -0.5f, 0),
                        new Vector3f(0.5f, 0.5f, 0)
                };
            case TOP:
            case BOTTOM:
                return new Vector3f[]{
                        new Vector3f(0.5f, 0, -0.5f),
                        new Vector3f(0.5f, 0, 0.5f),
                        new Vector3f(-0.5f, 0, -0.5f),
                        new Vector3f(-0.5f, 0, 0.5f)
                };
        }
        return null;
    }

    public static Vector2f[] getTextCoordFromSide(Enums.orientation side) {
        switch (side) {
            case SOUTH:
            case NORTH:
                return new Vector2f[]{
                        new Vector2f(0, 1),
                        new Vector2f(1, 1),
                        new Vector2f(0, 0),
                        new Vector2f(1, 0)
                };
            case WEST:
            case EAST:
                return new Vector2f[]{
                        new Vector2f(1, 1),
                        new Vector2f(1, 0),
                        new Vector2f(0, 1),
                        new Vector2f(0, 0)
                };
            case TOP:
            case BOTTOM:
                return new Vector2f[]{
                        new Vector2f(0, 0),
                        new Vector2f(0, 0),
                        new Vector2f(1, 0),
                        new Vector2f(1, 0)
                };
        }
        return null;
    }

}
