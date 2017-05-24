package fr.choicegame.lwjglengine;

public interface IHud {

	GameItem[] getGameItems();

    default void cleanup() {
        GameItem[] gameItems = getGameItems();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
    
    void updateSize(Window window);
	
}
