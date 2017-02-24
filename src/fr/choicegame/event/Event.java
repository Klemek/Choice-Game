package fr.choicegame.event;

public class Event {
	
	boolean repeatable;
	boolean actionned;
	String actions;
	GameEventListener listener;
	
	public Event(String actions, GameEventListener listener) {
		this.actions = actions;
		this.listener = listener;
	}
	
	// Getters & Setters

	public boolean isRepeatable() {
		return repeatable;
	}

	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}

	public boolean isActionned() {
		return actionned;
	}

	public void setActionned(boolean actionned) {
		this.actionned = actionned;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public GameEventListener getListener() {
		return listener;
	}

	public void setListener(GameEventListener listener) {
		this.listener = listener;
	}
	
	// Functions
	
	public void action() {
		listener.EventCalled(actions);
	}
	
}