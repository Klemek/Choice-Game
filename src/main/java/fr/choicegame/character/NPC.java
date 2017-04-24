package fr.choicegame.character;

import fr.choicegame.Map;
import fr.choicegame.event.Event;

public class NPC extends Character {
	
	private Event event;
	
	public NPC(float posX, float posY, String tileset, Event event) {
		super(posX, posY, tileset);
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	protected void updateChar(Map m) {
		// TODO IA
	}
	
	

}