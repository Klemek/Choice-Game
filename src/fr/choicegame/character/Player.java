package fr.choicegame.character;

import java.util.HashMap;
import fr.choicegame.Item;

public class Player extends Character {
	
	private HashMap<Item, Integer> inventory;
	
	public Player() {
		
	}

	// Getters & Setters
	
	public HashMap<Item, Integer> getInventory() {
		return inventory;
	}

	public void setInventory(HashMap<Item, Integer> inventory) {
		this.inventory = inventory;
	}

	// Functions
	
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
