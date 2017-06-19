package fr.choicegame.lwjglengine;

import java.util.ArrayList;
import java.util.List;

import fr.choicegame.lwjglengine.graph.Material;
import fr.choicegame.lwjglengine.graph.Mesh;
import fr.choicegame.lwjglengine.graph.Texture;

public class DialogItem extends GameItem {

	private static final int VERTICES_PER_QUAD = 4;

	private Texture texture;
	
	private boolean show_cursor = false;
	
	private int w,h;

	public DialogItem(Texture texture, int w, int h) throws Exception {
	    super();
	    this.texture = texture;
	    this.w = w;
	    this.h = h;
	    setMesh(buildMesh());
	}
	
	private Mesh buildMesh() {
		
		List<Float> positions = new ArrayList<>();
		List<Float> textCoords = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();

		float tileHeight = texture.getHeight()/3f;
		float tileWidth = texture.getWidth()/4f;
		
		int tx, ty;
		
		float cx = w*tileWidth/2f;
		float cy = h*tileHeight/2f;
		
		int n = 0;
		
		for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){
				
				if(x == 0){
					tx = 0;
				}else if(x == w-1){
					tx = 2;
				}else{
					tx = 1;
				}
				
				if(y == 0){
					ty = 0;
				}else if(y == h-1){
					ty = 2;
				}else{
					ty = 1;
				}
				
				positions.add(x*tileWidth-cx); // x
				positions.add(y*tileHeight-cy); // y
				textCoords.add(tx/4f);
				textCoords.add(ty/3f);
				indices.add(n * VERTICES_PER_QUAD);

				// Left Bottom vertex
				positions.add(x*tileWidth-cx); // x
				positions.add((y+1)*tileHeight-cy); // y
				textCoords.add(tx/4f);
				textCoords.add((ty+1)/3f);
				indices.add(n * VERTICES_PER_QUAD + 1);

				// Right Bottom vertex
				positions.add((x+1)*tileWidth-cx); // x
				positions.add((y+1)*tileHeight-cy); // y
				textCoords.add((tx+1)/4f);
				textCoords.add((ty+1)/3f);
				indices.add(n * VERTICES_PER_QUAD + 2);

				// Right Top vertex
				positions.add((x+1)*tileWidth-cx); // x
				positions.add(y*tileHeight-cy); // y
				textCoords.add((tx+1)/4f);
				textCoords.add(ty/3f);
				indices.add(n * VERTICES_PER_QUAD + 3);

				// Add indices por left top and bottom right vertices
				indices.add(n * VERTICES_PER_QUAD);
				indices.add(n * VERTICES_PER_QUAD + 2);
				n++;
			}
			
			if(show_cursor){
				positions.add((w-1.5f)*tileWidth-cx); // x
				positions.add((h-1.5f)*tileHeight-cy); // y
				textCoords.add(3f/4f);
				textCoords.add(0f);
				indices.add(n * VERTICES_PER_QUAD);

				// Left Bottom vertex
				positions.add((w-1.5f)*tileWidth-cx); // x
				positions.add((h-0.5f)*tileHeight-cy); // y
				textCoords.add(3f/4f);
				textCoords.add(1f/3f);
				indices.add(n * VERTICES_PER_QUAD + 1);

				// Right Bottom vertex
				positions.add((w-0.5f)*tileWidth-cx); // x
				positions.add((h-0.5f)*tileHeight-cy); // y
				textCoords.add(1f);
				textCoords.add(1f/3f);
				indices.add(n * VERTICES_PER_QUAD + 2);

				// Right Top vertex
				positions.add((w-0.5f)*tileWidth-cx); // x
				positions.add((h-1.5f)*tileHeight-cy); // y
				textCoords.add(1f);
				textCoords.add(0f);
				indices.add(n * VERTICES_PER_QUAD + 3);

				// Add indices por left top and bottom right vertices
				indices.add(n * VERTICES_PER_QUAD);
				indices.add(n * VERTICES_PER_QUAD + 2);
				n++;
			}
		}
		return new Mesh(positions, textCoords, indices, new Material(texture));
	}

	public float getWidth(){
		return this.getScale()*w*texture.getWidth()/4f;
	}
	
	public float getHeight(){
		return this.getScale()*h*texture.getHeight()/3f;
	}
	
	public boolean isCursorShown(){
		return show_cursor;
	}
	
	public void showCursor(boolean show){
		this.show_cursor = show;
		this.getMesh().cleanUp();
		this.setMesh(buildMesh());
	}
	
}
