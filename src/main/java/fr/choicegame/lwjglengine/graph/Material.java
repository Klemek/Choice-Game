package fr.choicegame.lwjglengine.graph;

import java.awt.Color;

import org.joml.Vector4f;

public class Material {

	private static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
	
	private Vector4f color;
	private Texture texture;
	
	public Material(Texture texture, Vector4f color) {
        this.color = color;
        this.texture = texture;
	}
	
	public Material(Texture texture){
		this(texture,DEFAULT_COLOR);
	}
	
	public Material(Color c){
		this(null, new Vector4f((float)c.getRed()/255f,(float)c.getGreen()/255f,(float)c.getBlue()/255f,(float)c.getAlpha()/255f));
	}
	
	public Material(Vector4f color){
		this(null,color);
	}
	
	public Material() {
        this(null,DEFAULT_COLOR);
	}
	
	
	
	public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
}

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}
	
}
