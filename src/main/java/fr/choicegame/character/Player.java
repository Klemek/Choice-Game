package fr.choicegame.character;

import java.util.HashMap;

import fr.choicegame.Config;
import fr.choicegame.Map;

public class Player extends Character {
	
	private HashMap<Integer, Integer> inventory;
	private float[] lastPos = {-1f,-1f};
	
	public Player(float posX, float posY, String tileset) {
		super(posX, posY, tileset);
		inventory = new HashMap<>();
	}

	// Getters & Setters
	
	public HashMap<Integer, Integer> getInventory() {
		return inventory;
	}

	// Functions
	
	public void updateChar(Map m, boolean forceEvent) {
		//action events when walked in
		
		if(forceEvent){
			lastPos = new float[]{-1f,-1f};
		}
		
		float hitboxstartx = Config.getFloatValue(Config.HITBOX_START_X);
		float hitboxstarty = Config.getFloatValue(Config.HITBOX_START_Y);
		float hitboxsizex = Config.getFloatValue(Config.HITBOX_SIZE_X);
		float hitboxsizey = Config.getFloatValue(Config.HITBOX_SIZE_Y);
		
		float char_spd = Config.getFloatValue(Config.CHARACTER_SPEED);
		
		float posx = getPosX()+hitboxstartx;
		float posy = getPosY()+hitboxstarty;
		float posx2 = getPosX()+hitboxstartx+hitboxsizex;
		float posy2 = getPosY()+hitboxstarty+hitboxsizey;

		if(lastPos[0] >= 0){
			float dx = getPosX()-lastPos[0];
			float dy = getPosY()-lastPos[1];
			
			if(dx>0){
				if(posx2-(int)posx2<=char_spd){
					m.action((int)posx2,(int)posy, true);
				}
			}else if(dx<0){
				if((int)(posx+1)-posx<=char_spd){
					m.action((int)posx,(int)posy, true);
				}
			}
			
			if(dy>0){
				if(posy2-(int)posy2<=char_spd){
					m.action((int)posx,(int)posy2, true);
				}
			}else if(dy<0){
				if((int)(posy+1)-posy<=char_spd){
					m.action((int)posx,(int)posy, true);
				}
			}
			
			//TODO diagonal trigger + optimize code
			
		}else{ //first appearance in map
			m.action((int)(posx),(int)(posy), true);
			boolean overx = (int)(posx2)>(int)(posx);
			boolean overy = (int)(posy2)>(int)(posy);
			if(overx)
				m.action((int)posx2,(int)posy, true);
			if(overy)
				m.action((int)posx,(int)posy2, true);
			if(overx && overy)
				m.action((int)posx2,(int)posy2, true);
		}
		lastPos = new float[]{getPosX(),getPosY()};
	}
	
	@Override
	protected void updateChar(Map m) {
		updateChar(m,false);
	}
	
}
