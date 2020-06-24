package fr.lunki.testlwjgl.engine.graphics.render;

import fr.lunki.testlwjgl.engine.graphics.Shader;
import fr.lunki.testlwjgl.engine.io.Window;
import fr.lunki.testlwjgl.engine.objects.Light;
import fr.lunki.testlwjgl.engine.objects.player.Camera;
import org.lwjgl.opengl.GL11;

public abstract class MasterRenderer<T> {

    protected Window window;
    protected Shader shader;

    public MasterRenderer(Window window, Shader shader) {
        this.window = window;
        this.shader = shader;
    }

    public void create(){
        enableCulling();
    }

    public void enableCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public abstract void render(T toRender, Camera camera, Light light);
}
