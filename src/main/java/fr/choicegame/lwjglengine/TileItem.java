package fr.choicegame.lwjglengine;

import java.util.ArrayList;
import java.util.List;

import fr.choicegame.Config;
import fr.choicegame.lwjglengine.graph.Material;
import fr.choicegame.lwjglengine.graph.Mesh;
import fr.choicegame.lwjglengine.graph.Texture;

public class TileItem {

    private final List<GameItem> items;
    
    private static final int[] INDICES = new int[]{
									        0, 1, 3, 3, 1, 2,
									    };
    
    private static final float Z = 0f;
    private static final float DELTA = 0.01f;
    
    public TileItem(Texture tex, int id) {
    	items = new ArrayList<>();
    	if(tex!= null){
		    items.add(new GameItem(new Mesh(getPositions(Z),tex.getTextCoords(id, Config.getIntValue(Config.TILE_SIZE)), INDICES, new Material(tex))));
    	} 
    }
    
    public TileItem(Texture[] texs, int[] ids) {
    	
	    items = new ArrayList<>();
	    
	    for(int i = 0; i < 4; i++){
	    	if(texs[i] != null){
	    		float z = Z;
	    		if(i<2)
	    			z -= DELTA*(2-i);
	    		else
	    			z += DELTA*(i-1);
	    		items.add(new GameItem(new Mesh(getPositions(z),texs[i].getTextCoords(ids[i], Config.getIntValue(Config.TILE_SIZE)), INDICES, new Material(texs[i]))));
	    	}else{
	    		items.add(null);
	    	}
	    }
    }
    
    private static float[] getPositions(float z){
    	return new float[]{
    	        -0.5f,  0.5f, z,
    	        -0.5f, -0.5f, z,
    	         0.5f, -0.5f, z,
    	         0.5f,  0.5f, z,
    	    };
    }

    public List<GameItem> getItems() {
		return items;
	}

	public void setPosition(float x, float y) {
        for(GameItem item:items)
        	if(item != null)
        		item.setPosition(x, y, Z);
    }

    public void setScale(float scale) {
    	for(GameItem item:items)
    		if(item != null)
    			item.setScale(scale);
    }
 

    public void setRotation(float x, float y, float z) {
    	for(GameItem item:items)
    		if(item != null)
    			item.setRotation(x, y, z);
    }
    
    public void cleanup(){
    	for(GameItem item: items)
    		if(item != null)
    			item.getMesh().cleanUp();
    }
    
}