package fr.lunki.testlwjgl;

import fr.lunki.testlwjgl.engine.graphics.Shader;
import fr.lunki.testlwjgl.engine.graphics.material.Material;
import fr.lunki.testlwjgl.engine.graphics.material.Texture;
import fr.lunki.testlwjgl.engine.graphics.meshes.AtlasTexturedMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.RawMesh;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.graphics.render.*;
import fr.lunki.testlwjgl.engine.gui.GuiTexture;
import fr.lunki.testlwjgl.engine.io.Input;
import fr.lunki.testlwjgl.engine.io.ModelLoader;
import fr.lunki.testlwjgl.engine.io.Window;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.AtlasTexturedGameObject;
import fr.lunki.testlwjgl.engine.objects.GameObject;
import fr.lunki.testlwjgl.engine.objects.Light;
import fr.lunki.testlwjgl.engine.objects.TexturedGameObject;
import fr.lunki.testlwjgl.engine.objects.complex.AtlasTexturedLoadedModel;
import fr.lunki.testlwjgl.engine.objects.complex.LineModel;
import fr.lunki.testlwjgl.engine.objects.complex.MinecraftBlock;
import fr.lunki.testlwjgl.engine.objects.complex.TexturedLoadedModel;
import fr.lunki.testlwjgl.engine.objects.player.Camera;
import fr.lunki.testlwjgl.engine.objects.primitives.BlockObject2D;
import fr.lunki.testlwjgl.engine.terrain.Terrain;
import fr.lunki.testlwjgl.engine.terrain.TerrainTextures;
import javafx.scene.media.AudioClip;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RenderManager {

    public Light light;
    public Camera camera;
    public Window window;
    public ArrayList<GameObject> entityList;
    public TexturedEntityRenderer texturedEntityRenderer;
    public RawEntityRenderer rawEntityRenderer;
    public TerrainRenderer terrainRenderer;
    public AtlasTexturedEntityRenderer atlasTexturedEntityRenderer;
    public GuiRenderer guiRenderer;
    public static ArrayList<Terrain> terrains;
    public ArrayList<GuiTexture> guis;
    public static Shader textureShader = new Shader("/shaders/mainVertex.glsl", "/shaders/textureFragment.glsl");
    public static Shader colorShader = new Shader("/shaders/mainVertex.glsl", "/shaders/colorFragment.glsl");
    public static Shader blockShader = new Shader("/shaders/mainVertex.glsl", "/shaders/blockFragment.glsl");
    public static Shader atlasShader = new Shader("/shaders/atlasVertex.glsl", "/shaders/textureFragment.glsl");
    public static Shader terrainShader = new Shader("/shaders/terrainVertex.glsl", "/shaders/terrainFragment.glsl");
    public static Shader guiShader = new Shader("/shaders/guiVertex.glsl", "/shaders/guiFragment.glsl");


    public RenderManager(Window window, Camera camera, Light light) {
        this.camera = camera;
        this.light = light;
        this.entityList = new ArrayList<>();
        this.window = window;
        this.terrains = new ArrayList<>();
        this.guis = new ArrayList<>();
        this.guiRenderer = new GuiRenderer(window,guiShader);
        this.texturedEntityRenderer = new TexturedEntityRenderer(window, textureShader);
        this.rawEntityRenderer = new RawEntityRenderer(window, colorShader);
        this.terrainRenderer = new TerrainRenderer(window, terrainShader);
        this.atlasTexturedEntityRenderer= new AtlasTexturedEntityRenderer(window,atlasShader);
    }

    public void create() {
        textureShader.create();
        blockShader.create();
        colorShader.create();
        terrainShader.create();
        atlasShader.create();
        guiShader.create();

        texturedEntityRenderer.create();
        rawEntityRenderer.create();
        terrainRenderer.create();
        atlasTexturedEntityRenderer.create();
        guiRenderer.create();
        //setZenLinitie();
        addEntities();
        Runnable creator = new Runnable() {
            @Override
            public void run() {
                if (!GLFW.glfwInit()) {
                    System.err.println("ERROR: GLFW wasn't initializied");
                    return;
                }
                GLFW.glfwMakeContextCurrent(window.getWindow());
                GL.createCapabilities();
                GL11.glEnable(GL11.GL_DEPTH_TEST);

                window.createCallbacks();
                GLFW.glfwSwapInterval(1);

            }
        };
        Thread t = new Thread(creator);

        for (GameObject object : entityList) {
            object.createMesh();
        }
        for (Terrain terrain : terrains) {
            terrain.create();
        }
        for(GuiTexture texture : guis){
            texture.create();
        }

    }

    int sunRotation = 0;

    public void render() {
        //sunRotation+=1;
        if (sunRotation > 720) sunRotation = 0;
        //light.getPosition().add((float)Math.cos(Math.toRadians(sunRotation))*3000,(float)Math.sin(Math.toRadians(sunRotation))*3000,0);
        if (Input.isKeyPressed(GLFW.GLFW_KEY_H)) light.setPosition(camera.getPosition());

        HashMap<RawMesh, List<GameObject>> map = new HashMap<>();
        HashMap<RawMesh, List<GameObject>> texturedMap = new HashMap<>();
        HashMap<RawMesh, List<GameObject>> atlastexturedMap = new HashMap<>();
        for (GameObject object : this.entityList) {
            if (Math.abs(Vector3f.length(Vector3f.subtract(camera.getPosition(), object.getPosition()))) != 5001) {
                if(object instanceof AtlasTexturedGameObject){
                    AtlasTexturedMesh mesh = ((AtlasTexturedGameObject) object).getMesh();
                    if (atlastexturedMap.keySet().contains(mesh)) {
                        atlastexturedMap.get(mesh).add(object);
                    } else {
                        List<GameObject> objects = new ArrayList<>();
                        objects.add(object);
                        atlastexturedMap.put(mesh, objects);
                    }
                }
                else if (object instanceof TexturedGameObject) {
                    TexturedMesh mesh = ((TexturedGameObject) object).getMesh();
                    if (texturedMap.keySet().contains(mesh)) {
                        texturedMap.get(mesh).add(object);
                    } else {
                        List<GameObject> objects = new ArrayList<>();
                        objects.add(object);
                        texturedMap.put(mesh, objects);
                    }
                } else if(object instanceof GameObject){
                    RawMesh mesh = object.getMesh();
                    if (map.keySet().contains(mesh)) {
                        map.get(mesh).add(object);
                    } else {
                        List<GameObject> objects = new ArrayList<>();
                        objects.add(object);
                        map.put(mesh, objects);
                    }
                }
            }
        }
        atlasTexturedEntityRenderer.render(atlastexturedMap,camera,light);
        texturedEntityRenderer.render(texturedMap, camera, light);
        rawEntityRenderer.render(map, camera, light);
        terrainRenderer.render(terrains, camera, light);
        guiRenderer.render(guis,camera,light);
    }

    public void destroy() {
        colorShader.destroy();
        blockShader.destroy();
        textureShader.destroy();
        terrainShader.destroy();
        atlasShader.destroy();
        guiShader.destroy();
        for (GameObject model : entityList) {
            model.destroyMesh();
        }
        for (Terrain terrain : terrains) {
            terrain.getMesh().destroy();
        }
        for(GuiTexture texture : guis){
            texture.destroy();
        }
    }

    public void setZenLinitie(){
        RawMesh[] plateaumeshes = ModelLoader.readModelFile("/zenplateau.fbx");
        RawMesh[] pionmeshes = ModelLoader.readModelFile("/pionts.fbx");
        for(RawMesh mesh : pionmeshes){
            mesh.setColors(new Vector3f[]{new Vector3f(255,255,255)});
            //entityList.add(new LoadedModel(new Vector3f(5,0,5),new Vector3f(90,0,0),new Vector3f(1,1,1),mesh));
        }
        for(RawMesh mesh : plateaumeshes){
            //entityList.add(new LoadedModel(new Vector3f(0,0,0),new Vector3f(90,0,0),new Vector3f(11,1,11),mesh));
        }
        entityList.add(new LineModel(new Vector3f(1, 0, 0), 10, new Vector3f(0, 2, 0), new Vector3f(255, 0, 0)));
        entityList.add(new LineModel(new Vector3f(0, 1, 0), 10, new Vector3f(0, 2, 0), new Vector3f(0, 255, 0)));
        entityList.add(new LineModel(new Vector3f(0, 0, 1), 10, new Vector3f(0, 2, 0), new Vector3f(0, 0, 255)));
    }

    public AtlasTexturedMesh[] toAtlas(TexturedMesh[] from){
        AtlasTexturedMesh[] out = new AtlasTexturedMesh[from.length];
        for(int i=0;i<from.length;i++){
            out[i]=new AtlasTexturedMesh(from[i],2);
        }
        return out;
    }


    public void addEntities() {
        this.guis.add(new GuiTexture(new Vector2f(0,0),new Vector2f(0.2f,0.2f),new Material(0,0,new Texture("gui/crosshair.png"))));
        //createSoil();
        Texture blendMap = new Texture("map.png");
        blendMap.create();
        TerrainTextures terrainTextures = new TerrainTextures(new Texture("soil/grass2.png"), new Texture("soil/dirt2.png"), new Texture("soil/flowers.png")
                , new Texture("soil/road2.png"), blendMap);
        Terrain te = new Terrain(0,0,terrainTextures,5,15);
        terrains.add(te);
        //terrains.add(new Terrain(0,-1,terrainTextures));
        //terrains.add(new Terrain(-1,0,terrainTextures));
        //terrains.add(new Terrain(0, 0, terrainTextures));

        TexturedMesh[] treeMeshes = ModelLoader.readModelFile("/tree/tree2.fbx", "/tree/");
        AtlasTexturedMesh[] fernMeshes = toAtlas(ModelLoader.readModelFile("/soil/fern.obj", ""));
        for (AtlasTexturedMesh mesh : fernMeshes) {
            mesh.getMaterial().setTransparent(true);
            mesh.getMaterial().setUsingFakeLighting(true);
        }
        TexturedMesh[] grassMeshes = ModelLoader.readModelFile("/soil/grassModel.obj", "");
        for (TexturedMesh mesh : grassMeshes) {
            mesh.getMaterial().setTransparent(true);
            mesh.getMaterial().setUsingFakeLighting(true);
        }


        Vector3f black = blendMap.colorAt(0,0);
        for (int i = 0; i < 512; i++) {
            for (int j = 0; j < 512; j++) {
                Vector3f point = blendMap.colorAt(i*2,j*2);
                if(point.getZ()==black.getZ()){
                    int percentage = (int) (Math.random() * 100);
                    if (percentage > 0 && percentage < 3) {
                        //entityList.add(new LoadedModel(new Vector3f(i, j, 0), new Vector3f(90, 0, 0), new Vector3f(0.005f, 0.005f, 0.005f),meshes[0]));
                        float randomRotation = (float) (Math.random() * 360);
                        for (TexturedMesh mesh : treeMeshes) {
                            entityList.add(new TexturedLoadedModel(new Vector3f(i, te.getHeight(i,j), j), new Vector3f(0, randomRotation, 0), new Vector3f(5, 5, 5), mesh));
                        }
                    }
                    if (percentage > 4 && percentage < 8) {
                        //entityList.add(new LoadedModel(new Vector3f(i, j, 0), new Vector3f(90, 0, 0), new Vector3f(0.005f, 0.005f, 0.005f),meshes[0]));
                        float randomRotation = (float) (Math.random() * 360);
                        for (TexturedMesh mesh : grassMeshes) {
                            entityList.add(new TexturedLoadedModel(new Vector3f(i, te.getHeight(i,j), j), new Vector3f(0, randomRotation, 0), new Vector3f(0.2f, 0.2f, 0.2f), mesh));
                        }
                    }
                    if (percentage > 9 && percentage < 15) {
                        //entityList.add(new LoadedModel(new Vector3f(i, j, 0), new Vector3f(90, 0, 0), new Vector3f(0.005f, 0.005f, 0.005f),meshes[0]));
                        float randomRotation = (float) (Math.random() * 360);
                        for (AtlasTexturedMesh mesh : fernMeshes) {
                            Random random = new Random();
                            entityList.add(new AtlasTexturedLoadedModel(new Vector3f(i, te.getHeight(i,j), j), new Vector3f(0, randomRotation, 0), new Vector3f(0.2f, 0.2f, 0.2f), mesh,random.nextInt(4)));
                        }
                    }
                }
            }
        }
        TexturedMesh[] cartMeshes = ModelLoader.readModelFile("/objects/Cart.fbx", "cart/");
        for (TexturedMesh mesh : cartMeshes) {
            entityList.add(new TexturedLoadedModel(new Vector3f((float)Math.random()*128, te.getHeight(15,6), (float)Math.random()*128), new Vector3f(0, 0, 0), new Vector3f(2, 2, 2), mesh));
        }

        TexturedMesh[] sofaMeshes = ModelLoader.readModelFile("/objects/Regency_low_optimized.fbx", "");
        for (TexturedMesh mesh : sofaMeshes) {
            entityList.add(new TexturedLoadedModel(new Vector3f(5, te.getHeight(5,50), 50), new Vector3f(0, 270, 0), new Vector3f(0.02f, 0.02f, 0.02f), mesh));
        }

        TexturedMesh[] houseMesh = ModelLoader.readModelFile("/objects/hatka_local.FBX", "woodenhouse/");
        double positionX = Math.random()*128;
        double positionZ = Math.random()*128;
        for (TexturedMesh mesh : houseMesh) {
            entityList.add(new TexturedLoadedModel(new Vector3f(-7, te.getHeight(-7,50)+8, 50), new Vector3f(90, 0, 0), new Vector3f(0.025f, 0.025f, 0.025f), mesh));
        }

        TexturedMesh[] container = ModelLoader.readModelFile("/objects/Container_fbx.fbx","container/");
        for(TexturedMesh mesh : container){
            entityList.add(new TexturedLoadedModel(new Vector3f(10,te.getHeight(10,0),0),new Vector3f(0,0,0),new Vector3f(0.05f,0.05f,0.05f),mesh));
        }


        entityList.add(Main.playerEntity);


        entityList.add(new LineModel(new Vector3f(1, 0, 0), 10, new Vector3f(0, 2, 0), new Vector3f(255, 0, 0)));
        entityList.add(new LineModel(new Vector3f(0, 1, 0), 10, new Vector3f(0, 2, 0), new Vector3f(0, 255, 0)));
        entityList.add(new LineModel(new Vector3f(0, 0, 1), 10, new Vector3f(0, 2, 0), new Vector3f(0, 0, 255)));
    }

    public void createSoil() {
        Material grass_side = new Material(1, 1 , new Texture("blocks/grass_side.png"));
        Material grass_top = new Material(1, 0 , new Texture("blocks/grass_top_green.png"));
        Material dirt = new Material(1, 0, new Texture("blocks/dirt.png"));
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                MinecraftBlock block = new MinecraftBlock(new Vector3f(i, 0, j), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), grass_side, grass_top, dirt);
                for (BlockObject2D object2D : block.getSides()) {
                    entityList.add(object2D);
                }
            }
        }
    }

    public ArrayList<GameObject> getEntityList() {
        return entityList;
    }
}
