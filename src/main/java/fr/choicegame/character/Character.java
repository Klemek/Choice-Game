package fr.choicegame.character;

import fr.choicegame.TileImage;

public abstract class Character {
	
	private double posX;
	private double posY;
	private Direction facing;
	private String tileset;
	private boolean walking;
	
	// Getters & Setters
	
	public double getPosX() {
		return posX;
	}
	
	public void setPosX(double posX) {
		this.posX = posX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public void setPosY(double posY) {
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
	
	public boolean isWalking() {
		return walking;
	}
	
	public void setWalking(boolean walking) {
		this.walking = walking;
	}

	public TileImage getImage(int i) {
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
		if(!walking)
			return new TileImage(face*4, tileset);
		else
			return new TileImage(face*4+1+i%2, tileset);
	}
	
}
