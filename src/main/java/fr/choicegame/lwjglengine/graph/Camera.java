package fr.choicegame.lwjglengine.graph;

import java.util.Random;

import org.joml.Vector2f;

public class Camera {

    private final Vector2f position;
    private float rotation;

    public Camera() {
        position = new Vector2f(0, 0);
        rotation = 0f;
    }

    public Camera(Vector2f position, float rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public void movePosition(float offsetX, float offsetY) {
    	position.x += offsetX;
        position.y += offsetY;
    }

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation){
		this.rotation = rotation;
	}
	
	public Camera shake(float intensity){
		if(intensity <= 0){
			return this;
		}else{
			Random r = new Random();
			Camera clone = new Camera();
			clone.setPosition(position.x+(r.nextFloat()-0.5f)*intensity,position.y+(r.nextFloat()-0.5f)*intensity);
			clone.setRotation(rotation);
			return clone;
		}
	}
	
}
