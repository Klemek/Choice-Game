package fr.choicegame;

import java.util.EventListener;

public class Map {
	
	private Tile[][] map;
	private String name;
	private EventListener listener;
	
	public Map(Loader loader, String name, EventListener listener) {
		this.name = name;
		this.listener = listener;
	}
	
	// Getters & Setters
	
	public Tile[][] getMap() {
		return this.map;
	}
	
	public void setMap(Tile[][] map) {
		this.map = map;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	
}
