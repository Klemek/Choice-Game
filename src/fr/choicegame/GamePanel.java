package fr.choicegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Game game;

	public GamePanel(Loader loader, Game game) {

		this.game = game;

		Timer refresh = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GamePanel.this.repaint();
			}
		});
		refresh.start();

	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (this.game != null) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			// TODO tile displaying
		} else {
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

	}

}
