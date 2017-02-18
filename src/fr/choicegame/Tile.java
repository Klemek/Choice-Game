package fr.choicegame;

public class Tile {

	private int id;
	private String tileset;
	private Script script;
	
	public Tile() {
		
	}
	
	public Tile(int id, String tileset, Script script) {
		this.id = id;
		this.tileset = tileset;
		this.script = script;
	}
	
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
	
	public Script getScript() {
		return this.script;
	}
	
	public void setScript(Script script) {
		this.script = script;
	}
	
}
