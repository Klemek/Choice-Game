package fr.choicegame.character;

import java.util.HashMap;

import fr.choicegame.Item;
import fr.choicegame.Map;

public class Player extends Character {
	
	private HashMap<Item, Integer> inventory;
	private float[] lastPos = {-1f,-1f};
	
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
	
	@Override
	protected void updateChar(Map m) {
		//action events when walked in
		float posx = getPosX()+hitboxstart[0];
		float posy = getPosY()+hitboxstart[1];
		float posx2 = getPosX()+hitboxstart[0]+hitboxsize[0];
		float posy2 = getPosY()+hitboxstart[1]+hitboxsize[1];
		if(lastPos[0] >= 0){
			float dx = getPosX()-lastPos[0];
			float dy = getPosY()-lastPos[1];
			
			if(dx>0){
				if(posx2-(int)posx2<=Character.CHAR_SPD){
					m.action((int)posx2,(int)posy);
				}
			}else if(dx<0){
				if((int)(posx+1)-posx<=Character.CHAR_SPD){
					m.action((int)posx,(int)posy);
				}
			}
			
			if(dy>0){
				if(posy2-(int)posy2<=Character.CHAR_SPD){
					m.action((int)posx,(int)posy2);
				}
			}else if(dy<0){
				if((int)(posy+1)-posy<=Character.CHAR_SPD){
					m.action((int)posx,(int)posy);
				}
			}
			
			//TODO diagonal trigger + optimize code
			
		}else{ //first appearance in map
			m.action((int)(posx),(int)(posy));
			boolean overx = (int)(posx2)>(int)(posx);
			boolean overy = (int)(posy2)>(int)(posy);
			if(overx)
				m.action((int)posx2,(int)posy);
			if(overy)
				m.action((int)posx,(int)posy2);
			if(overx && overy)
				m.action((int)posx2,(int)posy2);
		}
		lastPos = new float[]{getPosX(),getPosY()};
	}
	
}
