package fr.choicegame;

public class Map {
	
	private TileImage[][] map;
	private String name;
	
	public TileImage[][] getMap() {
		return this.map;
	}
	
	public void setMap(TileImage[][] map) {
		this.map = map;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
	
}
