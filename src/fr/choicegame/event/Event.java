package fr.choicegame.event;

public class Event {
	
	private String actions;
	private GameEventListener listener;
	
	public Event(String actions, GameEventListener listener) {
		this.actions = actions;
		this.listener = listener;
	}
	
	// Getters & Setters

	public void setListener(GameEventListener listener) {
		this.listener = listener;
	}
	
	// Functions
	
	public void action(int x, int y) {
		this.listener.eventCalled(actions, x, y);
	}
	
}