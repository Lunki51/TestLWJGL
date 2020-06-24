package fr.lunki.testlwjgl.engine.graphics.meshes;

import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class TexturedMesh extends RawMesh {
    protected Vector2f[] textureCoord;
    protected Material material;

    public TexturedMesh(Vector3f[] position, int[] indices, Vector2f[] textureCoord, Material material) {
        this(position, emptyVector3f(position.length), indices, textureCoord, material);
    }

    public TexturedMesh(Vector3f[] position, Vector3f[] colors, int[] indices, Vector2f[] textureCoord, Material material) {
        this(position, colors, emptyVector3f(position.length), indices, textureCoord, material);
    }

    public TexturedMesh(Vector3f[] position, int[] indices, Vector3f[] normals, Vector2f[] textureCoord, Material material) {
        this(position, emptyVector3f(position.length), normals, indices, textureCoord, material);
    }

    public TexturedMesh(Vector3f[] position, Vector3f[] colors, Vector3f[] normals, int[] indices, Vector2f[] textureCoord, Material material) {
        super(position, colors, normals, indices);
        this.textureCoord = textureCoord;
        this.material = material;
    }

    public static Vector3f[] emptyVector3f(int size) {
        Vector3f[] vector3fs = new Vector3f[size];
        for (int i = 0; i < size; i++) {
            vector3fs[i] = new Vector3f();
        }
        return vector3fs;
    }

    public static Vector2f[] emptyVector2f(int size) {
        Vector2f[] vector2f = new Vector2f[size];
        for (int i = 0; i < size; i++) {
            vector2f[i] = new Vector2f();
        }
        return vector2f;
    }


    public void create() {
        if(!isCreated()){
            material.create();
            vao = GL30.glGenVertexArrays();
            GL30.glBindVertexArray(vao);

            //Position buffer
            generateVBO(position);
            generateVBO(colors);
            generateVBO(textureCoord);
            generateVBO(normals);

            //------------------------
            updateIbo(indices);
            setCreated();
        }
    }

    public void generateVBO(Vector3f[] data) {
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(data.length * 3);
        float[] positionData = new float[data.length * 3];
        for (int i = 0; i < data.length; i++) {
            positionData[i * 3] = data[i].getX();
            positionData[i * 3 + 1] = data[i].getY();
            positionData[i * 3 + 2] = data[i].getZ();
        }
        positionBuffer.put(positionData).flip();

        vbos.add(storeData(positionBuffer, vbos.size(), 3));
    }

    public void generateVBO(Vector2f[] data) {
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(data.length * 2);
        float[] positionData = new float[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            positionData[i * 2] = data[i].getX();
            positionData[i * 2 + 1] = data[i].getY();
        }
        positionBuffer.put(positionData).flip();

        vbos.add(storeData(positionBuffer, vbos.size(), 2));
    }


    public void updateIbo(int[] indices) {
        if (indices != null) {
            ibo = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }

    public void updateIndices(Vector3f position) {
    }


    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    public void destroy() {
        material.destroy();
        for (int i : vbos) {
            GL15.glDeleteBuffers(i);
        }

        GL30.glDeleteVertexArrays(vao);
    }


    public int[] getIndices() {
        return indices;
    }

    public int getIBO() {
        return ibo;
    }

    public Material getMaterial() {
        return material;
    }

    public int getVAO() {
        return vao;
    }


}
