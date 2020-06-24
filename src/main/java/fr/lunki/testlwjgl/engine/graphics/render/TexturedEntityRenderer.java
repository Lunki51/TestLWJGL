package fr.lunki.testlwjgl.engine.graphics.render;

import fr.lunki.testlwjgl.engine.graphics.Shader;
import fr.lunki.testlwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.io.Window;
import fr.lunki.testlwjgl.engine.maths.Matrix4f;
import fr.lunki.testlwjgl.engine.objects.GameObject;
import fr.lunki.testlwjgl.engine.objects.Light;
import fr.lunki.testlwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;

public class TexturedEntityRenderer extends EntityRenderer {

    public TexturedEntityRenderer(Window window, Shader shader) {
        super(window, shader);
    }

    @Override
    protected void prepareMesh(RawMesh mesh, Camera camera, Light light) {
        TexturedMesh texturedMesh = (TexturedMesh) mesh;
        if(texturedMesh.getMaterial().isTransparent()){
            disableCulling();
        }
        shader.bind();
        GL30.glBindVertexArray(texturedMesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL30.glEnableVertexAttribArray(3);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, texturedMesh.getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL_TEXTURE_2D, texturedMesh.getMaterial().getTexture().getTextureID());
        shader.setUniform("shineDamper", texturedMesh.getMaterial().getShineDamper());
        shader.setUniform("reflectivity", texturedMesh.getMaterial().getReflectivity());
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjection());
        shader.setUniform("lightPosition", light.getPosition());
        shader.setUniform("lightColour", light.getColour());
        shader.setUniform("useFakeLighting",texturedMesh.getMaterial().isUsingFakeLighting());
        shader.setUniform("skyColour",window.getBackground());
    }



    @Override
    protected void renderObject(GameObject object) {
        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
    }

    @Override
    protected void unbindMesh() {
        enableCulling();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);
        shader.unbind();
    }

}
