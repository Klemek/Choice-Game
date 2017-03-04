package fr.choicegame;

import fr.choicegame.event.GameEventListener;

public class Map {
	
	private Tile[][] tileMap;
	private String name;
	private GameEventListener listener;
	
	public Map(Loader loader, String name, GameEventListener listener) {
		this.name = name;
		this.listener = listener;
	}
	
	// Getters & Setters
	
	public Tile[][] getTileMap() {
		return this.tileMap;
	}
	
	public void setTileMap(Tile[][] tileMap) {
		this.tileMap = tileMap;
	}
	
	
	
	public String getName() {
		return this.name;
	}	
}
