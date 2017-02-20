package fr.choicegame;

public class Container {
	
	private Item[] items;
	private boolean opened;
	
	public Item[] getItems() {
		return this.items;
	}
	
	public void setItems(Item[] items) {
		this.items = items;
	}
	
	public boolean isOpened() {
		return this.opened;
	}
	
	public void setOpened(boolean opened) {
		this.opened = opened;
	}

}
