package fr.choicegame.character;

public abstract class Character {
	
	private int posX;
	private int posY;
	Direction facing;
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
	
	// Move
	
	public void move(Direction dir) {
		switch(dir) {
			case NORTH :
				setPosY(getPosY() - 1);
			break;
			case SOUTH :
				setPosY(getPosY() + 1);
			break;
			case EAST :
				setPosX(getPosX() + 1);
			break;
			case WEST :
				setPosX(getPosX() - 1);
			break;
		}
	}
	
}
