package fr.choicegame.lwjglengine;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import fr.choicegame.lwjglengine.graph.Mesh;
import fr.choicegame.lwjglengine.graph.Texture;

public class TileItem {

    private final List<Mesh> meshes;

    private final Vector3f position;

    private float scale;

    private final Vector3f rotation;
    
    private static final int[] INDICES = new int[]{
									        0, 1, 3, 3, 1, 2,
									    };
    
    private static final float Z = 0f;
    private static final float DELTA = 0.01f;
    
    public TileItem(Texture tex, int id) {
	    meshes = new ArrayList<>();
	    meshes.add(new Mesh(getPositions(Z),tex.getTextCoords(id, 32), INDICES, tex));
	    
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }
    
    public TileItem(Texture[] texs, int[] ids) {
    	
	    meshes = new ArrayList<>();
	    
	    for(int i = 0; i < 4; i++){
	    	if(texs[i] != null){
	    		float z = Z;
	    		if(i<2)
	    			z -= DELTA*(2-i);
	    		else
	    			z += DELTA*(i-1);
	    		meshes.add(new Mesh(getPositions(z),texs[i].getTextCoords(ids[i], 32), INDICES, texs[i]));
	    	}else{
	    		meshes.add(null);
	    	}
	    }
	    
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }
    
    private static float[] getPositions(float z){
    	return new float[]{
    	        -0.5f,  0.5f, z,
    	        -0.5f, -0.5f, z,
    	         0.5f, -0.5f, z,
    	         0.5f,  0.5f, z,
    	    };
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void render(){
    	for(Mesh mesh: meshes)
    		if(mesh != null)
    			mesh.render();
    }
    
    public void render(int i){
    	if(meshes.size()>i && meshes.get(i) != null)
    		meshes.get(i).render();
    }
    
    public void cleanup(){
    	for(Mesh mesh: meshes)
    		if(mesh != null)
    			mesh.cleanUp();
    }
    
}