package fr.lunki.testlwjgl.engine.io;

import fr.lunki.testlwjgl.Main;
import fr.lunki.testlwjgl.engine.maths.Matrix4f;
import fr.lunki.testlwjgl.engine.maths.Vector2f;
import fr.lunki.testlwjgl.engine.maths.Vector3f;
import fr.lunki.testlwjgl.engine.maths.Vector4f;
import fr.lunki.testlwjgl.engine.objects.GameObject;
import fr.lunki.testlwjgl.engine.objects.complex.LineModel;
import fr.lunki.testlwjgl.engine.objects.player.Camera;

import java.util.ArrayList;

public class MousePicker {
    private Vector3f currentRay;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;
    private Window window;

    public MousePicker(Camera camera, Window window) {
        this.camera = camera;
        this.window = window;
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    public void update() {
        projectionMatrix = window.getProjection();
        viewMatrix = Matrix4f.view(camera.getPosition(), camera.getRotation());
        currentRay = calculateMouseRay();
    }

    public Vector3f calculateMouseRay() {
        double mouseX = Input.getMouseX();
        double mouseY = Input.getMouseY();
        Vector2f vector2f = getNormalizedDeviceCoords((float) mouseX, (float) mouseY);
        Vector4f clipCoords = new Vector4f(vector2f.getX(), vector2f.getY(), -1.0f, 1.0f);
        Vector4f eyeCoords = toEyeCoord(clipCoords);
        Vector3f worldRay = toWorldCoord(eyeCoords);
        return worldRay;
    }

    private Vector3f toWorldCoord(Vector4f eyeCoord) {
        Matrix4f invertedViewMatrix = Matrix4f.invert(viewMatrix);
        Vector4f rayWorld = Matrix4f.multiply(invertedViewMatrix, eyeCoord);
        Vector3f mouseRay = new Vector3f(rayWorld.getX(), rayWorld.getY(), rayWorld.getZ());
        mouseRay = Vector3f.normalize(mouseRay);
        return mouseRay;
    }

    private Vector4f toEyeCoord(Vector4f clipCoords) {
        Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix);
        Vector4f eyeCoords = Matrix4f.multiply(invertedProjection, clipCoords);
        return new Vector4f(eyeCoords.getX(), eyeCoords.getY(), -1.0f, 0.0f);
    }

    private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
        float x = (2f * (mouseX)) / Main.getWindow().getWidth() - 1f;
        float y = (2f * (mouseY)) / Main.getWindow().getHeight() - 1f;
        return new Vector2f(x, y);
    }


    private GameObject getSelected(Camera camera, ArrayList<GameObject> entityList) {
        Vector3f mousePicked = getCurrentRay();
        GameObject focused = null;
        int length = 15;
        Vector3f lign = new Vector3f(camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ());
        while (focused == null && length != 0) {
            for (GameObject obj : entityList) {
                if (obj.getPosition().equals(new Vector3f((float) java.lang.Math.round(lign.getX() + 1), (float) java.lang.Math.round(lign.getY() + 1), (float) java.lang.Math.round(lign.getZ() + 1)))) {
                    focused = obj;
                    break;
                }
            }
            lign = Vector3f.add(lign, mousePicked);
            length--;
        }

        LineModel model = new LineModel(mousePicked, 15 - length, camera.getPosition(), new Vector3f(255, 255, 255));
        model.createMesh();
        entityList.add(model);

        return focused;
    }

}
