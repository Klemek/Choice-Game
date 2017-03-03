package fr.choicegame;

import fr.choicegame.event.Event;

public class Tile {
	
	private TileImage[] images = new TileImage[4];
	private Event event;
	private TileType type;
	
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
