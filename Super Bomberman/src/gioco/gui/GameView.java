package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import gioco.controller.PlayerController;
import gioco.model.Block;
import gioco.utilities.Settings;

public class GameView extends JPanel {

	private static final long serialVersionUID = -4664820033827585199L;

	private PlayerController controller;
	private Image map;
	private EntitiesPanel notStatics;

	public GameView(PlayerController controller , int h , int w) {
		this.controller = controller;
		this.setPreferredSize(new Dimension(w, h));
		try {
			map = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/maps/" + controller.getGioco().getMap()+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setOpaque(false);
		notStatics = new EntitiesPanel(controller);
		this.add(notStatics);
		BoxLayout a = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(a);
		notStatics.setPreferredSize(getPreferredSize());

	}
	


	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(map.getScaledInstance(Settings.BLOCKSIZEX*controller.getGioco().getWidth(), Settings.BLOCKSIZEX*controller.getGioco().getHeight(), Image.SCALE_SMOOTH),0,0, null);
	}

	public EntitiesPanel getNotStatics() {
		return notStatics;
	}
	
	
}
