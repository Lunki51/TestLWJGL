package fr.lunki.testlwjgl.engine.objects.complex;

import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.graphics.meshes.SideTexturedMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.primitives.BlockObject2D;
import fr.lunki.testlwjgl.engine.utils.Enums;

public class MinecraftBlock {
    BlockObject2D[] sides = new BlockObject2D[6];

    public MinecraftBlock(Vector3f position, Vector3f rotation, Vector3f scale, Material topMat,Material bottomMat,Material sidesMat){
        sides[0] = new BlockObject2D(new Vector3f(position.getX()-0.5f,position.getY(),position.getZ()),rotation,scale,sidesMat, Enums.orientation.EAST);
        sides[1] = new BlockObject2D(new Vector3f(position.getX()+0.5f,position.getY(),position.getZ()),rotation,scale,sidesMat, Enums.orientation.WEST);
        sides[2] = new BlockObject2D(new Vector3f(position.getX(),position.getY(),position.getZ()-0.5f),rotation,scale,sidesMat, Enums.orientation.NORTH);
        sides[3] = new BlockObject2D(new Vector3f(position.getX(),position.getY(),position.getZ()+0.5f),rotation,scale,sidesMat, Enums.orientation.SOUTH);

        sides[4] = new BlockObject2D(new Vector3f(position.getX(),position.getY()+0.5f,position.getZ()),rotation,scale,topMat, Enums.orientation.TOP);

        sides[5] = new BlockObject2D(new Vector3f(position.getX(),position.getY()-0.5f,position.getZ()),rotation,scale,bottomMat, Enums.orientation.BOTTOM);
    }

    public TexturedMesh[] getMeshes(){
        TexturedMesh[] meshes = new TexturedMesh[6];
        for(int i=0;i<sides.length;i++){
            meshes[i]=sides[i].getMesh();
        }
        return meshes;
    }

    public BlockObject2D[] getSides() {
        return sides;
    }
}
