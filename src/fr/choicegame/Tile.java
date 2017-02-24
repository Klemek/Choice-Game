package fr.choicegame;

import fr.choicegame.event.Event;

public class Tile {
	
	TileImage[] images = new TileImage[4];
	Event event;
	
	// Getters & Setters
	
	public TileImage[] getImages() {
		return images;
	}
	public void setImages(TileImage[] images) {
		this.images = images;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}	

}
