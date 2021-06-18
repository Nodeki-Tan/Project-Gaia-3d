package toolbox.physics;

import core.MapCore;
import core.RenderCore;
import entities.Camera;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import toolbox.Maths;

public class Raycaster {

    private Vector3f currentRay;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    public Raycaster(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public Vector3f checkRayInMap(float range, boolean back, boolean centered){
        viewMatrix = Maths.createViewMatrix(RenderCore.camera);
        currentRay = calculateMouseRay(centered);
        return binarySearch(0, range, currentRay, back);
    }

    private Vector3f calculateMouseRay(boolean centered){
        float mouseX = Mouse.getX();
        float mouseY = Mouse.getY();

        Vector2f normalicedCoordinates = getNormalicedDeviceCoordinates(mouseX, mouseY);

        Vector4f clipCoords;

        if(centered) {
            clipCoords = new Vector4f(0f, 0f, -1f, 1f);
        }
        else{
            clipCoords = new Vector4f(normalicedCoordinates.x, normalicedCoordinates.y, -1f, 1f);
        }

        Vector4f eyeCoords = toEyeCoordinates(clipCoords);
        Vector3f worldRay = toWorldCoordinates(eyeCoords);
        return worldRay;
    }

    private Vector3f toWorldCoordinates(Vector4f eyeCoords){
        Matrix4f invertedViewMatrix = Matrix4f.invert(viewMatrix, null);
        Vector4f rayCoords = Matrix4f.transform(invertedViewMatrix, eyeCoords, null);
        Vector3f mouseRay = new Vector3f(rayCoords.x, rayCoords.y, rayCoords.z);
        mouseRay.normalise();
        return mouseRay;
    }

    private Vector4f toEyeCoordinates(Vector4f clipCoords){
        Matrix4f invertedProjectionMatrix = Matrix4f.invert(projectionMatrix, null);
        Vector4f eyeCoords = Matrix4f.transform(invertedProjectionMatrix, clipCoords, null);

        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    private Vector2f getNormalicedDeviceCoordinates(float mouseX, float mouseY){
        float x = (2f * mouseX) / Display.getWidth() - 1;
        float y = (2f * mouseY) / Display.getHeight() - 1;
        return new Vector2f(x,y);
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    //**********************************************************

    private Vector3f getPointOnRay(Vector3f ray, float distance) {
        Vector3f camPos = RenderCore.camera.getPosition();
        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return Vector3f.add(start, scaledRay, null);
    }

    private Vector3f binarySearch(float start, float finish, Vector3f ray, boolean getBack) {

        boolean collided = false;

        float current = 0f;
        Vector3f endPoint = null;

        while (!collided) {

            endPoint = getPointOnRay(ray, current);

            if(MapCore.getTile((int)(endPoint.x), (int)(endPoint.y), (int)(endPoint.z)) != 0){

                if(getBack) {
                    while (MapCore.getTile((int)(endPoint.x), (int)(endPoint.y), (int)(endPoint.z)) == 0) {
                        current -= 0.001f;
                        endPoint = getPointOnRay(ray, current);
                    }

                    current -= 0.001f;

                    endPoint = getPointOnRay(ray, current);
                }

                collided = true;

                System.out.println("ray proccesing ended as collided");
            }
            else {
                current += 0.001f;

                if (current >= finish) {

                    endPoint = null;

                    collided = true;

                    System.out.println("ray proccesing ended as non collided");
                }

            }



        }

        System.out.println("ray proccesing finished");

        return endPoint;
    }

}
