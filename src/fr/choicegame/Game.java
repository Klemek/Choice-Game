package fr.choicegame;

import java.util.HashMap;

import fr.choicegame.event.EventComputer;

public class Game {

	private HashMap<String, Boolean> triggers;
	private HashMap<String, Integer> globvars;
	
	private EventComputer evComputer;
	private HashMap<String, Map> maps;
	
	private String currentMap;
	
	public Game(Loader loader){
		
		this.triggers = new HashMap<>();
		this.globvars = new HashMap<>();
		
		this.maps = new HashMap<>();
		
		evComputer = new EventComputer(this);
		
		this.currentMap = "start"; //Maybe a Constants.java with START_MAP = start ?
		
		this.maps.put(this.currentMap, loader.loadMap(this.currentMap));
		
		//TODO create main character and give it to map
		
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
}
