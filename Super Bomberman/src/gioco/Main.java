package gioco;

 import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


import gioco.controller.PlayerController;
import gioco.gui.GamePanel;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class Main {

	public static void main(String[] args) {
		/*JFrame f = new JFrame("BOMBERMAN PROVA");	
		int width = 825;
		int height = 750;
		
		f.setUndecorated(false);		
		f.getContentPane().setPreferredSize(new Dimension(825, 750));
		f.pack();
		try {
			Resources.loadWindowIcon();
			f.setIconImage(Resources.iconWindow);
		} catch (Exception e) {
			
		}*/
		WindowsHandler windowsHandler = WindowsHandler.getWindowsHandler();
		//GamePanel panel = new GamePanel(f.getX() , f.getY());
	//	Settings.BLOCKSIZEX = 825/15;
		//Settings.BLOCKSIZEY = 715/13;
		/*panel.setFocusable(true);
		String mapName;
		Random r = new Random();
		int a = r.nextInt(3)+1;	
		mapName = "Map"+a;
		boolean multi = false;
		PlayerController controller = new PlayerController(panel,multi , "Map1");
		panel.setController(controller);
		f.setResizable(false);
		f.setLocationRelativeTo( null );
		f.add(panel);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameLoop gl = new GameLoop(controller);
		gl.start();	*/
	}
}
