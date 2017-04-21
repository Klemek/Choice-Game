package fr.choicegame;

import java.util.HashMap;

import fr.choicegame.character.Direction;
import fr.choicegame.character.Player;
import fr.choicegame.event.EventComputer;

public class Game {

	private HashMap<String, Boolean> triggers;
	private HashMap<String, Integer> globvars;
	
	private EventComputer evComputer;
	private HashMap<String, Map> maps;
	
	private String currentMap;
	
	private Player player;
	
	public enum UserEvent{
		ACTION,
		LEFT,
		RIGHT,
		DOWN,
		UP;
	}
	
	public Game(Loader loader){
		
		this.triggers = new HashMap<>();
		this.globvars = new HashMap<>();
		
		this.maps = new HashMap<>();
		
		evComputer = new EventComputer(this);
		
		
		
		if(loader != null){
			
			this.currentMap = "start"; //Maybe a Constants.java with START_MAP = start ?
		
			this.maps.put(this.currentMap, loader.loadMap(this.currentMap));
		
			//TODO create main character and give it to map
		
		}
		
		
	}
	
	public void onUserEvent(UserEvent e, boolean release){
		switch(e){
		case ACTION:
			break;
		case DOWN:
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		case UP:
			break;
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
	
	public EventComputer getEventComputer(){
		return evComputer;
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
		}
	}
	
	public void move(Direction dir, Map map) {
		switch(dir) {
			case NORTH :
				player.setPosY(player.getPosY() - 1);
			break;
			case SOUTH :
				player.setPosY(player.getPosY() + 1);
			break;
			case EAST :
				player.setPosX(player.getPosX() + 1);
			break;
			case WEST :
				player.setPosX(player.getPosX() - 1);
			break;
			case NORTH_EAST:
				player.setPosY(player.getPosY() - 1/Math.sqrt(2));
				player.setPosX(player.getPosX() + 1/Math.sqrt(2));
			break;
			case NORTH_WEST:
				player.setPosY(player.getPosY() - 1/Math.sqrt(2));
				player.setPosX(player.getPosX() - 1/Math.sqrt(2));
			break;
			case SOUTH_EAST:
				player.setPosY(player.getPosY() + 1/Math.sqrt(2));
				player.setPosX(player.getPosX() + 1/Math.sqrt(2));
			break;
			case SOUTH_WEST:
				player.setPosY(player.getPosY() + 1/Math.sqrt(2));
				player.setPosX(player.getPosX() - 1/Math.sqrt(2));
			break;
		}
	}
}
