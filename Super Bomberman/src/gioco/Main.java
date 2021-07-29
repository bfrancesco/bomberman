package gioco;

import java.util.Random;

import javax.swing.JFrame;

import gioco.controller.PlayerController;
import gioco.gui.GameInterface;
import gioco.model.Gioco;
import gioco.utilities.Settings;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame("BOMBERMAN PROVA");
		f.setSize(700,700);
		Settings.BLOCKSIZEX = f.getWidth()/14;
		Settings.BLOCKSIZEY = f.getHeight()/14;
		GameInterface panel = new GameInterface( f.getSize().height , f.getSize().width);
		panel.setFocusable(true);
		String mapName;
		Random r = new Random();
		if(!r.nextBoolean())
			 mapName = "Map1";
		else mapName = "Map2";
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
