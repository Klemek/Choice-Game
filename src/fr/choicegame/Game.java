package fr.choicegame;

import java.util.HashMap;

import fr.choicegame.event.EventComputer;

public class Game {

	private HashMap<String, Boolean> triggers;
	private HashMap<String, Integer> globvars;
	
	private EventComputer evComputer;
	private Map map;
	
	public Game(Loader loader){
		
		this.triggers = new HashMap<>();
		this.globvars = new HashMap<>();
		
		evComputer = new EventComputer(this);
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
	
	public Map getMap(){
		return map;
	}
	
	public EventComputer getEventComputer(){
		return evComputer;
	}
}
