package fr.choicegame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Splash extends JFrame{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Loader load = (new Splash()).run();
		if(load != null){
			new Window(load);
		}
	}
	
	public Splash(){
		ImageIcon splashImg = new ImageIcon(this.getClass().getResource("/splash.jpg"));
		this.setSize(splashImg.getIconWidth(), splashImg.getIconHeight());
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		JLabel img = new JLabel(splashImg);
		this.getContentPane().add(img);
		this.setVisible(true);
	}
	
	public Loader run(){
		Loader loader = new Loader();
		
		if(loader.load()){
			this.setVisible(false);
			return loader;
		}else{
			this.setVisible(false);
			JOptionPane.showMessageDialog(this, "Couldn't load game assets.",
					"Loading error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	

}
