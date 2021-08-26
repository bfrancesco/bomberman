package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import gioco.GameLoop;
import gioco.controller.PlayerController;
import gioco.net.Client;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class WindowsHandler {

	private JFrame f;
	private Menu menu;
	// private static MapChooser mapChooser;
	private GamePanel gamePanel;
	private ConnectingView connecting;
	private String map = "Map1";
	private GameLoop gl;

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
		Settings.BLOCKSIZEX = 825 / 15;
		Settings.BLOCKSIZEY = 715 / 13;
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMenu();
	}

	public synchronized void setMenu() {
		interruptGame();
		menu = new Menu(Settings.WINDOWWIDTH, Settings.WINDOWHEIGHT);
		f.getContentPane().removeAll();
		f.revalidate();
		f.setContentPane(menu);
		f.revalidate();
		f.repaint();
		menu.setFocusable(true);
		menu.requestFocus();
	}

	public synchronized void setConnectingView(boolean battleRoyale) {
		Client client = null;
		client = new Client(battleRoyale);
		connecting = new ConnectingView(client);
		f.setContentPane(connecting);
		f.revalidate();
		connecting.setFocusable(true);
		connecting.requestFocus();
		//il thread può partire perchè la grafica è stata già impostata
		//Consente che sia modificata da un solo Thread
		Thread t = new Thread(client);
		t.start();
		
	}

	//Attenzione : è necessario assicurarsi che la view venga modificata da un solo thread contemporaneamente
	public synchronized void setGamePanel(boolean multi, boolean battleRoyale ,Client client) {
		f.getContentPane().removeAll();
		f.revalidate();
		gamePanel = new GamePanel();	
		f.setContentPane(gamePanel);
		f.revalidate();
		PlayerController pc = new PlayerController(gamePanel, multi, battleRoyale, map, client );
		gamePanel.setController(pc);
		GameLoop gl = new GameLoop(pc);
		gl.start();
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
	}

	public void interruptGame() {
		if (gl != null)
			gl.setRunning(false);
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

}
