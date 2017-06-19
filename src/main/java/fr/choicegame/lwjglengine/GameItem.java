package fr.choicegame.lwjglengine;

import org.joml.Vector2f;

import fr.choicegame.lwjglengine.graph.Material;
import fr.choicegame.lwjglengine.graph.Mesh;
import fr.choicegame.lwjglengine.graph.Texture;

public class GameItem {

	public static final int[] QUAD_INDICES = new int[]{
	        0, 1, 3, 3, 1, 2,
	    };
	
	public static final float[] QUAD_POSITIONS = new float[]{
	        -0.5f,  0.5f,
	        -0.5f, -0.5f,
	         0.5f, -0.5f,
	         0.5f,  0.5f,
	    };
	
	public static final float[] QUAD_TEXTCOORDS = new float[] {
			0f, 0f,
			0f, 1f,
			1f, 1f,
			1f, 0f
		};
	
    private Mesh mesh;

    private final Vector2f position;

    private float scale;

    private float rotation;
    
    private boolean visible;

    public GameItem() {
    	this(null);
    }
    
    public GameItem(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector2f(0, 0);
        scale = 1;
        rotation = 0f;
        this.visible = true;
    }

    public Vector2f getPosition() {
        return position;
    }

    public GameItem setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
        return this;
    }

    public float getScale() {
        return scale;
    }

    public GameItem setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public float getRotation() {
        return rotation;
    }

    public GameItem setRotation(float r) {
        this.rotation = r;
        return this;
    }

    public Mesh getMesh() {
        return mesh;
    }
    
    public void setMesh(Mesh mesh){
    	this.mesh = mesh;
    }

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public float getRatio(){
		Texture t = this.mesh.getMaterial().getTexture();
		if(t == null){
			return 1f;
		}else{
			return (float)t.getWidth()/(float)t.getHeight();
		}
	}
	
	
	public static GameItem simpleQuad(Material material){
		return simpleQuad(material,1f);
	}
	
	public static GameItem simpleQuad(Material material, float scale){
		
		float[] positions;
		
		if(material.getTexture() != null){
			positions = getQuadPositions(((float)material.getTexture().getWidth())/((float)material.getTexture().getHeight()),scale);
		}else{
			positions = getQuadPositions(1f, scale);
		}
		
		return new GameItem(new Mesh(positions, QUAD_TEXTCOORDS, QUAD_INDICES, material));
	}
	
	public static float[] getQuadPositions(float ratio, float scale){
		if(ratio>1){
			return new float[]{
			        -scale/2f,  scale/(2f*ratio),
			        -scale/2f, -scale/(2f*ratio),
			        scale/2f, -scale/(2f*ratio),
			        scale/2f,  scale/(2f*ratio),
			    };
		}else{
			return new float[]{
					-ratio*scale/2f,  scale/2f,
			        -ratio*scale/2f, -scale/2f,
			        ratio*scale/2f, -scale/2f,
			        ratio*scale/2f,  scale/2f,
			    };
		}
	}
}