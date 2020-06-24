package fr.lunki.testlwjgl.engine.graphics.render;

import fr.lunki.testlwjgl.engine.graphics.Shader;
import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.testlwjgl.engine.io.Window;
import fr.lunki.testlwjgl.engine.maths.Matrix4f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.maths.Vector4f;
import fr.lunki.testlwjgl.engine.objects.GameObject;
import fr.lunki.testlwjgl.engine.objects.Light;
import fr.lunki.testlwjgl.engine.objects.player.Camera;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;

public abstract class EntityRenderer extends MasterRenderer<HashMap<RawMesh, List<GameObject>>> {


    public EntityRenderer(Window window, Shader shader) {
        super(window, shader);
    }

    @Override
    public void render(HashMap<RawMesh, List<GameObject>> toRender, Camera camera, Light light) {
        {
            shader.bind();
            for (RawMesh mesh : toRender.keySet()) {
                prepareMesh(mesh, camera, light);
                List<GameObject> batch = toRender.get(mesh);
                for (GameObject object : batch) {
                    Vector4f worldPosition = Matrix4f.multiply(Matrix4f.transform(object.getPosition(),object.getRotation(),object.getScale()),new Vector4f(0,0,0,1.0f));
                    Vector4f positionToCam = Matrix4f.multiply(Matrix4f.view(camera.getPosition(),camera.getRotation()),worldPosition);
                    float distanceToCamera = Vector3f.length(new Vector3f(positionToCam.getX(),positionToCam.getY(),positionToCam.getZ()));

                    if(distanceToCamera<50){
                        renderObject(object);
                        GL11.glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
                    }

                }
                unbindMesh();
            }
            shader.unbind();
            toRender.clear();
        }
    }

    protected abstract void prepareMesh(RawMesh mesh, Camera camera, Light light);

    protected abstract void renderObject(GameObject object);

    protected abstract void unbindMesh();
}
