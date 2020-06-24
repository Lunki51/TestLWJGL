package fr.lunki.testlwjgl.engine.graphics.render;

import fr.lunki.testlwjgl.engine.graphics.Shader;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.io.Window;
import fr.lunki.testlwjgl.engine.maths.Matrix4f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.Light;
import fr.lunki.testlwjgl.engine.objects.player.Camera;
import fr.lunki.testlwjgl.engine.terrain.Terrain;
import fr.lunki.testlwjgl.engine.terrain.TerrainTextures;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class TerrainRenderer extends MasterRenderer<List<Terrain>> {

    public TerrainRenderer(Window window, Shader shader) {
        super(window, shader);
    }

    @Override
    public void render(List<Terrain> toRender, Camera camera, Light light) {
        shader.bind();
        for(Terrain terrain : toRender){
            prepareTerrain(terrain.getMesh(),camera,light,terrain);
            renderTerrain(terrain);
            unbindTerrain();
        }
        shader.unbind();
    }

    @Override
    public void create() {
        super.create();
        shader.setUniform("backgroundTexture",0);
        shader.setUniform("rTexture",1);
        shader.setUniform("gTexture",2);
        shader.setUniform("bTexture",3);
        shader.setUniform("blendMap",4);
    }

    protected void prepareTerrain(TexturedMesh mesh, Camera camera, Light light, Terrain terrain) {
        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL30.glEnableVertexAttribArray(3);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        create();
        bindTextures(terrain);
        shader.setUniform("shineDamper", mesh.getMaterial().getShineDamper());
        shader.setUniform("reflectivity", mesh.getMaterial().getReflectivity());
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjection());
        shader.setUniform("lightPosition", light.getPosition());
        shader.setUniform("lightColour", light.getColour());
        shader.setUniform("skyColour",window.getBackground());
    }

    private void bindTextures(Terrain terrain){
        TerrainTextures textures = terrain.getTerrainTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getFont().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getR().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getG().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getB().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL13.glBindTexture(GL_TEXTURE_2D, textures.getMap().getTextureID());
    }

    protected void renderTerrain(Terrain terrain) {
        shader.setUniform("model", Matrix4f.transform(new Vector3f(terrain.getX(),0,terrain.getZ()), new Vector3f(0,0,0), new Vector3f(1,1,1)));
        GL11.glDrawElements(GL_TRIANGLES, terrain.getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
    }

    protected void unbindTerrain() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);
    }
}
