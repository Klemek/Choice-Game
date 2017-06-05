package fr.choicegame.lwjglengine;

public interface IHud {

	GameItem[] getGameItems();

    default void cleanup() {
        GameItem[] gameItems = getGameItems();
        for (GameItem gameItem : gameItems) {
        	if(gameItem != null)
        		gameItem.getMesh().cleanUp();
        }
    }
    
    void updateSize(Window window);
	
}
