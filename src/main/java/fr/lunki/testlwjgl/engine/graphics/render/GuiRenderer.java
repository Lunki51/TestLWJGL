package fr.lunki.testlwjgl.engine.graphics.render;

import fr.lunki.testlwjgl.engine.graphics.Shader;
import fr.lunki.testlwjgl.engine.gui.GuiTexture;
import fr.lunki.testlwjgl.engine.io.Window;
import fr.lunki.testlwjgl.engine.objects.Light;
import fr.lunki.testlwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL13;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class GuiRenderer extends MasterRenderer<List<GuiTexture>> {

    public GuiRenderer(Window window, Shader shader) {
        super(window, shader);
    }

    @Override
    public void render(List<GuiTexture> toRender, Camera camera, Light light) {
        shader.bind();
        for(GuiTexture mesh : toRender){
            enableCulling();
            glBindVertexArray(mesh.getVAO());
            glEnableVertexAttribArray(0);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL13.glBindTexture(GL_TEXTURE_2D, mesh.getMaterial().getTexture().getTextureID());
            glDrawArrays(GL_TRIANGLE_STRIP,0,mesh.getIndices().length);
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
            disableCulling();
        }
        shader.unbind();
    }
}
