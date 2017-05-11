package fr.choicegame.character;

import java.awt.Rectangle;

import fr.choicegame.Config;
import fr.choicegame.Map;
import fr.choicegame.TileImage;
import fr.choicegame.TileType;

public abstract class Character {

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
		if (!walking || (face % 2 == 1 && i % 2 == 1))
			return new TileImage(face * 3 + 1, tileset);
		else
			return new TileImage(face * 3 + (face % 2 == 1 ? i % 4 : 2 * (i % 2)), tileset);
	}

	protected abstract void updateChar(Map m);

	public void update(Map m) {
		if (m != null) {

			float char_spd = Config.getFloatValue(Config.CHARACTER_SPEED);

			updateChar(m);
			if (walking) {

				float newx = getPosX(), newy = getPosY();

				switch (moving) {
				case NORTH:
					newy = getPosY() - char_spd;
					break;
				case SOUTH:
					newy = getPosY() + char_spd;
					break;
				case EAST:
					newx = getPosX() + char_spd;
					break;
				case WEST:
					newx = getPosX() - char_spd;
					break;
				case NORTH_EAST:
					newy = (float) (getPosY() - char_spd / Math.sqrt(2));
					newx = (float) (getPosX() + char_spd / Math.sqrt(2));
					break;
				case NORTH_WEST:
					newy = (float) (getPosY() - char_spd / Math.sqrt(2));
					newx = (float) (getPosX() - char_spd / Math.sqrt(2));
					break;
				case SOUTH_EAST:
					newy = (float) (getPosY() + char_spd / Math.sqrt(2));
					newx = (float) (getPosX() + char_spd / Math.sqrt(2));
					break;
				case SOUTH_WEST:
					newy = (float) (getPosY() + char_spd / Math.sqrt(2));
					newx = (float) (getPosX() - char_spd / Math.sqrt(2));
					break;
				}

				if (!collision(m, newx, newy)) {
					setPosition(newx, newy);
				}
			}
		}
	}

	private boolean collision(Map m, float newx, float newy) {
		// TODO continue

		float hitboxstartx = Config.getFloatValue(Config.HITBOX_START_X);
		float hitboxstarty = Config.getFloatValue(Config.HITBOX_START_Y);
		float hitboxsizex = Config.getFloatValue(Config.HITBOX_SIZE_X);
		float hitboxsizey = Config.getFloatValue(Config.HITBOX_SIZE_Y);

		int inewx = (int) Math.floor(newx);
		int inewy = (int) Math.floor(newy);
		if (inewx >= 0 && inewy >= 0 && inewx + 1 < m.getWidth() && inewy + 1 < m.getHeight()) {
			for (int dx = 0; dx <= 1; dx += 1) {
				for (int dy = 0; dy <= 1; dy += 1) {
					if (m.getTile(inewx + dx, inewy + dy).getType() != TileType.FLAT) {
						if (new Rectangle.Float(newx + hitboxstartx, newy + hitboxstarty, hitboxsizex, hitboxsizey)
								.intersects(new Rectangle.Float(inewx + dx, inewy + dy, 1f, 1f))) {
							// TODO tile size < 1 like rocks,etc
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
