package gioco.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import gioco.GameLoop;
import gioco.controller.PlayerController;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class WindowsHandler {
	
	private  JFrame f;
	private  Menu menu;
	//private static  MapChooser mapChooser;
	private  GamePanel gamePanel;
	private String map = "Map1";
	private int width = 825;
	private int height = 750;
	private  GameLoop gl;
	 
	private static WindowsHandler windowsHandler = null;
	public static WindowsHandler getWindowsHandler() {
		if (windowsHandler == null) {
			windowsHandler = new WindowsHandler();
		}
		return windowsHandler;
	}
	
	private WindowsHandler() {
		Resources.loadResources();
		f = new JFrame("BOMBERMAN PROVA");	
		f.setUndecorated(false);		
		f.getContentPane().setPreferredSize(new Dimension(825, 750));
		f.pack();
		try {
			Resources.loadWindowIcon();
			f.setIconImage(Resources.iconWindow);
		} catch (Exception e) {
			
		}	
		Settings.BLOCKSIZEX = 825/15;
		Settings.BLOCKSIZEY = 715/13;
		f.setResizable(false);
		f.setLocationRelativeTo( null );
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMenu();
	}
	
	public void setMenu() {
		interruptGame();
		menu = new Menu(825,750);
		f.getContentPane().removeAll();
		f.revalidate();
		f.add(menu);
		f.revalidate();
		f.repaint();
		menu.setFocusable(true);
		menu.requestFocus();
	}
	
	public void setGamePanel(boolean multi , boolean battleRoyale) {
		gamePanel = new GamePanel(width, height);
		f.getContentPane().removeAll();
		f.validate();
		f.add(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		f.revalidate();
		f.repaint();
		PlayerController pc = new PlayerController(gamePanel, multi, battleRoyale ,  map);
		gamePanel.setController(pc);
		gl = new GameLoop(pc);
		gl.start();
	}
	
	public  void interruptGame() {
		if(gl != null)
			gl.setRunning (false);
	}


	public String getMap() {
		return map;
	}

	public  void setMap(String map) {
		this.map = map;
	}
	
	
	
}
