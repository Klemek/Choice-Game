package fr.choicegame;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Matrix4f;

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

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;
	
	private ShaderProgram shaderProgram;
	
	private Transformation transformation;

	private HashMap<String, Texture> textures;
	
	private final Loader loader;
	
	private TileItem playerTileItem;
	private List<TileItem> mapTileItems;
	private final Camera camera;

	public Renderer(Loader l){
		loader = l;
		transformation = new Transformation();
		camera = new Camera();
		mapTileItems = new ArrayList<>();
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
	
	public void updateMap(Map m) {
		// free tile memory
		for(TileItem item:mapTileItems)
			item.cleanup();
		mapTileItems.clear();
		
		//allocate tiles
		for(int x = 0; x < m.getWidth(); x++){
			for(int y = 0; y < m.getHeight(); y++){
				TileImage[] timgs = m.getTile(x, y).getImages();
				Texture[] texs = new Texture[timgs.length];
				int[] ids = new int[timgs.length];
				for(int i = 0; i < timgs.length; i++){
					if(timgs[i] != null){
						ids[i] = timgs[i].getId();
						texs[i] = getTexture("/tilesets/"+timgs[i].getTileset()+".png");
					}
				}
				TileItem item = new TileItem(texs, ids);
				item.setPosition(x, (m.getHeight()-y));
				item.setScale(1.01f); //merge borders;
				mapTileItems.add(item);
			}
		}
		camera.setPosition(m.getWidth()/2f, m.getHeight()/2f, 10f); //TODO position
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window) {
		clear();

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
	    
	    // Render each gameItem
	    for(TileItem tileItem : mapTileItems) {
	        // Set world matrix for this item
	    	Matrix4f modelViewMatrix = transformation.getModelViewMatrix(tileItem, viewMatrix);
	        shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
	        tileItem.render();
	    }

		shaderProgram.unbind();
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
