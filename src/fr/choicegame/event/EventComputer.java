package fr.choicegame.event;

import fr.choicegame.Game;

public class EventComputer implements GameEventListener {
	
	Game game;
	
	public EventComputer(Game game) {
		this.game = game;
	}
	
	// Getters & Setters

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	// Functions

	public void EventCalled(String event) {
		switch(event) {
		}
	}

}
