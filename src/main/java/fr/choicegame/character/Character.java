package fr.choicegame.character;

import java.awt.Rectangle;

import fr.choicegame.Map;
import fr.choicegame.TileImage;
import fr.choicegame.TileType;

public abstract class Character {

	private static final float CHAR_SPD = 0.1f;

	private float posX;
	private float posY;
	private Direction facing;
	private Direction moving;
	private String tileset;
	private boolean walking;

	public Character(float posX, float posY, String tileset) {
		this.posX = posX;
		this.posY = posY;
		this.tileset = tileset;
		this.walking = false;
		this.facing = Direction.SOUTH;
	}

	// Getters & Setters

	public void setPosition(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public Direction getFacing() {
		return facing;
	}

	public void setFacing(Direction facing) {
		this.facing = facing;
	}

	public Direction getMoving() {
		return moving;
	}

	public void setMoving(Direction moving) {
		this.moving = moving;
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
		switch (facing) {
		case SOUTH:
			face = 0;
			break;
		case WEST:
			face = 1;
			break;
		case NORTH:
			face = 2;
			break;
		case EAST:
			face = 3;
			break;
		default:
			break;
		}
		if (!walking)
			return new TileImage(face * 3, tileset);
		else
			return new TileImage(face * 3 + 1 + i % 2, tileset);
	}

	public void update(Map m) {

		if (walking) {

			float newx = getPosX(), newy = getPosY();

			switch (moving) {
			case NORTH:
				newy = getPosY() - CHAR_SPD;
				break;
			case SOUTH:
				newy = getPosY() + CHAR_SPD;
				break;
			case EAST:
				newx = getPosX() + CHAR_SPD;
				break;
			case WEST:
				newx = getPosX() - CHAR_SPD;
				break;
			case NORTH_EAST:
				newy = (float) (getPosY() - CHAR_SPD / Math.sqrt(2));
				newx = (float) (getPosX() + CHAR_SPD / Math.sqrt(2));
				break;
			case NORTH_WEST:
				newy = (float) (getPosY() - CHAR_SPD / Math.sqrt(2));
				newx = (float) (getPosX() - CHAR_SPD / Math.sqrt(2));
				break;
			case SOUTH_EAST:
				newy = (float) (getPosY() + CHAR_SPD / Math.sqrt(2));
				newx = (float) (getPosX() + CHAR_SPD / Math.sqrt(2));
				break;
			case SOUTH_WEST:
				newy = (float) (getPosY() + CHAR_SPD / Math.sqrt(2));
				newx = (float) (getPosX() - CHAR_SPD / Math.sqrt(2));
				break;
			}

			if (!collision(m, newx, newy)) {
				setPosition(newx, newy);
			}
		}
	}

	private boolean collision(Map m, float newx, float newy) {
		//TODO continue
		if(newx>=0f && newy>=0f && newx+1f<m.getWidth() && newy+1f<m.getHeight()){
			for(float dx = 0; dx <= 1f; dx+=1f){
				for(float dy = 0; dy <= 1f; dy+=1f){
					if(m.getTile(Math.round(newx+dx),Math.round(newy+dy)).getType() != TileType.FLAT){
						if(new Rectangle.Float(newx, newy,1f,1f).intersects(new Rectangle.Float(Math.round(newx+dx),Math.round(newy+dy),1f,1f))){
							return true;
						}
					}
				}
			}
			return false;
		}
		return true;
	}

}
