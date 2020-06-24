package fr.lunki.testlwjgl;

import fr.lunki.testlwjgl.engine.io.Input;
import fr.lunki.testlwjgl.engine.io.ModelLoader;
import fr.lunki.testlwjgl.engine.io.MousePicker;
import fr.lunki.testlwjgl.engine.io.Window;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.objects.Light;
import fr.lunki.testlwjgl.engine.objects.player.Camera;
import fr.lunki.testlwjgl.engine.objects.player.PlayerEntity;

import static org.lwjgl.glfw.GLFW.*;


public class Main {

    private static Window window = new Window(800, 800, "Test");

    public static PlayerEntity playerEntity = new PlayerEntity(new Vector3f(10,2,10),new Vector3f(180,0,180),new Vector3f(0.015f,0.015f,0.015f),ModelLoader.readModelFile("player/eric.fbx","player/eric/")[0],2);
    public static Camera camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0),playerEntity);

    public static RenderManager manager = new RenderManager(window, camera, new Light(new Vector3f(3000, 5000, 0), new Vector3f(1, 1, 1)));
    public static MousePicker mousePicker = new MousePicker(camera, window);

    public static void main(String[] args) {

        createDisplay();

        while (!window.shouldClose()) {
            updateDisplay();
        }

        closeDisplay();
    }

    public static void createDisplay() {
        window.setBackgroundColor(0, 255, 255);
        window.create();
        manager.create();
    }


    public static void updateDisplay() {
        camera.update();
        window.update();
        mousePicker.update();

        manager.render();

        window.swapBuffers();

        if (Input.isMousePressed(GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);
        if (Input.isKeyPressed(GLFW_KEY_ESCAPE)) org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose(window.getWindow(), true);
        glfwSetCursorPos(window.getWindow(), 0, 0);
    }

    public static void closeDisplay() {
        window.destroy();
        manager.destroy();

    }

    public static Window getWindow() {
        return window;
    }

    public static RenderManager getManager() {
        return manager;
    }
}
