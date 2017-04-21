package fr.choicegame.jframeengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.Timer;

import fr.choicegame.Game;
import fr.choicegame.Loader;
import fr.choicegame.Map;
import fr.choicegame.TileImage;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Game game;
	private HashMap<String, BufferedImage> assets;
	private int animationTick;

	public GamePanel(Loader loader, Game game) {

		this.game = game;

		this.assets = loader.loadGameAssets();
		
		Timer refresh = new Timer(20, new ActionListener() {
			
			private long t0;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GamePanel.this.repaint();
				long t1 = System.currentTimeMillis();
				if(t1-t0>1000){
					t0 = t1;
					animationTick++;
				}
			}
		});
		refresh.start();

	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (this.game != null && this.game.getCurrentMap() != null) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			Map m = game.getCurrentMap();
			//Background
			for(int x = 0; x < m.getWidth(); x++){
				for(int y = 0; y < m.getHeight(); y++){
					paintTile(g2, m.getTile(x, y).getImages()[0], x*32, y*32, 32);
					paintTile(g2, m.getTile(x, y).getImages()[1], x*32, y*32, 32);
				}
			}
			//Characters
			//Foreground
			for(int x = 0; x < m.getWidth(); x++){
				for(int y = 0; y < m.getHeight(); y++){
					paintTile(g2, m.getTile(x, y).getImages()[2], x*32, y*32, 32);
					paintTile(g2, m.getTile(x, y).getImages()[3], x*32, y*32, 32);
				}
			}
		} else {
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

	}
	
	private void paintTile(Graphics2D g2, TileImage ti, int x, int y, int size){
		if(ti != null && assets.containsKey("/tilesets/"+ti.getTileset()+".png")){
			BufferedImage img = assets.get("/tilesets/"+ti.getTileset()+".png");
			int nc = img.getWidth()/32;
			int ix = ti.getId()%nc;
			int iy = ti.getId()/nc;
			g2.drawImage(img, x, y, x+size, y+size, ix*32, iy*32, (ix+1)*32, (iy+1)*32, this);
		}
	}

}
