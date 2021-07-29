package gioco;

import javax.swing.JFrame;

import gioco.controller.PlayerController;
import gioco.gui.BombermanPanel;
import gioco.model.Gioco;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame("BOMBERMAN PROVA");
		f.setSize(700,700);
		Settings.BLOCKSIZEX = f.getWidth()/14;
		Settings.BLOCKSIZEY = f.getHeight()/14;
		BombermanPanel panel = new BombermanPanel( f.getSize().height , f.getSize().width);
		panel.setFocusable(true);
		String mapName =   "src/gioco/resources/maps/Map1.txt";
		boolean multi = false;
		Gioco gioco = new Gioco(multi, mapName);
		PlayerController controller = new PlayerController(panel, gioco);
		panel.setController(controller);
		f.setResizable(false);
		f.setLocationRelativeTo( null );
		f.add(panel);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameLoop gl = new GameLoop(controller);
		gl.start();
	}
}
