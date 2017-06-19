package fr.choicegame;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Matrix4f;

import fr.choicegame.character.NPC;
import fr.choicegame.character.Player;
import fr.choicegame.lwjglengine.GameItem;
import fr.choicegame.lwjglengine.IHud;
import fr.choicegame.lwjglengine.TileItem;
import fr.choicegame.lwjglengine.Utils;
import fr.choicegame.lwjglengine.Window;
import fr.choicegame.lwjglengine.graph.Camera;
import fr.choicegame.lwjglengine.graph.Mesh;
import fr.choicegame.lwjglengine.graph.ShaderProgram;
import fr.choicegame.lwjglengine.graph.Texture;
import fr.choicegame.lwjglengine.graph.Transformation;

public class Renderer {

	private ShaderProgram shaderProgram;

	private Transformation transformation;

	private HashMap<String, Texture> textures;

	private final Loader loader;

	private TileItem playerTileItem;
	private List<TileItem> npcTileItems;
	private List<TileItem> mapTileItems;
	private IHud hud;
	private final Camera camera;
	private float scale = 0.2f;
	private float updateTimer = 0f;
	
	private static final float CAMERA_DEPTH = -1f;
	private static final float MAP_DEPTH = 1f;
	
	private static final float UPDATETIME = 0.2f;

	public Renderer(Loader l) {
		loader = l;
		transformation = new Transformation();
		camera = new Camera();
		mapTileItems = new ArrayList<>();
		npcTileItems = new ArrayList<>();
	}

	public void init(Window window) throws Exception {
		this.textures = loader.loadTextures();

		setupShader();
		
		window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	}

	private void setupShader() throws Exception {
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex.vs", this));
		shaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment.fs", this));
		shaderProgram.link();

		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("modelViewMatrix");
		shaderProgram.createUniform("texture_sampler");
		// Create uniform for default colour and the flag that controls it
		shaderProgram.createUniform("colour");
		shaderProgram.createUniform("useColour");
	}

	public void updateCharacters(float interval, Player p, Map m) {
		// tick to change frame in animations
		updateTimer += interval;
		int tick = (int) (updateTimer / UPDATETIME);
		// refresh all positions and camera
		if (playerTileItem != null)
			playerTileItem.cleanup();

		if (p != null && m != null) {
			TileImage ti = p.getImage(tick);
			playerTileItem = new TileItem(
					getTexture("/" + Config.getValue(Config.TILESETS_FOLDER) + "/" + ti.getTileset() + ".png"),
					ti.getId());
			playerTileItem.setPosition(p.getPosX() * scale, (m.getHeight() - p.getPosY()) * scale);
			playerTileItem.setScale(scale);
			camera.setPosition(p.getPosX() * scale, (m.getHeight() - p.getPosY()) * scale);
		}

		for (TileItem item : npcTileItems)
			item.cleanup();
		npcTileItems.clear();

		if (m != null) {

			for (String key : m.getNpcs().keySet()) {
				NPC npc = m.getNpcs().get(key);
				TileImage ti = npc.getImage(tick);
				TileItem item = new TileItem(
						getTexture("/" + Config.getValue(Config.TILESETS_FOLDER) + "/" + ti.getTileset() + ".png"),
						ti.getId());
				item.setPosition(npc.getPosX() * scale, (m.getHeight() - npc.getPosY()) * scale);
				item.setScale(scale);
				npcTileItems.add(item);
			}

		}

	}

	public void updateMap(Map m) {

		// free tile memory
		for (TileItem item : mapTileItems)
			item.cleanup();
		mapTileItems.clear();

		if (m != null) {
			// allocate tiles
			for (int x = 0; x < m.getWidth(); x++) {
				for (int y = 0; y < m.getHeight(); y++) {
					TileImage[] timgs = m.getTile(x, y).getImages();
					Texture[] texs = new Texture[4];
					int[] ids = new int[4];
					for (int i = 0; i < 4; i++) {
						if (timgs[i] != null) {
							ids[i] = timgs[i].getId();
							texs[i] = getTexture("/" + Config.getValue(Config.TILESETS_FOLDER) + "/"
									+ timgs[i].getTileset() + ".png");
						}
					}
					TileItem item = new TileItem(texs, ids);
					item.setPosition(x * scale, (m.getHeight() - y) * scale);
					item.setScale(scale);
					mapTileItems.add(item);
				}
			}
			camera.setPosition(scale * m.getWidth() / 2f, scale * m.getHeight() / 2f);
			// position
		}
	}

	public void setHud(IHud hud) {
		this.hud = hud;
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window) {
		clear();

		//glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);

		// Enable transparency
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
			if (hud != null)
				hud.updateSize(window);
		}

		renderScene(window);

		renderHud(window);

	}

	private void renderScene(Window window) {
		shaderProgram.bind();

		shaderProgram.setUniform("texture_sampler", 0);

		// Update projection Matrix to make squares
		Matrix4f projectionMatrix = transformation.getProjectionMatrix(window.getWidth(), window.getHeight());
		shaderProgram.setUniform("projectionMatrix", projectionMatrix);

		Matrix4f viewMatrix = transformation.getViewMatrix(camera, CAMERA_DEPTH);

		// Render each tileItem level by level

		// render background 0
		for (TileItem tileItem : mapTileItems) {
			renderTileItem(tileItem, viewMatrix, 0);
		}
		// render background 1
		for (TileItem tileItem : mapTileItems) {
			renderTileItem(tileItem, viewMatrix, 1);
		}
		// render npcs
		for (TileItem tileItem : npcTileItems) {
			// Set world matrix for this item
			renderTileItem(tileItem, viewMatrix);
		}
		// render player
		if (playerTileItem != null) {
			renderTileItem(playerTileItem, viewMatrix);
		}
		// render foreground 0
		for (TileItem tileItem : mapTileItems) {
			renderTileItem(tileItem, viewMatrix, 2);
		}
		// render foreground 1
		for (TileItem tileItem : mapTileItems) {
			renderTileItem(tileItem, viewMatrix, 3);
		}

		shaderProgram.unbind();
	}

	private void renderTileItem(TileItem tileItem, Matrix4f viewMatrix) {
		for (GameItem gameItem : tileItem.getItems()) {
			if (gameItem != null && gameItem.isVisible()) {
				// Set world matrix for this item
				Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix,MAP_DEPTH);
				shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
				// Render the mesh for this game item
				shaderProgram.setUniform("colour", gameItem.getMesh().getColour());
			    shaderProgram.setUniform("useColour", gameItem.getMesh().isTextured() ? 0 : 1);
				gameItem.getMesh().render();
			}
		}
	}

	private void renderTileItem(TileItem tileItem, Matrix4f viewMatrix, int i) {
		GameItem gameItem = tileItem.getItems().get(i);
		if (gameItem != null && gameItem.isVisible()) {
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix,MAP_DEPTH);
			shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
			// Render the mesh for this game item
			shaderProgram.setUniform("colour", gameItem.getMesh().getColour());
		    shaderProgram.setUniform("useColour", gameItem.getMesh().isTextured() ? 0 : 1);
			gameItem.getMesh().render();
		}
	}

	private void renderHud(Window window) {

		if (hud != null) {

			shaderProgram.bind();

			shaderProgram.setUniform("projectionMatrix", new Matrix4f());
			
			Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
			for (GameItem gameItem : hud.getGameItems()) {
				if (gameItem != null && gameItem.isVisible()) {
					Mesh mesh = gameItem.getMesh();
					// Set ortohtaphic and model matrix for this HUD item
					Matrix4f projModelMatrix = transformation.getModelViewMatrix(gameItem, ortho, CAMERA_DEPTH);
					/*if(!gameItem.getMesh().isTextured()){
						projModelMatrix = transformation.getModelViewMatrix(gameItem, ortho, CAMERA_DEPTH-0.5f);
					}else{
						projModelMatrix = transformation.getModelViewMatrix(gameItem, ortho, CAMERA_DEPTH-1f);
					}*/
					shaderProgram.setUniform("modelViewMatrix", projModelMatrix);
					shaderProgram.setUniform("colour", gameItem.getMesh().getColour());
					shaderProgram.setUniform("useColour", gameItem.getMesh().isTextured() ? 0 : 1);
					
					// Render the mesh for this HUD item
					mesh.render();
				}
			}
			shaderProgram.unbind();
		}
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
		for (TileItem item : mapTileItems)
			item.cleanup();
		mapTileItems.clear();
	}

	public Texture getTexture(String name) {
		return textures.get(name);
	}

	public HashMap<String, Texture> getTextures() {
		return textures;
	}
	
	public void setTextures(HashMap<String, Texture> textures) {
		this.textures = textures;
	}

}
