package fr.choicegame.lwjglengine;

import org.joml.Vector3f;

import fr.choicegame.lwjglengine.graph.Material;
import fr.choicegame.lwjglengine.graph.Mesh;
import fr.choicegame.lwjglengine.graph.Texture;

public class GameItem {

	public static final int[] QUAD_INDICES = new int[]{
	        0, 1, 3, 3, 1, 2,
	    };
	
	public static final float[] QUAD_POSITIONS = new float[]{
	        -0.5f,  0.5f, 0f,
	        -0.5f, -0.5f, 0f,
	         0.5f, -0.5f, 0f,
	         0.5f,  0.5f, 0f,
	    };
	
	public static final float[] QUAD_TEXTCOORDS = new float[] {
			0f, 0f,
			0f, 1f,
			1f, 1f,
			1f, 0f
		};
	
    private Mesh mesh;

    private final Vector3f position;

    private float scale;

    private final Vector3f rotation;
    
    private boolean visible;

    public GameItem() {
    	this(null);
    }
    
    public GameItem(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
        this.visible = true;
    }

    public Vector3f getPosition() {
        return position;
    }

    public GameItem setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
        return this;
    }

    public float getScale() {
        return scale;
    }

    public GameItem setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public GameItem setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
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
		
		float[] positions;
		
		if(material.getTexture() != null){
			positions = getQuadPositions(((float)material.getTexture().getWidth())/((float)material.getTexture().getHeight()));
		}else{
			positions = getQuadPositions(1f);
		}
		
		return new GameItem(new Mesh(positions, QUAD_TEXTCOORDS, QUAD_INDICES, material));
	}
	
	public static float[] getQuadPositions(float ratio){
		if(ratio>1){
			return new float[]{
			        -0.5f,  1f/(2f*ratio), 0f,
			        -0.5f, -1f/(2f*ratio), 0f,
			         0.5f, -1f/(2f*ratio), 0f,
			         0.5f,  1f/(2f*ratio), 0f,
			    };
		}else{
			return new float[]{
					-ratio/2f,  0.5f, 0f,
			        -ratio/2f, -0.5f, 0f,
			        ratio/2f, -0.5f, 0f,
			        ratio/2f,  0.5f, 0f,
			    };
		}
	}
}