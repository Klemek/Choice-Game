package fr.choicegame.event;

public class Event {
	
	private String actions;
	private GameEventListener listener;
	
	public Event(String actions, GameEventListener listener) {
		this.actions = actions;
		this.listener = listener;
	}
	
	public Event(String actions) {
		this(actions, null);
	}
	
	// Getters & Setters

	public void setListener(GameEventListener listener) {
		this.listener = listener;
	}
	
	public String getActions(){
		return actions;
	}
	
	// Functions
	
	public void action(int x, int y, boolean collide) {
		if(listener != null)
			this.listener.eventCalled(actions, x, y, collide);
	}
	
}