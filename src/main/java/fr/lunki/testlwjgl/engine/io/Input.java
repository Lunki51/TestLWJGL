package fr.lunki.testlwjgl.engine.io;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] mouseButtons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX;
    private static double mouseY;
    private static double scrollX = 0;
    private static double scrollY = 0;

    private static GLFWKeyCallback keyCallback;
    private static GLFWMouseButtonCallback mouseButtonCallback;
    private static GLFWCursorPosCallback cursorPosCallback;
    private static GLFWScrollCallback scrollCallback;

    public static boolean isMousePressed(int mouse) {
        return mouseButtons[mouse];
    }

    public static boolean isKeyPressed(int key) {
        return keys[key];
    }

    public static boolean[] getKeys() {
        return keys;
    }

    public static boolean[] getMouseButtons() {
        return mouseButtons;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }

    public static GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    public static GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public static GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public static GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public void destroy() {
        keyCallback.free();
        mouseButtonCallback.free();
        cursorPosCallback.free();
        scrollCallback.free();
    }

    public Input() {
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_PRESS) {
                    keys[key] = true;
                }
                if (action == GLFW_RELEASE) {
                    keys[key] = false;
                }
            }
        };

        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW_PRESS) {
                    mouseButtons[button] = true;
                }
                if (action == GLFW_RELEASE) {
                    mouseButtons[button] = false;
                }
            }
        };

        cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos+400;
                mouseY = ypos+400;
            }
        };

        scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };


    }
}
