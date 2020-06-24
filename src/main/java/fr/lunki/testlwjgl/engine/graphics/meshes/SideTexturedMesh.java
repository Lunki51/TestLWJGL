package fr.lunki.testlwjgl.engine.graphics.meshes;

import fr.lunki.testlwjgl.Main;
import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.GameObject;
import fr.lunki.testlwjgl.engine.utils.Enums;
import fr.lunki.testlwjgl.engine.utils.Indice;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.util.HashMap;

public class SideTexturedMesh extends TexturedMesh {

    HashMap<Enums.orientation, Indice> indicesH;

    public SideTexturedMesh(Vector3f[] position, HashMap<Enums.orientation, Indice> indicesH, Vector2f[] textCoord, Material material) {
        this(position, emptyVector3f(1), indicesH, textCoord, material);
    }

    public SideTexturedMesh(Vector3f[] position, Vector3f[] color, HashMap<Enums.orientation, Indice> indicesH, Vector2f[] textCoord, Material material) {
        this(position, color, indicesH, emptyVector3f(1), textCoord, material);
    }

    public SideTexturedMesh(Vector3f[] position, HashMap<Enums.orientation, Indice> indicesH, Vector3f[] normals, Vector2f[] textCoord, Material material) {
        super(position, emptyVector3f(0), normals, new int[0], textCoord, material);
        this.indicesH = indicesH;
    }

    public SideTexturedMesh(Vector3f[] position, Vector3f[] color, HashMap<Enums.orientation, Indice> indicesH, Vector3f[] normals, Vector2f[] textCoord, Material material) {
        super(position, color, normals, new int[0], textCoord, material);
        this.indicesH = indicesH;
    }

    @Override
    public void updateIbo(int[] indices) {
        if (indices != null) {
            IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();

            ibo = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }

    @Override
    public void create() {
        super.create();
    }

    private boolean checkNeighbor(Vector3f neighbor) {
        for (GameObject obj : Main.getManager().getEntityList()) {
            if (obj.getPosition().equals(neighbor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateIndices(Vector3f position) {
        indices = new int[0];
        if (checkNeighbor(new Vector3f(position.getX(), position.getY(), position.getZ() + 1))) {
            if (indicesH.get(Enums.orientation.EAST) != null && indicesH.get(Enums.orientation.EAST).isActivated()) {
                indicesH.get(Enums.orientation.EAST).revert();
            }
        } else {
            if (indicesH.get(Enums.orientation.EAST) != null && !indicesH.get(Enums.orientation.EAST).isActivated()) {
                indicesH.get(Enums.orientation.EAST).revert();
            }
        }
        if (checkNeighbor(new Vector3f(position.getX(), position.getY(), position.getZ() - 1))) {
            if (indicesH.get(Enums.orientation.WEST) != null && indicesH.get(Enums.orientation.WEST).isActivated()) {
                indicesH.get(Enums.orientation.WEST).revert();
            }
        } else {
            if (indicesH.get(Enums.orientation.WEST) != null && !indicesH.get(Enums.orientation.WEST).isActivated()) {
                indicesH.get(Enums.orientation.WEST).revert();
            }
        }
        if (checkNeighbor(new Vector3f(position.getX() - 1, position.getY(), position.getZ()))) {
            if (indicesH.get(Enums.orientation.SOUTH) != null && indicesH.get(Enums.orientation.SOUTH).isActivated()) {
                indicesH.get(Enums.orientation.SOUTH).revert();
            }
        } else {
            if (indicesH.get(Enums.orientation.SOUTH) != null && !indicesH.get(Enums.orientation.SOUTH).isActivated()) {
                indicesH.get(Enums.orientation.SOUTH).revert();
            }
        }
        if (checkNeighbor(new Vector3f(position.getX() + 1, position.getY(), position.getZ()))) {
            if (indicesH.get(Enums.orientation.NORTH) != null && indicesH.get(Enums.orientation.NORTH).isActivated()) {
                indicesH.get(Enums.orientation.NORTH).revert();
            }
        } else {
            if (indicesH.get(Enums.orientation.NORTH) != null && !indicesH.get(Enums.orientation.NORTH).isActivated()) {
                indicesH.get(Enums.orientation.NORTH).revert();
            }
        }
        if (checkNeighbor(new Vector3f(position.getX(), position.getY() + 1, position.getZ()))) {
            if (indicesH.get(Enums.orientation.TOP) != null && indicesH.get(Enums.orientation.TOP).isActivated()) {
                indicesH.get(Enums.orientation.TOP).revert();
            }
        } else {
            if (indicesH.get(Enums.orientation.TOP) != null && !indicesH.get(Enums.orientation.TOP).isActivated()) {
                indicesH.get(Enums.orientation.TOP).revert();
            }
        }
        if (checkNeighbor(new Vector3f(position.getX(), position.getY() - 1, position.getZ()))) {
            if (indicesH.get(Enums.orientation.BOTTOM) != null && indicesH.get(Enums.orientation.BOTTOM).isActivated()) {
                indicesH.get(Enums.orientation.BOTTOM).revert();
            }
        } else {
            if (indicesH.get(Enums.orientation.BOTTOM) != null && !indicesH.get(Enums.orientation.BOTTOM).isActivated()) {
                indicesH.get(Enums.orientation.BOTTOM).revert();
            }
        }

        for (Indice test : indicesH.values()) {
            if (test.isActivated()) {
                int[] tmp = indices.clone();
                indices = new int[indices.length + test.getTab().length];
                int pos = 0;
                for (int i : tmp) {
                    indices[pos] = i;
                    pos++;
                }
                for (int i : test.getTab()) {
                    indices[pos] = i;
                    pos++;
                }
            }
        }

        updateIbo(indices);
    }
}
