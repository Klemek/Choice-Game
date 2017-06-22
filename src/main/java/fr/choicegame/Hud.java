package fr.choicegame;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import org.joml.Vector4f;

import fr.choicegame.lwjglengine.DialogItem;
import fr.choicegame.lwjglengine.GameItem;
import fr.choicegame.lwjglengine.IHud;
import fr.choicegame.lwjglengine.TextItem;
import fr.choicegame.lwjglengine.Window;
import fr.choicegame.lwjglengine.graph.FontTexture;
import fr.choicegame.lwjglengine.graph.Material;
import fr.choicegame.lwjglengine.graph.Texture;

public class Hud implements IHud {

	
	private static final String TITLE = "Choice-Game Beta 1.3";
	
	private GameItem[] gameItems;

	private TextItem infoTextItem;

	private TextItem msgTextItem, menuTextItem;
	private GameItem colorFilter;
	private float transp_start, transp_goal, fade_start;
	public static final float DEFAULT_FADE_TIME = 0.5f;
	private float refresh_timer, fade_time;
	private boolean fade;

	private DialogItem dialogBg, menuBg;

	private int windowWidth, windowHeight;

	private float updateTimer = 0f;
	private String[] dialogmsg, menumsg;
	private int dialogchoice, menuchoice;
	private String dialogvar;
	private static final float UPDATETIME = 0.5f;

	private int msgFontSize = 25;
	
	private boolean dialog, menu;
	
	public void init(Window window,HashMap<String, Texture> textures) throws Exception{
		FontTexture infoFont = new FontTexture(new Font(Config.getValue(Config.FONT_NAME), Font.BOLD, 16), "ISO-8859-1",
				Color.RED);
		FontTexture msgFont = new FontTexture(new Font(Config.getValue(Config.FONT_NAME), Font.BOLD, msgFontSize), "ISO-8859-1",
				Color.WHITE);
		this.infoTextItem = new TextItem(TITLE, infoFont);
		
		this.msgTextItem = new TextItem("", msgFont);
		this.msgTextItem.setVisible(false);
		
		this.colorFilter = GameItem.simpleQuad(new Material(new Vector4f(0,0,0,1)),2000f);
		this.colorFilter.setVisible(false);
		
		this.dialogBg = new DialogItem(textures.get("/"+Config.getValue(Config.TILESETS_FOLDER)+"/"+Config.getValue(Config.DIALOG_TILESET)+".png"),20,6);
		this.dialogBg.setScale(2f);
		this.dialogBg.setVisible(false);
		
		this.menuBg = new DialogItem(textures.get("/"+Config.getValue(Config.TILESETS_FOLDER)+"/"+Config.getValue(Config.DIALOG_TILESET)+".png"),6,5);
		this.menuBg.setScale(2f);
		this.menuBg.setVisible(false);
		
		this.menuTextItem = new TextItem("", msgFont);
		this.menuTextItem.setVisible(false);
		
		gameItems = new GameItem[] {colorFilter, dialogBg, msgTextItem, menuBg, menuTextItem, infoTextItem};
		this.updateSize(window);
	}

	public void setColorFilter(float r, float g, float b, float a, float fade_time){
		if(fade_time <= 0){
			this.colorFilter.getMesh().getMaterial().setColor(new Vector4f(r,g,b,a));
			this.colorFilter.setVisible(true);
		}else{
			this.transp_start = this.colorFilter.getMesh().getMaterial().getColor().w;
			this.transp_goal = a;
			this.fade_start = refresh_timer;
			this.fade = true;
			this.colorFilter.getMesh().getMaterial().setColor(new Vector4f(r,g,b,transp_start));
			this.colorFilter.setVisible(true);
			this.fade_time = fade_time;
		}
	}
	
	public void clearColorFilter(float fade_time){
		if(fade_time <= 0){
			this.colorFilter.setVisible(false);
			this.colorFilter.getMesh().getMaterial().setColor(new Vector4f(0f,0f,0f,0f));
		}else{
			this.transp_goal = 0f;
			this.transp_start = this.colorFilter.getMesh().getMaterial().getColor().w;
			this.fade_start = refresh_timer;
			this.fade = true;
			this.fade_time = fade_time;
		}
	}
	
	public void setMsg(String msg) {
		this.msgTextItem.setVisible(true);
		this.dialogBg.setVisible(true);
		this.msgTextItem.setText(msg);
		updatePos();
	}

	public void setDialog(String[] dial, String dialvar) {
		this.dialogmsg = dial;
		this.dialogvar = dialvar;
		this.dialogchoice = 1;
		this.dialog = true;
		this.msgTextItem.setVisible(true);
		this.dialogBg.setVisible(true);
		this.dialogBg.showCursor(false);
		this.msgTextItem.setText(getDialogMsg(dial,dialogchoice,"\t"));
		updatePos();
	}
	
	public void openMenu(){
		this.menumsg = new String[]{"Menu","Continuer","Quitter"};
		this.menuchoice = 1;
		this.menu = true;
		this.menuTextItem.setVisible(true);
		this.menuBg.setVisible(true);
		this.menuTextItem.setText(getDialogMsg(menumsg,menuchoice," "));
		updatePos();
	}

	private String getDialogMsg(String[] vals, int choice, String space) {
		String msg = vals[0];
		for (int i = 1; i < vals.length; i++) {
			if (i == choice) {
				msg += "\n"+space+"> " + vals[i];
			} else {
				msg += "\n"+space+"   " + vals[i];
			}
		}
		return msg;
	}

	public boolean hasDialog() {
		return dialog;
	}
	
	public boolean hasMsg(){
		return  dialogBg.isVisible();
	}
	
	public boolean hasMenu(){
		return menu;
	}

	public void clearMsg() {
		this.msgTextItem.setText("");
		this.msgTextItem.setVisible(false);
		this.dialogBg.setVisible(false);
		this.dialog = false;
	}
	
	public void clearMenu() {
		this.menuBg.setVisible(false);
		this.menuTextItem.setVisible(false);
		this.menuTextItem.setText("");
		this.menu = false;
	}

	@Override
	public GameItem[] getGameItems() {
		return gameItems;
	}

	@Override
	public void updateSize(Window window) {
		windowWidth = window.getWidth();
		windowHeight = window.getHeight();
		System.out.println("Window size is now : "+windowWidth+"x"+windowHeight+"px");
		updatePos();
	}

	public void up() {
		if(menu){
			menuchoice--;
			if (menuchoice == 0) {
				menuchoice = menumsg.length - 1;
			}
			this.menuTextItem.setText(getDialogMsg(menumsg,menuchoice," "));
		}else if (dialog) {
			dialogchoice--;
			if (dialogchoice == 0) {
				dialogchoice = dialogmsg.length - 1;
			}
			this.msgTextItem.setText(getDialogMsg(dialogmsg,dialogchoice,"\t"));
		}
	}

	public void down() {
		if(menu){
			menuchoice++;
			if (menuchoice == menumsg.length) {
				menuchoice = 1;
			}
			this.menuTextItem.setText(getDialogMsg(menumsg,menuchoice," "));
		}else if (dialog) {
			dialogchoice++;
			if (dialogchoice == dialogmsg.length) {
				dialogchoice = 1;
			}
			this.msgTextItem.setText(getDialogMsg(dialogmsg,dialogchoice,"\t"));
		}
	}

	public int getDialogchoice() {
		return dialogchoice;
	}
	
	public int getMenuChoice(){
		return menuchoice;
	}

	public String getDialogvar() {
		return dialogvar;
	}

	private void updatePos() {
		this.infoTextItem.setPosition(2f, windowHeight - 20f);
		
		this.colorFilter.setScale(Math.max(windowHeight, windowWidth)/2000f); //can't scale more than times 
		this.colorFilter.setPosition(windowWidth / 2, windowHeight / 2);
		
		//menu
		float cx = windowWidth / 2f;
		float cy = windowHeight / 2f;
		
		this.menuBg.setPosition(cx, cy);

		this.menuTextItem.setMaxWidth(this.menuBg.getWidth()-2*msgFontSize);
		this.menuTextItem.setPosition(cx - this.menuBg.getWidth()/2f + msgFontSize,
				cy - this.menuBg.getHeight()/2f + msgFontSize);

		//msg
		cx = windowWidth / 2f;
		cy = (windowHeight * 19f / 20f)- this.dialogBg.getHeight()/2f;
		
		this.dialogBg.setPosition(cx, cy);

		this.msgTextItem.setMaxWidth(this.dialogBg.getWidth()-2*msgFontSize);
		this.msgTextItem.setPosition(cx - this.dialogBg.getWidth()/2f + msgFontSize,
				cy - this.dialogBg.getHeight()/2f + msgFontSize);
		
		
	}

	public void update(float interval) {
		
		refresh_timer += interval;
		
		if(fade && colorFilter.isVisible()){
			if(refresh_timer-fade_start < fade_time){
				float transp = transp_start+(transp_goal-transp_start)*(refresh_timer-fade_start)/fade_time;
				this.colorFilter.getMesh().getColour().setComponent(3, transp);
			}else{
				fade = false;
				this.colorFilter.getMesh().getColour().setComponent(3, transp_goal);
				if(transp_goal == 0f){
					this.colorFilter.setVisible(false);
				}
			}
		}
		
		updateTimer += interval;
		
		if (updateTimer > UPDATETIME) {
			updateTimer -= UPDATETIME;
			if (!dialog && dialogBg.isVisible()) {
				dialogBg.showCursor(!dialogBg.isCursorShown());
			}
		}
	}
}