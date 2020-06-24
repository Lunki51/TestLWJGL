package fr.lunki.testlwjgl.engine.graphics.render;

import fr.lunki.testlwjgl.engine.graphics.Shader;
import fr.lunki.testlwjgl.engine.graphics.material.AtlasMaterial;
import fr.lunki.testlwjgl.engine.graphics.meshes.AtlasTexturedMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.testlwjgl.engine.io.Window;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.objects.AtlasTexturedGameObject;
import fr.lunki.testlwjgl.engine.objects.GameObject;
import fr.lunki.testlwjgl.engine.objects.Light;
import fr.lunki.testlwjgl.engine.objects.player.Camera;

public class AtlasTexturedEntityRenderer extends TexturedEntityRenderer {
    public AtlasTexturedEntityRenderer(Window window, Shader shader) {
        super(window, shader);
    }

    @Override
    protected void prepareMesh(RawMesh mesh, Camera camera, Light light) {
        super.prepareMesh(mesh, camera, light);
        shader.setUniform("numberOfRows",(float)((AtlasMaterial)((AtlasTexturedMesh)mesh).getMaterial()).getAtlasSize());
    }

    @Override
    protected void renderObject(GameObject object) {
        super.renderObject(object);
        AtlasTexturedGameObject atlasTexturedGameObject = (AtlasTexturedGameObject) object;
        shader.setUniform("offset",new Vector2f(atlasTexturedGameObject.getTextureXOffset(),atlasTexturedGameObject.getTextureYOffset()));
    }
}
