package fr.choicegame;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Matrix4f;

import fr.choicegame.character.NPC;
import fr.choicegame.character.Player;
import fr.choicegame.lwjglengine.TileItem;
import fr.choicegame.lwjglengine.Utils;
import fr.choicegame.lwjglengine.Window;
import fr.choicegame.lwjglengine.graph.Camera;
import fr.choicegame.lwjglengine.graph.ShaderProgram;
import fr.choicegame.lwjglengine.graph.Texture;
import fr.choicegame.lwjglengine.graph.Transformation;

public class Renderer {

	 /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.001f;

    private static final float Z_FAR = 100.f;
	
	private ShaderProgram shaderProgram;
	
	private Transformation transformation;

	private HashMap<String, Texture> textures;
	
	private final Loader loader;
	
	private TileItem playerTileItem;
	private List<TileItem> npcTileItems;
	private List<TileItem> mapTileItems;
	private final Camera camera;
	private float zoom = 10f;
	private float updateTimer = 0f;
	private static final float UPDATETIME = 0.5f;

	public Renderer(Loader l){
		loader = l;
		transformation = new Transformation();
		camera = new Camera();
		mapTileItems = new ArrayList<>();
		npcTileItems = new ArrayList<>();
	}

	public void init(Window window) throws Exception {
		this.textures = loader.loadTextures();

		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(Utils.loadResource("/vertex.vs",this));
		shaderProgram.createFragmentShader(Utils.loadResource("/fragment.fs",this));
		shaderProgram.link();
		
		shaderProgram.createUniform("projectionMatrix");
	    shaderProgram.createUniform("modelViewMatrix");
	    shaderProgram.createUniform("texture_sampler");

	    window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	}
	
	public void updateCharacters(float interval, Player p, Map m){
		//tick to change frame in animations
		updateTimer += interval;
		int tick = (int)(updateTimer/UPDATETIME);
		//refresh all positions and camera
		if(playerTileItem != null)
			playerTileItem.cleanup();
		
		if(p != null && m != null){
			TileImage ti = p.getImage(tick);
			playerTileItem = new TileItem(getTexture("/"+Config.getValue(Config.TILESETS_FOLDER)+"/"+ti.getTileset()+".png"),ti.getId());
			playerTileItem.setPosition(p.getPosX(), m.getHeight()-p.getPosY());
			camera.setPosition(p.getPosX(), m.getHeight()-p.getPosY(), zoom);
		}
		
		for(TileItem item:npcTileItems)
			item.cleanup();
		npcTileItems.clear();
		
		if(m != null){
		
			for(String key:m.getNpcs().keySet()){
				NPC npc = m.getNpcs().get(key);
				TileImage ti = npc.getImage(tick);
				TileItem item = new TileItem(getTexture("/"+Config.getValue(Config.TILESETS_FOLDER)+"/"+ti.getTileset()+".png"),ti.getId());
				item.setPosition(npc.getPosX(), m.getHeight()-npc.getPosY());
				npcTileItems.add(item);
			}
		
		}
		
	}
	
	public void updateMap(Map m) {
		
		// free tile memory
		for(TileItem item:mapTileItems)
			item.cleanup();
		mapTileItems.clear();
		
		if(m != null){
			//allocate tiles
			for(int x = 0; x < m.getWidth(); x++){
				for(int y = 0; y < m.getHeight(); y++){
					TileImage[] timgs = m.getTile(x, y).getImages();
					Texture[] texs = new Texture[4];
					int[] ids = new int[4];
					for(int i = 0; i < 4; i++){
						if(timgs[i] != null){
							ids[i] = timgs[i].getId();
							texs[i] = getTexture("/"+Config.getValue(Config.TILESETS_FOLDER)+"/"+timgs[i].getTileset()+".png");
						}
					}
					TileItem item = new TileItem(texs, ids);
					item.setPosition(x, (m.getHeight()-y));
					item.setScale(1.0f); //merge borders;
					mapTileItems.add(item);
				}
			}
			camera.setPosition(m.getWidth()/2f, m.getHeight()/2f, zoom); //TODO position
		}
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window) {
		clear();
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		
		// Enable transparency
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();
		
		shaderProgram.setUniform("texture_sampler", 0);
		
		// Update projection Matrix
	    Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
	    shaderProgram.setUniform("projectionMatrix", projectionMatrix);

	    Matrix4f viewMatrix = transformation.getViewMatrix(camera);
	    
	    // Render each tileItem level by level
	    
	    //render background 0
	    for(TileItem tileItem : mapTileItems) {
	    	renderTileItem(tileItem, viewMatrix,0);
	    }
	    //render background 1
	    for(TileItem tileItem : mapTileItems) {
	    	renderTileItem(tileItem, viewMatrix,1);
	    }
	    //render npcs
	    for(TileItem tileItem : npcTileItems) {
	        // Set world matrix for this item
	    	renderTileItem(tileItem, viewMatrix);
	    }
	    //render player
	    if(playerTileItem != null){
	    	renderTileItem(playerTileItem, viewMatrix);
	    }
	    //render foreground 0
	    for(TileItem tileItem : mapTileItems) {
	    	renderTileItem(tileItem, viewMatrix,2);
	    }
	    //render foreground 1
	    for(TileItem tileItem : mapTileItems) {
	    	renderTileItem(tileItem, viewMatrix,3);
	    }

		shaderProgram.unbind();
	}
	
	private void renderTileItem(TileItem tileItem, Matrix4f viewMatrix){
		Matrix4f modelViewMatrix = transformation.getModelViewMatrix(tileItem, viewMatrix);
        shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
        tileItem.render();
	}
	
	private void renderTileItem(TileItem tileItem, Matrix4f viewMatrix, int i){
		Matrix4f modelViewMatrix = transformation.getModelViewMatrix(tileItem, viewMatrix);
        shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
        tileItem.render(i);
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
		for(TileItem item:mapTileItems)
			item.cleanup();
		mapTileItems.clear();
	}

	public Texture getTexture(String name) {
		return textures.get(name);
	}

	public void setTextures(HashMap<String, Texture> textures) {
		this.textures = textures;
	}

}
