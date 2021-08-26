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
	private Image floor;
	private Image iron;

	public GameView(PlayerController controller , int h , int w) {
		this.controller = controller;
		this.setPreferredSize(new Dimension(w, h));
		try {
			map = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/maps/" + controller.getGioco().getMap()+".png"));
			floor = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/blocks/floor.png"));
			iron = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/blocks/iron.png"));
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
		//Block matrix[][] = controller.getGioco().getMatrix();
		g.drawImage(map.getScaledInstance(Settings.BLOCKSIZEX*controller.getGioco().getWidth(), Settings.BLOCKSIZEY*controller.getGioco().getHeight(), Image.SCALE_SMOOTH),0,0, null);
		/*for(int i = 0;i<matrix.length;++i) {
			for(int j = 0;j<matrix[0].length;++j) {
				if(matrix[i][j].getType() == Block.IRON)
					g.drawImage(iron.getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_SMOOTH),j*Settings.BLOCKSIZEX,i*Settings.BLOCKSIZEY, null);
				else g.drawImage(floor.getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_SMOOTH),j*Settings.BLOCKSIZEX,i*Settings.BLOCKSIZEY, null);
			}
		}*/
	}

	public EntitiesPanel getNotStatics() {
		return notStatics;
	}
	
	
}
