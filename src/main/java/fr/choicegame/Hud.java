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

    private final TextItem statusTextItem;

    public Hud(String statusText) throws Exception {
    	
    	FontTexture fontTexture = new FontTexture(new Font(Config.getValue(Config.FONT_NAME), Font.BOLD, 16), "ISO-8859-1", Color.RED);
    	
    	this.statusTextItem = new TextItem(statusText, fontTexture);
        gameItems = new GameItem[]{statusTextItem};
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }

    public void updateSize(Window window) {
        this.statusTextItem.setPosition(2f, window.getHeight() - 20f, 0);
    }
}