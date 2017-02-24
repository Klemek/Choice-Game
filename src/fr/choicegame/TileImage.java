package fr.choicegame;

import fr.choicegame.event.Event;

public class TileImage {

	private int id;
	private String tileset;
	
	public TileImage() {
		
	}
	
	public TileImage(int id, String tileset, Event script) {
		this.id = id;
		this.tileset = tileset;
	}
	
	// Getters & Setters
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTileset() {
		return this.tileset;
	}
	
	public void setTileset(String tileset) {
		this.tileset = tileset;
	}

	
}
