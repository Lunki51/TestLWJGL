package fr.lunki.testlwjgl.engine.objects.player;

import fr.lunki.testlwjgl.Main;
import fr.lunki.testlwjgl.RenderManager;
import fr.lunki.testlwjgl.engine.graphics.meshes.TexturedMesh;
import fr.lunki.testlwjgl.engine.io.Input;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.TexturedGameObject;
import fr.lunki.testlwjgl.engine.terrain.Terrain;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;

public class PlayerEntity extends Playable {

    private static final float WALK_SPEED = 5;
    private float mouseSensitivity = 0.08f;

    public PlayerEntity(Vector3f position, Vector3f rotation, Vector3f scale, TexturedMesh mesh, float headHight) {
        super(position, rotation, scale, mesh);
        this.currentSpeed = new Vector3f();
        this.headhight=headHight;
    }

    public void move() {
        getPosition().add(0,-1,0);
        Terrain ter = null;
        for(Terrain terrain : RenderManager.terrains){
            if(terrain.contains(getPosition().getX(),getPosition().getZ())){
                ter=terrain;
            }
        }
        float terrainHeight = ter==null?0:ter.getHeight((int)getPosition().getX(),(int)getPosition().getZ());

        updateRotation();
        updatePosition(terrainHeight);

        float delta = (float) Main.getWindow().getFrameTimeSeconds();
        Vector3f currentTime = new Vector3f(delta, delta, delta);


        getRotation().add(0, currentRotation.getY(), 0);
        getPosition().add(Vector3f.multiply(currentSpeed,currentTime));
        if(getPosition().getY()<terrainHeight)getPosition().set(getPosition().getX(),terrainHeight,getPosition().getZ());
    }

    public void updatePosition(float terrainHeight) {
        float nowSpeed = WALK_SPEED;
        if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT))nowSpeed*=2;
        float x = (float) Math.sin(Math.toRadians(getRotation().getY())) * nowSpeed;
        float y = (float) Math.cos(Math.toRadians(getRotation().getY())) * nowSpeed;

        currentSpeed=new Vector3f();

        if (Input.isKeyPressed(GLFW.GLFW_KEY_A))currentSpeed.add(new Vector3f(-y, 0, x));
        if (Input.isKeyPressed(GLFW.GLFW_KEY_D)) currentSpeed.add(new Vector3f(y, 0, -x));
        if (Input.isKeyPressed(GLFW.GLFW_KEY_W)) currentSpeed.add(new Vector3f(-x, 0, -y));
        if (Input.isKeyPressed(GLFW.GLFW_KEY_S)) currentSpeed.add(new Vector3f(x, 0, y));
        if (Input.isKeyPressed(GLFW.GLFW_KEY_SPACE))currentSpeed.add(new Vector3f(0, nowSpeed, 0));
        if(Input.isKeyPressed(GLFW_KEY_LEFT_CONTROL)){
            isCrouching=true;
        }else{
            isCrouching=false;
        }

    }



    public void updateRotation() {
        double mouseY = Input.getMouseX() - Main.getWindow().getWidth() / 2;
        double mouseX = Input.getMouseY() - Main.getWindow().getHeight() / 2;
        float diffx = (float) (mouseX), diffy = (float) (mouseY);
        currentRotation = new Vector3f(-diffx * mouseSensitivity, -diffy * mouseSensitivity, 0);
    }

}
