package fr.choicegame;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	private Game game;
	private GamePanel gpanel;
	
	
	public Window(Loader loader){
		this.setTitle("Choice Game");
		this.setSize(600, 400); //TODO choose window sizes
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//this.game = new Game();
		
		this.gpanel = new GamePanel(null);
		
		this.setContentPane(this.gpanel);
		
		this.setVisible(true);
	}
	
	
}
