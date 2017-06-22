package fr.choicegame;

import java.awt.Rectangle;
import java.util.HashMap;
import fr.choicegame.character.NPC;
import fr.choicegame.character.Player;
import fr.choicegame.event.GameEventListener;

public class Map {
	
	private Tile[][] tileMap;
	private HashMap<String, NPC> npcs;
	
	public Map(int sizex, int sizey) {
		tileMap = new Tile[sizex][sizey];
		for(int x = 0; x < sizex; x++) {
			for(int y = 0; y < sizey; y++) {
				tileMap[x][y] = Tile.EMPTY;
			}
		}
		npcs = new HashMap<>();
	}
		
	public void setGameEventListener(GameEventListener listener) {
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				if(tileMap[x][y].getEvent() != null) {
					tileMap[x][y].getEvent().setListener(listener);
				}
			}
		}
		
		for(NPC npc : npcs.values()){
			npc.getEvent().setListener(listener);
		}
	}
	
	public void setTile(int x, int y, Tile tile) {
		tileMap[x][y] = tile;
	}
	
	public Tile getTile(int x, int y){
		return tileMap[x][y];
	}
	
	public void action(int x, int y, boolean collide){
		
		//TODO detect npc at this case
		if(!collide){
			Rectangle tile = new Rectangle(x,y,1,1);
			for(NPC npc : npcs.values()){
				if(tile.intersects(npc.getHitBox())){
					npc.getEvent().action(x, y, collide);
					break;
				}
			}
		}
		
		if(x>=0 && x<this.getWidth() && y>=0 && y<this.getHeight()){
			tileMap[x][y].action(x, y, collide);
		}
	}
	
	public void npcUpdate(Player p){
		for(NPC npc : npcs.values()){
			npc.update(this, p);
		}
	}
	
	public int getWidth() {
		return tileMap.length;
	}
	
	public int getHeight() {
		return tileMap[0].length;
	}

	public HashMap<String, NPC> getNpcs() {
		return npcs;
	}
	
	public NPC getNpc(String name){
		if(!npcs.containsKey(name))
			System.out.println("#Unkown npc : "+name);
		return npcs.get(name);
	}

	public void setNpcs(HashMap<String, NPC> npcs) {
		this.npcs = npcs;
	}
	
	
}
