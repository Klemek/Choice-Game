package fr.choicegame;

import java.awt.Color;
import java.awt.Font;

import fr.choicegame.lwjglengine.GameItem;
import fr.choicegame.lwjglengine.IHud;
import fr.choicegame.lwjglengine.TextItem;
import fr.choicegame.lwjglengine.Window;
import fr.choicegame.lwjglengine.graph.FontTexture;

public class Hud implements IHud {

    private final GameItem[] gameItems;

    private final TextItem infoTextItem, msgTextItem;
    
    private int windowWidth, windowHeight;
    
    private static final String WAIT_CHAR="_";
    
    private float updateTimer = 0f;
    private String[] dialogmsg;
    private int dialogchoice;
    private String dialogvar;
	private static final float UPDATETIME = 0.5f;
    
    private boolean dialog;
    
    public Hud() throws Exception {
    	FontTexture infoFont = new FontTexture(new Font(Config.getValue(Config.FONT_NAME), Font.BOLD, 16), "ISO-8859-1", Color.RED);
    	FontTexture msgFont = new FontTexture(new Font(Config.getValue(Config.FONT_NAME), Font.BOLD, 32), "ISO-8859-1", Color.WHITE);
    	this.infoTextItem = new TextItem("Choice-Game Beta", infoFont);
    	this.msgTextItem = new TextItem("", msgFont);
        gameItems = new GameItem[]{infoTextItem,msgTextItem};
    }
    
    public void setMsg(String msg){
    	this.msgTextItem.setText(msg);
    	this.msgTextItem.setPosition(windowWidth/2-msgTextItem.getWidth()/2,windowHeight/2-msgTextItem.getHeight()/2, 0f);
    }
    
    public void setDialog(String[] dial, String dialvar){
    	this.dialogmsg = dial;
    	this.dialogvar = dialvar;
    	this.dialogchoice = 1;
    	this.dialog = true;
    	
    	this.msgTextItem.setText(getDialogMsg());
    	this.msgTextItem.setPosition(windowWidth/2-msgTextItem.getWidth()/2,windowHeight/2-msgTextItem.getHeight()/2, 0f);
    	
    }
    
    private String getDialogMsg(){
    	String msg = dialogmsg[0];
    	for(int i = 1; i < dialogmsg.length; i++){
    		if(i == dialogchoice){
    			msg+="\n   >"+dialogmsg[i];
    		}else{
    			msg+="\n    "+dialogmsg[i];
    		}
    	}
    	return msg;
    }
    
    public boolean hasDialog() {
		return dialog;
	}

	public void clear(){
    	this.msgTextItem.setText("");
    	this.dialog = false;
    }

    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }

    @Override
    public void updateSize(Window window) {
    	windowWidth = window.getWidth();
    	windowHeight = window.getHeight();
        this.infoTextItem.setPosition(2f, window.getHeight() - 20f, 0);
    	this.msgTextItem.setPosition(windowWidth/2-msgTextItem.getWidth()/2,windowHeight/2-msgTextItem.getHeight()/2, 0f);
    }

    public void up(){
    	if(dialog){
    		dialogchoice--;
    		if(dialogchoice==0){
    			dialogchoice = dialogmsg.length-1;
    		}
    		this.msgTextItem.setText(getDialogMsg());
    	}
    }
    
    public void down(){
    	if(dialog){
    		dialogchoice++;
    		if(dialogchoice==dialogmsg.length){
    			dialogchoice = 1;
    		}
    		this.msgTextItem.setText(getDialogMsg());
    	}
    }
    
	public int getDialogchoice() {
		return dialogchoice;
	}

	public String getDialogvar() {
		return dialogvar;
	}

	public void update(float interval) {
		updateTimer += interval;
		if(updateTimer>UPDATETIME){
			updateTimer -= UPDATETIME;
			if(!dialog){
				String msg = this.msgTextItem.getText();
				if(msg.length()>0){
					if(msg.endsWith(WAIT_CHAR)){
						msg = msg.substring(0,msg.length()-WAIT_CHAR.length());
					}else{
						msg+=WAIT_CHAR;
					}
					this.msgTextItem.setText(msg);
					System.out.println(msg);
				}
			}
		}
	}
}