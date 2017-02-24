package fr.choicegame.character;

import fr.choicegame.Item;

public class Player extends Character {
	
	private int[] position;
	private Item[] stuff;
	
	public Item[] getStuff() {
		return this.stuff;
	}

	
	public void interact(Item item) {
		
	}
	
	public int choice() {
		return 0;
	}	

}
