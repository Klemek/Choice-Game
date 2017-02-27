package fr.choicegame;

import java.util.EventListener;

public class Map {
	
	private Tile[][] tileMap;
	private String name;
	private EventListener listener;
	
	public Map(Loader loader, String name, EventListener listener) {
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
