package fr.choicegame.lwjglengine.graph;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import fr.choicegame.lwjglengine.GameItem;

public class Transformation {

    private final Matrix4f projectionMatrix;

    private final Matrix4f modelViewMatrix;
    
    private final Matrix4f viewMatrix;
    
    private final Matrix4f orthoMatrix;

    public Transformation() {
        modelViewMatrix = new Matrix4f(); 
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        orthoMatrix = new Matrix4f();
    }
    
    public final Matrix4f getProjectionMatrix(float width, float height) {
        float aspectRatio = width / height;        
        projectionMatrix.identity();
        projectionMatrix.scale(1f, aspectRatio, 1f);
        return projectionMatrix;
    }
    
    public final Matrix4f getOrthoProjectionMatrix(float left, float right, float bottom, float top) {
        orthoMatrix.identity();
        orthoMatrix.setOrtho2D(left, right, bottom, top);
        return orthoMatrix;
    }
    
    public Matrix4f getModelViewMatrix(GameItem gameItem, Matrix4f viewMatrix, float depth) {
        modelViewMatrix.identity().translate(gameItem.getPosition().x,gameItem.getPosition().y, depth).
            rotateZ((float)Math.toRadians(-gameItem.getRotation())).
            scale(gameItem.getScale());
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(modelViewMatrix);
    }
    
    public Matrix4f getViewMatrix(Camera camera, float depth) {
        Vector2f cameraPos = camera.getPosition();
        float rotation = camera.getRotation();

        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(rotation), new Vector3f(0, 0, 1));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y,depth);
        return viewMatrix;
    }
}