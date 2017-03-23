package fr.choicegame.character;

import java.util.HashMap;
import fr.choicegame.Item;
import fr.choicegame.Map;
import fr.choicegame.TileType;

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
	
}
