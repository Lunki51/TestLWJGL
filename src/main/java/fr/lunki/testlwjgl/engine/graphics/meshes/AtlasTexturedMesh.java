package fr.lunki.testlwjgl.engine.graphics.meshes;

import fr.lunki.testlwjgl.engine.graphics.material.AtlasMaterial;
import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;

public class AtlasTexturedMesh extends TexturedMesh{

    public AtlasTexturedMesh(TexturedMesh mesh,int atlasSize){
        this(mesh.position,mesh.colors,mesh.normals,mesh.indices,mesh.textureCoord,new AtlasMaterial(mesh.material,atlasSize));
    }

    public AtlasTexturedMesh(Vector3f[] position, int[] indices, Vector2f[] textureCoord, AtlasMaterial material) {
        super(position, indices, textureCoord, material);
    }

    public AtlasTexturedMesh(Vector3f[] position, Vector3f[] colors, int[] indices, Vector2f[] textureCoord, AtlasMaterial material) {
        super(position, colors, indices, textureCoord, material);
    }

    public AtlasTexturedMesh(Vector3f[] position, int[] indices, Vector3f[] normals, Vector2f[] textureCoord, AtlasMaterial material) {
        super(position, indices, normals, textureCoord, material);
    }

    public AtlasTexturedMesh(Vector3f[] position, Vector3f[] colors, Vector3f[] normals, int[] indices, Vector2f[] textureCoord, AtlasMaterial material) {
        super(position, colors, normals, indices, textureCoord, material);
    }
}
