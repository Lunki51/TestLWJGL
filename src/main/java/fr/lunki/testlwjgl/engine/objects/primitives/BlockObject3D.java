package fr.lunki.testlwjgl.engine.objects.primitives;

import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.graphics.meshes.SideTexturedMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.TexturedGameObject;
import fr.lunki.testlwjgl.engine.utils.Enums;
import fr.lunki.testlwjgl.engine.utils.Indice;

import java.util.HashMap;

public class BlockObject3D extends TexturedGameObject {

    public BlockObject3D(Material allfaces, Vector3f position) {
        super(position, new Vector3f(0, 0, 0), new Vector3f(1, 1, 1),
                new SideTexturedMesh(
                        new Vector3f[]{
                                new Vector3f(-0.5f, -0.5f, -0.5f),
                                new Vector3f(-0.5f, -0.5f, 0.5f),
                                new Vector3f(-0.5f, 0.5f, -0.5f),
                                new Vector3f(-0.5f, 0.5f, 0.5f),

                                new Vector3f(0.5f, -0.5f, -0.5f),
                                new Vector3f(0.5f, -0.5f, 0.5f),
                                new Vector3f(0.5f, 0.5f, -0.5f),
                                new Vector3f(0.5f, 0.5f, 0.5f),

                                new Vector3f(-0.5f, -0.5f, -0.5f),
                                new Vector3f(-0.5f, 0.5f, -0.5f),
                                new Vector3f(0.5f, -0.5f, -0.5f),
                                new Vector3f(0.5f, 0.5f, -0.5f),

                                new Vector3f(-0.5f, -0.5f, 0.5f),
                                new Vector3f(-0.5f, 0.5f, 0.5f),
                                new Vector3f(0.5f, -0.5f, 0.5f),
                                new Vector3f(0.5f, 0.5f, 0.5f),
                        },
                        getIndices(Enums.sides.FULL),
                        new Vector3f[]{
                                new Vector3f(-1, 0, 0),
                                new Vector3f(-1, 0, 0),
                                new Vector3f(-1, 0, 0),
                                new Vector3f(-1, 0, 0),

                                new Vector3f(1, 0, 0),
                                new Vector3f(1, 0, 0),
                                new Vector3f(1, 0, 0),
                                new Vector3f(1, 0, 0),

                                new Vector3f(0, 0, -1),
                                new Vector3f(0, 0, -1),
                                new Vector3f(0, 0, -1),
                                new Vector3f(0, 0, -1),

                                new Vector3f(0, 0, 1),
                                new Vector3f(0, 0, 1),
                                new Vector3f(0, 0, 1),
                                new Vector3f(0, 0, 1),

                        },
                        new Vector2f[]{
                                new Vector2f(0, 1),
                                new Vector2f(1, 1),
                                new Vector2f(0, 0),
                                new Vector2f(1, 0)
                        },
                        allfaces));
    }

    @Override
    public void updateMesh() {
        getMesh().updateIndices(getPosition());
    }

    @Override
    public void createMesh() {
        getMesh().create();
        getMesh().updateIndices(getPosition());
    }

    public static HashMap<Enums.orientation, Indice> getIndices(Enums.sides full) {
        HashMap<Enums.orientation, Indice> indices = new HashMap<Enums.orientation, Indice>();
        switch (full) {
            case FULL:
                indices.put(Enums.orientation.SOUTH, new Indice(new int[]{0, 1, 2, 1, 2, 3}));
                indices.put(Enums.orientation.NORTH, new Indice(new int[]{4, 5, 6, 5, 6, 7}));
                indices.put(Enums.orientation.WEST, new Indice(new int[]{8, 9, 10, 9, 10, 11}));
                indices.put(Enums.orientation.EAST, new Indice(new int[]{12, 13, 14, 13, 14, 15}));
                indices.put(Enums.orientation.TOP, new Indice(new int[]{11, 15, 9, 15, 9, 13}));
                indices.put(Enums.orientation.BOTTOM, new Indice(new int[]{10, 14, 8, 14, 8, 1}));
                break;
            case SIDES:
                indices.put(Enums.orientation.SOUTH, new Indice(new int[]{0, 1, 2, 1, 2, 3}));
                indices.put(Enums.orientation.NORTH, new Indice(new int[]{4, 5, 6, 5, 6, 7}));
                indices.put(Enums.orientation.WEST, new Indice(new int[]{8, 9, 10, 9, 10, 11}));
                indices.put(Enums.orientation.EAST, new Indice(new int[]{12, 13, 14, 13, 14, 15}));
                break;
            case TOP:
                indices.put(Enums.orientation.TOP, new Indice(new int[]{0, 1, 2, 1, 2, 3}));
                break;
            case BOTTOM:
                indices.put(Enums.orientation.BOTTOM, new Indice(new int[]{0, 1, 2, 1, 2, 3}));
                break;
        }
        return indices;
    }


}
