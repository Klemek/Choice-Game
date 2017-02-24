package fr.choicegame.event;

import fr.choicegame.Game;

public class EventComputer implements GameEventListener {
	
	private Game game;
	
	public EventComputer(Game game) {
		this.game = game;
	}
	
	// Functions

	public void EventCalled(String event) {
		
		String actions[] = event.split("\n");
		
		//TODO
	}

}
