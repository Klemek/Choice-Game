package fr.choicegame.character;

import java.util.HashMap;
import fr.choicegame.Item;

public class Player extends Character {
	
	private HashMap<Item, Integer> inventory;
	
	public Player(float posX, float posY, String tileset) {
		super(posX, posY, tileset);
		inventory = new HashMap<>();
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
