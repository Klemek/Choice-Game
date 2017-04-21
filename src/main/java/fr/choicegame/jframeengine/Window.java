package fr.choicegame.jframeengine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import fr.choicegame.Game;
import fr.choicegame.Loader;
import fr.choicegame.Game.UserEvent;

public class Window extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	private Game game;
	private GamePanel gpanel;

	public Window(Loader loader) {
		this.setTitle("Choice Game");
		this.setSize(600, 400); // TODO choose window sizes
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.game = new Game(loader, null);

		this.gpanel = new GamePanel(loader, game);

		this.setContentPane(this.gpanel);

		this.addKeyListener(this);
		
		
		this.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_E:
			game.onUserEvent(UserEvent.ACTION, false);
			break;
		case KeyEvent.VK_LEFT:
			game.onUserEvent(UserEvent.LEFT, false);
			break;
		case KeyEvent.VK_RIGHT:
			game.onUserEvent(UserEvent.RIGHT, false);
			break;
		case KeyEvent.VK_UP:
			game.onUserEvent(UserEvent.UP, false);
			break;
		case KeyEvent.VK_DOWN:
			game.onUserEvent(UserEvent.DOWN, false);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_E:
			game.onUserEvent(UserEvent.ACTION, true);
			break;
		case KeyEvent.VK_LEFT:
			game.onUserEvent(UserEvent.LEFT, true);
			break;
		case KeyEvent.VK_RIGHT:
			game.onUserEvent(UserEvent.RIGHT, true);
			break;
		case KeyEvent.VK_UP:
			game.onUserEvent(UserEvent.UP, true);
			break;
		case KeyEvent.VK_DOWN:
			game.onUserEvent(UserEvent.DOWN, true);
			break;
		}
	}

}
