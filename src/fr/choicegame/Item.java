package fr.choicegame;

public class Item {
	
	private String name;
	private int id;
	private TileImage image;
	
	public String getName() {
		return this.name;
	}
	
	// Getters & Setters
	
	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TileImage getImage() {
		return image;
	}

	public void setImage(TileImage image) {
		this.image = image;
	}

}
