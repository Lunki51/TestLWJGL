package fr.lunki.testlwjgl.engine.objects.complex;

import fr.lunki.testlwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.GameObject;
import fr.lunki.testlwjgl.engine.utils.Enums;
import fr.lunki.testlwjgl.engine.utils.Indice;

import java.util.HashMap;

public class LineModel extends GameObject {

    public LineModel(Vector3f position, Vector3f rotation, Vector3f scale, RawMesh rawMesh) {
        super(position, rotation, scale, rawMesh);
    }

    public LineModel(Vector3f linevector, int length, Vector3f position, Vector3f color) {
        this(position, new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), new RawMesh(getVertexArray(linevector, length), transformUniqToArray(color, length * 4), getIndices(length)));
    }

    public static Vector3f[] transformUniqToArray(Vector3f uniq, int length) {
        Vector3f[] array = new Vector3f[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = uniq;
        }
        return array;
    }

    public static Vector3f[] getVertexArray(Vector3f vector3f, int length) {
        Vector3f[] vertices = new Vector3f[length * 4];
        vertices[0] = new Vector3f(0.2f, 0.2f, 0);
        vertices[1] = new Vector3f(0.2f, -0.2f, 0);
        vertices[2] = new Vector3f(-0.2f, 0.2f, 0);
        vertices[3] = new Vector3f(-0.2f, -0.2f, 0);
        for (int i = 4; i < vertices.length; i += 4) {
            vertices[i] = Vector3f.add(vertices[i - 4], vector3f);
            vertices[i + 1] = Vector3f.add(vertices[i - 3], vector3f);
            vertices[i + 2] = Vector3f.add(vertices[i - 2], vector3f);
            vertices[i + 3] = Vector3f.add(vertices[i - 1], vector3f);
        }
        return vertices;
    }

    public static int[] getIndices(int length) {
        HashMap<Enums.orientation, Indice> map = new HashMap<Enums.orientation, Indice>();
        int a = length / 3;
        int b = length % 3;
        int[] indices = new int[(a + b) * 3 * 6];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < 3; j++) {
                indices[0 + (i * 18) + j * 6] = 0 + j * 4 + (i * 12);
                indices[1 + (i * 18) + j * 6] = 1 + j * 4 + (i * 12);
                indices[2 + (i * 18) + j * 6] = 2 + j * 4 + (i * 12);
                indices[3 + (i * 18) + j * 6] = 1 + j * 4 + (i * 12);
                indices[4 + (i * 18) + j * 6] = 2 + j * 4 + (i * 12);
                indices[5 + (i * 18) + j * 6] = 3 + j * 4 + (i * 12);
            }

        }
        if (b > 0) {
            /*for(int j=0;j<3;j++){
                indices[0+(i*18)+j*6]=0+j*4+(i*12);
                indices[1+(i*18)+j*6]=1+j*4+(i*12);
                indices[2+(i*18)+j*6]=2+j*4+(i*12);
                indices[3+(i*18)+j*6]=1+j*4+(i*12);
                indices[4+(i*18)+j*6]=2+j*4+(i*12);
                indices[5+(i*18)+j*6]=3+j*4+(i*12);
            }*/
        }
        return indices;
    }

}
