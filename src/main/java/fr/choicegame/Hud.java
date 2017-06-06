package fr.choicegame;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import org.joml.Vector4f;

import fr.choicegame.lwjglengine.GameItem;
import fr.choicegame.lwjglengine.IHud;
import fr.choicegame.lwjglengine.TextItem;
import fr.choicegame.lwjglengine.Window;
import fr.choicegame.lwjglengine.graph.FontTexture;
import fr.choicegame.lwjglengine.graph.Material;
import fr.choicegame.lwjglengine.graph.Texture;

public class Hud implements IHud {

	private GameItem[] gameItems;

	private TextItem infoTextItem;

	private TextItem msgTextItem;
	private GameItem dialogBg, dialogCursor, colorFilter;

	private int windowWidth, windowHeight;

	//private static final String WAIT_CHAR = "_";

	private float updateTimer = 0f;
	private String[] dialogmsg;
	private int dialogchoice;
	private String dialogvar;
	private static final float UPDATETIME = 0.5f;

	private int msgFontSize = 25;
	
	private boolean dialog;
	
	public void init(Window window,HashMap<String, Texture> textures) throws Exception{
		
		FontTexture infoFont = new FontTexture(new Font(Config.getValue(Config.FONT_NAME), Font.BOLD, 16), "ISO-8859-1",
				Color.RED);
		FontTexture msgFont = new FontTexture(new Font(Config.getValue(Config.FONT_NAME), Font.BOLD, msgFontSize), "ISO-8859-1",
				Color.WHITE);
		this.infoTextItem = new TextItem("Choice-Game Beta", infoFont);
		this.msgTextItem = new TextItem("", msgFont);
		this.msgTextItem.setVisible(false);
		this.colorFilter = GameItem.simpleQuad(new Material(new Vector4f(1, 0, 0, 0.5f)));
		this.colorFilter.setVisible(false);
		this.dialogBg = GameItem.simpleQuad(new Material(textures.get("/dialog.png")));
		this.dialogBg.setVisible(false);
		this.dialogCursor = GameItem.simpleQuad(new Material(textures.get("/cursor.png")));
		this.dialogCursor.setVisible(false);
		gameItems = new GameItem[] {colorFilter, dialogBg, msgTextItem, dialogCursor, infoTextItem };
		this.updateSize(window);
	}

	public void setColorFilter(float r, float g, float b, float a){
		this.colorFilter.getMesh().getMaterial().setColor(new Vector4f(r,g,b,a));
		this.colorFilter.setVisible(true);
	}
	
	public void clearColorFilter(){
		this.colorFilter.setVisible(false);
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
		this.msgTextItem.setText(getDialogMsg());
		updatePos();
	}

	private String getDialogMsg() {
		String msg = dialogmsg[0];
		for (int i = 1; i < dialogmsg.length; i++) {
			if (i == dialogchoice) {
				msg += "\n   >" + dialogmsg[i];
			} else {
				msg += "\n    " + dialogmsg[i];
			}
		}
		return msg;
	}

	public boolean hasDialog() {
		return dialog;
	}

	public void clear() {
		this.msgTextItem.setText("");
		this.msgTextItem.setVisible(false);
		this.dialogBg.setVisible(false);
		this.dialogCursor.setVisible(false);
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
		System.out.println("Window size is : "+windowWidth+"x"+windowHeight+"px");
		updatePos();
	}

	public void up() {
		if (dialog) {
			dialogchoice--;
			if (dialogchoice == 0) {
				dialogchoice = dialogmsg.length - 1;
			}
			this.msgTextItem.setText(getDialogMsg());
		}
	}

	public void down() {
		if (dialog) {
			dialogchoice++;
			if (dialogchoice == dialogmsg.length) {
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

	private void updatePos() {
		this.infoTextItem.setPosition(2f, windowHeight - 20f, 0);
		
		this.colorFilter.setScale(Math.max(windowHeight, windowWidth));
		this.colorFilter.setPosition(windowWidth / 2, windowHeight / 2, 0);
		
		float dialogwidth = this.dialogBg.getRatio()*msgFontSize*7f;
		
		this.dialogBg.setScale(dialogwidth);
		this.dialogBg.setPosition(windowWidth / 2f, (windowHeight * 19f / 20f)-msgFontSize*3.5f, 0);
		
		this.dialogCursor.setScale(-msgFontSize);
		this.dialogCursor.setPosition(windowWidth / 2f + dialogwidth/2f - msgFontSize, (windowHeight * 19f / 20f)-msgFontSize, 0);
		
		this.msgTextItem.setMaxWidth(dialogwidth-2*msgFontSize);
		
		this.msgTextItem.setPosition(windowWidth / 2f - dialogwidth/2f + msgFontSize,
				(windowHeight * 19f / 20f)-msgFontSize*6.5f, 0f);
	}

	public void update(float interval) {
		updateTimer += interval;
		if (updateTimer > UPDATETIME) {
			updateTimer -= UPDATETIME;
			if (!dialog && dialogBg.isVisible()) {
				dialogCursor.setVisible(!dialogCursor.isVisible());
				/*String msg = this.msgTextItem.getText();
				if (msg.length() > 0) {
					if (msg.endsWith(WAIT_CHAR)) {
						msg = msg.substring(0, msg.length() - WAIT_CHAR.length());
					} else {
						msg += WAIT_CHAR;
					}
					this.msgTextItem.setText(msg);
				}*/
			}
		}
	}
}