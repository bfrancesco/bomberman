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
		f.setSize(825 , 750);
		f.setUndecorated(true);	
		GameInterface panel = new GameInterface(f.getX() , f.getY());
		Settings.BLOCKSIZEX = 825/15;
		Settings.BLOCKSIZEY =715/13;
		panel.setFocusable(true);
		String mapName;
		Random r = new Random();
		int a = r.nextInt(3)+1;	
		mapName = "Map"+a;
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
