package fr.choicegame.character;

import fr.choicegame.TileImage;

public abstract class Character {
	
	private int posX;
	private int posY;
	private Direction facing;
	private String tileset;
	private int animation;
	
	// Getters & Setters
	
	public int getPosX() {
		return posX;
	}
	
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public Direction getFacing() {
		return facing;
	}
	
	public void setFacing(Direction facing) {
		this.facing = facing;
	}
	
	public String getTileset() {
		return tileset;
	}
	
	public void setTileset(String tileset) {
		this.tileset = tileset;
	}
	
	public int getAnimation() {
		return animation;
	}
	
	public void setAnimation(int animation) {
		this.animation = animation;
	}

	public TileImage getImage() {
		int face = 0;
		
		switch(facing) {
			case SOUTH :
				face = 0;
			break;
			case WEST :
				face = 3;
			break;
			case NORTH :
				face = 6;
			break;
			case EAST :
				face = 9;
			break;
		}
		
		return new TileImage(animation + face, tileset);
	}
	
}
