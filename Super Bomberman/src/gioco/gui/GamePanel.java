package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gioco.Settings;
import gioco.controller.PlayerController;
import gioco.model.Block;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -4664820033827585199L;

	private PlayerController controller;
	//private Image floor;
	//private Image iron;
	private Image map;
	private ChangeablePanel notStatics;

	public GamePanel(PlayerController controller) {
		this.controller = controller;
		try {
			System.out.println("MIKI");
			//iron = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/blocks/iron.png"));
			//floor = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/blocks/floor.png"));
			map = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/blocks/Map1.png"));
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
		/*for (int i = 0; i < controller.getGioco().getHeight(); i++) {
			for (int j = 0; j < controller.getGioco().getWidth(); j++) {
				switch (controller.getGioco().getElement(i, j).getType()) {
				case Block.IRON:
					g.drawImage(iron.getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_SMOOTH),
							i * Settings.BLOCKSIZEX, j * Settings.BLOCKSIZEY, Settings.BLOCKSIZEX, Settings.BLOCKSIZEY,
							null);
					break;

				default:
					g.drawImage(floor.getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_SMOOTH),
							i * Settings.BLOCKSIZEX, j * Settings.BLOCKSIZEY, Settings.BLOCKSIZEX, Settings.BLOCKSIZEY,
							null);
					break;

				}
			}
		}*/

	}

	public ChangeablePanel getNotStatics() {
		return notStatics;
	}
	
	
}
