package fr.choicegame;

public class Map {
	
	Tile[][] map;
	String name;
	
	public Tile[][] getMap() {
		return this.map;
	}
	
	public void setMap(Tile[][] map) {
		this.map = map;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
	
}
