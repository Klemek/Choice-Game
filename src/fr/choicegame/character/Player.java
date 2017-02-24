package fr.choicegame.character;

import fr.choicegame.Item;

public class Player extends Character {
	
	private Item[] inventory;
	
	public Item[] getInventory() {
		return inventory;
	}

	public void setInventory(Item[] inventory) {
		this.inventory = inventory;
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
