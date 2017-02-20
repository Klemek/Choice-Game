package fr.choicegame;

public class PC {
	
	private int[] position;
	private Item[] stuff;
	
	public int[] getPosition() {
		return this.position;
	}
	
	public void setPosition(int[] position) {
		this.position = position;
	}
	
	public Item[] getStuff() {
		return this.stuff;
	}
	
	public void moveLeft() {
		this.position[0] -= 1;
	}
	
	public void moveRight() {
		this.position[0] += 1;
	}
	public void moveUp() {
		this.position[1] -= 1;
	}
	public void moveDown() {
		this.position[1] += 1;
	}
	
	public void interact(Item item) {
		
	}
	
	public int choice() {
		return 0;
	}	

}
