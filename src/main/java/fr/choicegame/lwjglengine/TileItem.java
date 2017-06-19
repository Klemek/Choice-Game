package fr.choicegame.lwjglengine;

import java.util.ArrayList;
import java.util.List;

import fr.choicegame.Config;
import fr.choicegame.lwjglengine.graph.Material;
import fr.choicegame.lwjglengine.graph.Mesh;
import fr.choicegame.lwjglengine.graph.Texture;

public class TileItem {

    private final List<GameItem> items;
    
    public TileItem(Texture tex, int id) {
    	items = new ArrayList<>();
    	if(tex!= null){
		    items.add(new GameItem(new Mesh(GameItem.QUAD_POSITIONS,
		    							tex.getTextCoords(id, Config.getIntValue(Config.TILE_SIZE)),
		    							GameItem.QUAD_INDICES,
		    							new Material(tex))));
    	} 
    }
    
    public TileItem(Texture[] texs, int[] ids) {
    	
	    items = new ArrayList<>();
	    for(int i = 0; i < 4; i++){
	    	if(texs[i] != null){
	    		items.add(new GameItem(new Mesh(GameItem.QUAD_POSITIONS,
    										texs[i].getTextCoords(ids[i], Config.getIntValue(Config.TILE_SIZE)),
    										GameItem.QUAD_INDICES,
    										new Material(texs[i]))));
	    	}else{
	    		items.add(null);
	    	}
	    }
    }

    public List<GameItem> getItems() {
		return items;
	}

	public void setPosition(float x, float y) {
        for(GameItem item:items)
        	if(item != null)
        		item.setPosition(x, y);
    }

    public void setScale(float scale) {
    	for(GameItem item:items)
    		if(item != null)
    			item.setScale(scale);
    }
 

    public void setRotation(float r) {
    	for(GameItem item:items)
    		if(item != null)
    			item.setRotation(r);
    }
    
    public void cleanup(){
    	for(GameItem item: items)
    		if(item != null)
    			item.getMesh().cleanUp();
    }
    
}