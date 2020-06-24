package fr.lunki.testlwjgl.engine.io;

import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.graphics.material.Texture;
import fr.lunki.testlwjgl.engine.graphics.meshes.AtlasTexturedMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import sun.awt.image.ByteArrayImageSource;
import sun.awt.image.InputStreamImageSource;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.assimp.Assimp.*;

public class ModelLoader {

    public static TexturedMesh[] readModelFile(String filename, String textureDir) {
        return readModelFile(filename, textureDir, aiProcess_Triangulate | aiProcess_FixInfacingNormals);
    }

    public static TexturedMesh[] readModelFile(String filename){
        return readModelFile(filename,null,aiProcess_Triangulate | aiProcess_FixInfacingNormals);
    }

    public static TexturedMesh[] readModelFile(String filename, String dataPath, int flags) {
        AIScene fileScene = aiImportFile("res/models/" + filename, flags);
        if (fileScene == null) System.err.println("Erreur impossible de lire le fichier " + "res/models/" +filename);
        PointerBuffer aiMats = fileScene.mMaterials();

        ArrayList<Material> mats = new ArrayList<>();
        ArrayList<Vector3f> colors = new ArrayList<>();
        float reflectivity = 0.1f;
        float shininess = 1f;

        while (aiMats.remaining() > 0) {
            AIMaterial material = AIMaterial.create(aiMats.get());

            PointerBuffer properties = material.mProperties();
            while (properties.hasRemaining()) {
                AIMaterialProperty property = AIMaterialProperty.create(properties.get());
                if (property.mKey().dataString().equals("$mat.reflectivity")) {
                    ByteBuffer buffer1 = property.mData();
                    reflectivity = buffer1.getFloat();
                }
                if (property.mKey().dataString().equals("$mat.shininess")) {
                    ByteBuffer buffer1 = property.mData();
                    shininess = buffer1.getFloat();
                }
            }
            reflectivity = 0.1f;
            shininess = 0.1f;
            mats.add(processMaterial(fileScene, material, dataPath, filename, reflectivity, shininess));

            AIColor4D colors4D = AIColor4D.calloc();
            aiGetMaterialColor(material, AI_MATKEY_COLOR_DIFFUSE, 0, 0, colors4D);
            colors.add(new Vector3f(colors4D.r(),colors4D.g(),colors4D.b()));
        }

        PointerBuffer aiMeshes = fileScene.mMeshes();

        TexturedMesh[] texturedMeshes = new TexturedMesh[fileScene.mNumMeshes()];

        for (int i = 0; i < texturedMeshes.length; i++) {
            AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));

            Vector3f[] position = processPosition(aiMesh);
            Vector3f[] color = new Vector3f[]{colors.get(aiMesh.mMaterialIndex())};
            Vector3f[] normals = processNormals(aiMesh);
            int[] indices = processIndices(aiMesh);
            Vector2f[] textCoord = processTextCoords(aiMesh);
            Material material = mats.get(aiMesh.mMaterialIndex());

            texturedMeshes[i] = new TexturedMesh(position, color, normals, indices, textCoord, material);

        }
        return texturedMeshes;
    }

    public static Vector3f[] processNormals(AIMesh aiMesh) {
        AIVector3D.Buffer buffer = aiMesh.mNormals();
        if(buffer==null)return new Vector3f[]{new Vector3f(1,1,1)};
        Vector3f[] normals = new Vector3f[buffer.remaining()];
        for (int i = 0; buffer.remaining() > 0; i++) {
            AIVector3D vector3D = buffer.get();
            normals[i] = new Vector3f(vector3D.x(), vector3D.y(), vector3D.z());
        }
        return normals;
    }

    public static Vector2f[] processTextCoords(AIMesh aiMesh) {
        AIVector3D.Buffer aiTextures = aiMesh.mTextureCoords(0);

        ArrayList<Vector2f> textCorrd = new ArrayList<>();
        int numTextCoords = aiTextures != null ? aiTextures.remaining() : 0;
        for (int i = 0; i < numTextCoords; i++) {
            AIVector3D textCoord = aiTextures.get();
            textCorrd.add(new Vector2f(textCoord.x(), 1f - textCoord.y()));
        }
        return textCorrd.toArray(new Vector2f[textCorrd.size()]);
    }

    public static Vector3f[] processPosition(AIMesh aiMesh) {
        AIVector3D.Buffer aiVertices = aiMesh.mVertices();
        Vector3f[] vertices = new Vector3f[aiVertices.remaining()];
        for (int i = 0; aiVertices.remaining() > 0; i++) {
            AIVector3D aiVertex = aiVertices.get();
            vertices[i] = new Vector3f(aiVertex.x(), aiVertex.y(), aiVertex.z());
        }
        return vertices;
    }

    public static int[] processIndices(AIMesh aiMesh) {
        int numFaces = aiMesh.mNumFaces();
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        int[] indices = new int[numFaces * 3];
        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            for (int j = 0; buffer.remaining() > 0; j++) {
                indices[j + i * 3] = buffer.get();
            }
        }
        return indices;
    }

    public static Material processMaterial(AIScene fileScene, AIMaterial aiMaterial, String dataPath, String fileName, float reflectivity, float shininess) {
        AIString path = AIString.calloc();
        aiGetMaterialTexture(aiMaterial, aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
        AITexture aiTexture;
        if (!path.dataString().contains("*")) {
            if (path.dataString().isEmpty()) {
                return new Material(shininess,reflectivity,new Texture(dataPath+fileName.split("\\.")[0]+".png"));
            }
            return new Material(shininess,reflectivity,new Texture(dataPath+path.dataString()));
        } else {
            aiTexture = AITexture.create(fileScene.mTextures().get(Integer.valueOf((path.dataString().split(".fbm")[1]).substring(1))));
            if (aiTexture.mHeight() == 0) {
                AITexel.Buffer buffer = aiTexture.pcData(aiTexture.mWidth());
                AIString stringImage = AIString.create(buffer.address());
                ByteBuffer byteBuffer = stringImage.data();
                byte[] imageData = new byte[aiTexture.mWidth()];

                for (int i = 0; i < aiTexture.mWidth(); i++) {
                    byte b = byteBuffer.get(aiTexture.mWidth() - i);
                    imageData[i] = b;
                }
                InputStream stream = new ByteArrayInputStream(imageData);

                try {

                    FileImageOutputStream out = new FileImageOutputStream(new File("out.png"));
                    InputStreamImageSource imageSource = new ByteArrayImageSource(imageData);
                    while (stream.available() > 0) {
                        out.writeBit(stream.read());
                    }
                    BufferedImage image = ImageIO.read(stream);
                    //return new Texture(image,"image");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                AITexel.Buffer buffer = aiTexture.pcData(aiTexture.mWidth());
                byte[] imageData = AIString.create(buffer.address()).data().array();
                InputStream stream = new ByteArrayInputStream(imageData);
                try {
                    BufferedImage image = ImageIO.read(stream);
                    //return new Texture(image,"PNG");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

}
