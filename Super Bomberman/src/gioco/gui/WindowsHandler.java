package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


import gioco.GameLoop;
import gioco.controller.PlayerController;
import gioco.net.Client;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class WindowsHandler {

	private JFrame f;
	private Menu menu;
	 private static MapChooser mapChooser;
	private GamePanel gamePanel;
	private ConnectingView connecting;
	private GameLoop gl;
	private String map;
	private int selectedBomberman;

	private static WindowsHandler windowsHandler = null;

	public static WindowsHandler getWindowsHandler() {
		if (windowsHandler == null) {
			windowsHandler = new WindowsHandler();
		}
		return windowsHandler;
	}

	private WindowsHandler() {
		Resources.loadResources();
		//Settings.WINDOWWIDTH = 825;
		//Settings.WINDOWHEIGHT = 750;
		f = new JFrame("BOMBERMAN PROVA");
		f.setUndecorated(false);
		f.getContentPane().setPreferredSize(new Dimension(Settings.WINDOWWIDTH,Settings.WINDOWHEIGHT ));
		f.pack();
		try {
			Resources.loadWindowIcon();
			f.setIconImage(Resources.iconWindow);
		} catch (Exception e) {

		}
		
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			if(Client.getClient().isConnected())
				Client.getClient().disconnect();
			System.exit(0);
			super.windowClosing(e);
		}
		});
		setMenu();
	}
	
	public synchronized void setMapChooser() {
		mapChooser = new MapChooser();
		f.getContentPane().removeAll();
		f.revalidate();
		f.setContentPane(mapChooser);
		f.revalidate();
		f.pack();
	}

	public synchronized void setMenu() {
		map = "Map1";
		interruptGame();
		Client.reset();	
		menu = new Menu(Settings.WINDOWWIDTH, Settings.WINDOWHEIGHT);
		f.getContentPane().removeAll();
		f.revalidate();
		f.setContentPane(menu);
		f.revalidate();
		f.repaint();
		menu.setFocusable(true);
		menu.requestFocus();
	}

	//Attenzione : è necessario assicurarsi che la view venga modificata da un solo thread contemporaneamente
	public synchronized void setConnectingView(boolean battleRoyale) {
		Client.getClient().setBattleRoyale(battleRoyale);
		connecting = new ConnectingView(Client.getClient());
		f.setContentPane(connecting);
		f.revalidate();
		connecting.setFocusable(true);
		connecting.requestFocus();
		//il thread può partire perchè la grafica è stata già impostata
		//Consente che sia modificata da un solo Thread
		Thread t = new Thread(Client.getClient());
		t.start();
		
	}

	
	public synchronized void setGamePanel(boolean multi, boolean battleRoyale ,Client client ) {
		f.getContentPane().removeAll();
		f.revalidate();
		gamePanel = new GamePanel();	
		f.setContentPane(gamePanel);
		f.revalidate();
		PlayerController pc = new PlayerController(gamePanel, multi, battleRoyale, map, client );
		gamePanel.setController(pc);
		gl = new GameLoop(pc);
		gl.start();
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
	}

	
	
	public void interruptGame() {
		if (gl != null) {
			gl.setRunning(false);
		}
	}

	public String getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = "Map"+map;
	}
	
	


}
