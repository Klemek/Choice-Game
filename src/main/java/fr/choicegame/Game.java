package fr.choicegame;

import java.util.HashMap;

import javax.swing.JFrame;

import static org.lwjgl.glfw.GLFW.*;

import fr.choicegame.character.Direction;
import fr.choicegame.character.Player;
import fr.choicegame.event.EventComputer;
import fr.choicegame.lwjglengine.IGameLogic;
import fr.choicegame.lwjglengine.Window;



public class Game implements IGameLogic{
	
	private HashMap<String, Boolean> triggers;
	private HashMap<String, Integer> globvars;
	
	private EventComputer evComputer;
	private HashMap<String, Map> maps;
	
	private String currentMap;
	
	private Player player;

	private final Renderer renderer;
	private final JFrame splash;
	
	public Game(Loader loader, JFrame splash){
		
		this.splash = splash;
		
		this.triggers = new HashMap<>();
		this.globvars = new HashMap<>();
		
		this.maps = new HashMap<>();
		
		evComputer = new EventComputer(this);
		
		renderer = new Renderer(loader);
		
		if(loader != null){
			
			this.currentMap = "start"; //Maybe a Constants.java with START_MAP = start ?
		
			this.maps.put(this.currentMap, loader.loadMap(this.currentMap));

			this.setCurrentMap(currentMap);
			
			//TODO create main character and give it to map
		
			player = new Player(0f,0f,"character1");
		}
	}
	
	public boolean getTrigger(String trigname){
		if(this.triggers.containsKey(trigname))
			return this.triggers.get(trigname);
		else
			return false;
	}
	
	public void setTrigger(String trigname, boolean value){
		this.triggers.put(trigname, value);
	}
	
	public int getGlobalVariable(String varname){
		if(this.globvars.containsKey(varname))
			return this.globvars.get(varname);
		else
			return 0;
	}
	
	public void setGlobalVariable(String varname, int value){
		this.globvars.put(varname, value);
	}
	
	public Map getCurrentMap(){
		return this.maps.get(this.currentMap);
	}
	
	public void setCurrentMap(String name){
		if(maps.containsKey(name)){
			this.currentMap = name;
			getCurrentMap().setGameEventListener(evComputer);
		}
	}
	
	public EventComputer getEventComputer(){
		return evComputer;
	}

	public void updateMap() {
		renderer.updateMap(getCurrentMap());
	}
	
	public void action(EventComputer listener) {
		int posX = (int) Math.round(player.getPosX()), posY = (int) Math.round(player.getPosY());
		switch(player.getFacing()) {
			case NORTH :
				maps.get(currentMap).getTile(posX, posY-1).getEvent().action(posX, posY-1);
			break;
			case EAST :
				maps.get(currentMap).getTile(posX+1, posY).getEvent().action(posX+1, posY);
			break;
			case SOUTH :
				maps.get(currentMap).getTile(posX, posY+1).getEvent().action(posX, posY+1);
			break;
			case WEST :
				maps.get(currentMap).getTile(posX-1, posY).getEvent().action(posX-1, posY);
			break;
		default:
			break;
		}
	}

	@Override
	public void init(Window window) throws Exception {
		System.out.println("Initializing renderer and Loading textures ...");
		renderer.init(window);
		System.out.println("Creating map...");
		renderer.updateMap(getCurrentMap());
		renderer.updateCharacters(0, player, getCurrentMap());
		System.out.println("Finished loading");
		splash.setVisible(false); //end of loading
	}

	@Override
	public void input(Window window) {
		//azerty is auto converted to qwerty with lwjgl
		boolean up = window.isKeyPressed(GLFW_KEY_UP) || window.isKeyPressed(GLFW_KEY_W);
		boolean down = window.isKeyPressed(GLFW_KEY_DOWN) || window.isKeyPressed(GLFW_KEY_S);
		boolean left = window.isKeyPressed(GLFW_KEY_LEFT) || window.isKeyPressed(GLFW_KEY_A);
		boolean right = window.isKeyPressed(GLFW_KEY_RIGHT) || window.isKeyPressed(GLFW_KEY_D);
		
		player.setWalking((up ^ down) || (left ^ right));
		
		if(player.isWalking()){
			if(up && !down){
				if(left && !right){
					player.setMoving(Direction.NORTH_WEST);
				}else if(right && !left){
					player.setMoving(Direction.NORTH_EAST);
				}else{
					player.setMoving(Direction.NORTH);
				}
				player.setFacing(Direction.NORTH);
			}else if(down && !up){
				if(left && !right){
					player.setMoving(Direction.SOUTH_WEST);
				}else if(right && !left){
					player.setMoving(Direction.SOUTH_EAST);
				}else{
					player.setMoving(Direction.SOUTH);
				}
				player.setFacing(Direction.SOUTH);
			}else{
				if(left && !right){
					player.setMoving(Direction.WEST);
					player.setFacing(Direction.WEST);
				}else if(right && !left){
					player.setMoving(Direction.EAST);
					player.setFacing(Direction.EAST);
				}
			}
		}
	}

	@Override
	public void update(float interval) {
		//TODO game loop
		player.update(getCurrentMap());
		renderer.updateCharacters(interval, player, getCurrentMap());
	}


	@Override
	public void render(Window window) {
		renderer.render(window);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
	}
}
