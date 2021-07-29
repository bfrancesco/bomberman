package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gioco.controller.PlayerController;
import gioco.model.Block;
import gioco.utilities.Settings;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -4664820033827585199L;

	private PlayerController controller;
	private Image map;
	private ChangeablePanel notStatics;

	public GamePanel(PlayerController controller) {
		this.controller = controller;
		try {
			map = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/maps/" + controller.getGioco().getMap()+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setOpaque(false);
		notStatics = new ChangeablePanel(controller);
		setLayout(new BorderLayout());
		this.add(notStatics);

	}
	


	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(map.getScaledInstance(Settings.BLOCKSIZEX*controller.getGioco().getWidth(), Settings.BLOCKSIZEX*controller.getGioco().getHeight(), Image.SCALE_SMOOTH),this.getX() , this.getY(), null);
	}

	public ChangeablePanel getNotStatics() {
		return notStatics;
	}
	
	
}
