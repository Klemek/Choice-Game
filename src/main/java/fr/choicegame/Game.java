package fr.choicegame;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

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

	private float wait_time_left;
	private boolean wait_input, wait, player_stopped, shouldClose;

	public Game(Loader loader, JFrame splash) {

		this.splash = splash;

		this.triggers = new HashMap<>();
		this.globvars = new HashMap<>();

		this.maps = new HashMap<>();

		evComputer = new EventComputer(this);
		
		hud = new Hud();

		renderer = new Renderer(loader);

		if (loader != null) {

			this.loadMaps(loader);
			
			String startMap = Config.getValue(Config.START_MAP);

			if (startMap != null && this.maps.containsKey(startMap)) {
				
				this.setCurrentMap(startMap);

				String player_tileset = Config.getValue(Config.PLAYER_TILESET);

				if (player_tileset != null)
					player = new Player(0f, 0f, player_tileset);
			}
		}
	}

	private void loadMaps(Loader loader){
		ArrayList<String> files = loader.getTextResourcesNames();
		for(String file:files){
			if(file.endsWith(".tmx")){
				String mapName = Loader.getFileName(file);
				this.maps.put(mapName, loader.loadMap(mapName));
				System.out.println("Map '"+mapName+"' loaded");
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

	public boolean hasGlobalVariable(String varname) {
		return this.globvars.containsKey(varname);
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
		setCurrentMap(name,0,0);
	}
	
	public void setCurrentMap(String name, int x, int y) {
		if (maps.containsKey(name)) {
			this.currentMap = name;
			this.wait = false;
			this.wait_input = false;
			this.wait_time_left = 0f;
			getCurrentMap().setGameEventListener(evComputer);
			if(this.player != null){
				this.player.setPosition(x,y);
				if(hud != null){//all loaded
					player.updateChar(getCurrentMap(),true);
					renderer.updateMap(getCurrentMap());
				}
			}
		}else{
			System.out.println("Unknown map : '"+name+"'");
		}
	}

	public EventComputer getEventComputer() {
		return evComputer;
	}

	public void updateMap() {
		renderer.updateMap(getCurrentMap());
	}

	public void interact() {
		if (player != null && !wait_input && !player_stopped) {

				int posX = (int) Math.round(player.getPosX()), posY = (int) Math.round(player.getPosY());
				switch (player.getFacing()) {
				case NORTH:
					maps.get(currentMap).action(posX, posY - 1, false);
					break;
				case EAST:
					maps.get(currentMap).action(posX + 1, posY, false);
					break;
				case SOUTH:
					maps.get(currentMap).action(posX, posY + 1, false);
					break;
				case WEST:
					maps.get(currentMap).action(posX - 1, posY, false);
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

		System.out.println("Creating hud...");
		hud.init(window,renderer.getTextures());
		renderer.setHud(hud);
		
		System.out.println("Finished loading");
		
		splash.setVisible(false); // end of loading
		
		if(getCurrentMap()!=null && player!=null){
			player.updateChar(getCurrentMap(),true);
			renderer.updateMap(getCurrentMap());
			if (player != null) {
				renderer.updateCharacters(0, player, getCurrentMap());
			}
		}
		
		
	}

	@Override
	public void keyEvent(int key, int action, int mods) {
		
		if(action == GLFW_PRESS){
			
			if(key == GLFW_KEY_ESCAPE){
				if(hud.hasMenu()){
					hud.clearMenu();
					if(!hud.hasMsg())
						setWaitInput(false);
				}else{
					hud.openMenu();
					setWaitInput(true);
				}
			}
			
			if(wait_input){
				if(hud.hasDialog() || hud.hasMenu()){
					if(key == GLFW_KEY_UP || key == GLFW_KEY_W){
						hud.up();
					}else if(key == GLFW_KEY_DOWN || key == GLFW_KEY_S){
						hud.down();
					}else if(key == GLFW_KEY_E || key == GLFW_KEY_SPACE || key == GLFW_KEY_ENTER){
						if(hud.hasDialog()){
							wait_input = false;
							hud.clearMsg();
							
							if(globvars.containsKey(hud.getDialogvar())){
								globvars.put(hud.getDialogvar(), hud.getDialogchoice());
							}else{
								evComputer.setTempVar(hud.getDialogvar(),hud.getDialogchoice());
							}
							evComputer.resume();
						}else{
							hud.clearMenu();
							if(!hud.hasMsg())
								wait_input = false;
							if(hud.getMenuChoice()==2){
								shouldClose = true;
							}
						}
					}
				}else if(key == GLFW_KEY_E || key == GLFW_KEY_SPACE || key == GLFW_KEY_ENTER){
					wait_input = false;
					hud.clearMsg();
					evComputer.resume();
				}
			}
		}
	}
	
	@Override
	public void input(Window window) {
		// azerty is auto converted to qwerty with lwjgl
		if(!player_stopped && !wait_input && !wait){
		
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
		}else{
			player.setWalking(false);
		}
	}

	@Override
	public void update(float interval) {
		if (player != null) {
			player.update(getCurrentMap());
			renderer.updateCharacters(interval, player, getCurrentMap());
		}
		hud.update(interval);
		
		if(wait_time_left>0){
			wait_time_left -= interval;
			if(wait_time_left<=0){
				wait_time_left = 0;
				wait = false;
				evComputer.resume();
			}
		}else{
			wait = false;
		}
		
	}

	@Override
	public void render(Window window) {
		renderer.render(window);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		hud.cleanup();
	}

	public void setWaitInput(boolean pause) {
		this.wait_input = pause;
	}
	
	public void setStopPlayer(boolean stop){
		this.player_stopped = stop;
	}

	public void setWaitTime(float time, boolean wait){
		this.wait = wait;
		this.wait_time_left = time;
	}
	
	@Override
	public boolean shouldClose() {
		return shouldClose;
	}
	
}
