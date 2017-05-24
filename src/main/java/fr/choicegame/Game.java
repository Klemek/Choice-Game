package fr.choicegame;

import java.util.HashMap;

import javax.swing.JFrame;

import static org.lwjgl.glfw.GLFW.*;

import fr.choicegame.character.Direction;
import fr.choicegame.character.Player;
import fr.choicegame.event.EventComputer;
import fr.choicegame.lwjglengine.IGameLogic;
import fr.choicegame.lwjglengine.Window;
import fr.choicegame.lwjglengine.Window.KeyEventListener;

public class Game implements IGameLogic, KeyEventListener {

	private HashMap<String, Boolean> triggers;
	private HashMap<String, Integer> globvars;

	private EventComputer evComputer;
	private HashMap<String, Map> maps;

	private String currentMap;

	private Player player;

	private final Renderer renderer;
	private Hud hud;
	private final JFrame splash;

	private boolean paused;

	public Game(Loader loader, JFrame splash) {

		this.splash = splash;

		this.triggers = new HashMap<>();
		this.globvars = new HashMap<>();

		this.maps = new HashMap<>();

		evComputer = new EventComputer(this);

		renderer = new Renderer(loader);

		if (loader != null) {

			String startMap = Config.getValue(Config.START_MAP);

			if (startMap != null) {

				// TODO load all maps
				this.maps.put(startMap, loader.loadMap(startMap));

				this.setCurrentMap(startMap);

				String player_tileset = Config.getValue(Config.PLAYER_TILESET);

				if (player_tileset != null)
					player = new Player(0f, 0f, player_tileset);
			}
		}
	}

	public boolean getTrigger(String trigname) {
		if (this.triggers.containsKey(trigname))
			return this.triggers.get(trigname);
		else
			return false;
	}

	public void setTrigger(String trigname, boolean value) {
		this.triggers.put(trigname, value);
	}

	public int getGlobalVariable(String varname) {
		if (this.globvars.containsKey(varname))
			return this.globvars.get(varname);
		else
			return 0;
	}

	public void setGlobalVariable(String varname, int value) {
		this.globvars.put(varname, value);
	}

	public Map getCurrentMap() {
		return this.maps.get(this.currentMap);
	}

	public Player getPlayer() {
		return player;
	}

	public Hud getHud() {
		return hud;
	}

	public void setCurrentMap(String name) {
		if (maps.containsKey(name)) {
			this.currentMap = name;
			getCurrentMap().setGameEventListener(evComputer);
		}
	}

	public EventComputer getEventComputer() {
		return evComputer;
	}

	public void updateMap() {
		renderer.updateMap(getCurrentMap());
	}

	public void interact() {
		if (player != null && !isPaused()) {

				int posX = (int) Math.round(player.getPosX()), posY = (int) Math.round(player.getPosY());
				switch (player.getFacing()) {
				case NORTH:
					maps.get(currentMap).action(posX, posY - 1);
					break;
				case EAST:
					maps.get(currentMap).action(posX + 1, posY);
					break;
				case SOUTH:
					maps.get(currentMap).action(posX, posY + 1);
					break;
				case WEST:
					maps.get(currentMap).action(posX - 1, posY);
					break;
				default:
					break;
				}
		}
	}

	@Override
	public void init(Window window) throws Exception {
		System.out.println("Initializing renderer and Loading textures ...");
		renderer.init(window);
		System.out.println("Creating map...");
		renderer.updateMap(getCurrentMap());
		if (player != null) {
			renderer.updateCharacters(0, player, getCurrentMap());
		}

		hud = new Hud();

		renderer.setHud(hud);

		System.out.println("Finished loading");
		splash.setVisible(false); // end of loading
	}

	@Override
	public void keyEvent(int key, int action, int mods) {
		if(isPaused() && action == GLFW_PRESS){
			
			if(hud.hasDialog()){
				
				if(key == GLFW_KEY_UP || key == GLFW_KEY_W){
					hud.up();
				}else if(key == GLFW_KEY_DOWN || key == GLFW_KEY_S){
					hud.down();
				}else if(key == GLFW_KEY_E || key == GLFW_KEY_SPACE || key == GLFW_KEY_ENTER){
					setPaused(false);
					hud.clear();
					evComputer.setTempVar(hud.getDialogvar(),hud.getDialogchoice());
					evComputer.resume();
				}
				
			}else if(key == GLFW_KEY_E || key == GLFW_KEY_SPACE || key == GLFW_KEY_ENTER){
				setPaused(false);
				hud.clear();
				evComputer.resume();
			}
		}
	}
	
	@Override
	public void input(Window window) {
		// azerty is auto converted to qwerty with lwjgl
		if(!isPaused()){
		
			boolean up = window.isKeyPressed(GLFW_KEY_UP) || window.isKeyPressed(GLFW_KEY_W);
			boolean down = window.isKeyPressed(GLFW_KEY_DOWN) || window.isKeyPressed(GLFW_KEY_S);
			boolean left = window.isKeyPressed(GLFW_KEY_LEFT) || window.isKeyPressed(GLFW_KEY_A);
			boolean right = window.isKeyPressed(GLFW_KEY_RIGHT) || window.isKeyPressed(GLFW_KEY_D);
			
			if (player != null) {
				
				if(window.isKeyPressed(GLFW_KEY_E)){
					interact();
				}
				
				player.setWalking((up ^ down) || (left ^ right));
	
				if (player.isWalking()) {
					if (up && !down) {
						if (left && !right) {
							player.setMoving(Direction.NORTH_WEST);
						} else if (right && !left) {
							player.setMoving(Direction.NORTH_EAST);
						} else {
							player.setMoving(Direction.NORTH);
						}
						player.setFacing(Direction.NORTH);
					} else if (down && !up) {
						if (left && !right) {
							player.setMoving(Direction.SOUTH_WEST);
						} else if (right && !left) {
							player.setMoving(Direction.SOUTH_EAST);
						} else {
							player.setMoving(Direction.SOUTH);
						}
						player.setFacing(Direction.SOUTH);
					} else {
						if (left && !right) {
							player.setMoving(Direction.WEST);
							player.setFacing(Direction.WEST);
						} else if (right && !left) {
							player.setMoving(Direction.EAST);
							player.setFacing(Direction.EAST);
						}
					}
				}
			}
		}
	}

	@Override
	public void update(float interval) {
		if (player != null && !isPaused()) {
			player.update(getCurrentMap());
			renderer.updateCharacters(interval, player, getCurrentMap());
		}
		hud.update(interval);
	}

	@Override
	public void render(Window window) {
		renderer.render(window);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean pause) {
		this.paused = pause;
	}

	
}
