package gioco;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import gioco.controller.CreateGameController;
import gioco.controller.PlayerController;
import gioco.gui.ConnectingView;
import gioco.gui.GamePanel;
import gioco.gui.InfoPanel;
import gioco.gui.CreateGamePanel;
import gioco.gui.Menu;
import gioco.gui.MyDialog;
import gioco.net.Client;
import gioco.sound.SoundsHandler;
import gioco.utilities.Resources;
import gioco.utilities.Settings;
/*
 * Singleton, gestisce i vari pannelli dell'applicazione
 * si occupa di sostituire il contenuto del contentpane e quindi il cambio di schermata
 * si occupa del glassPane del JFrame
 * 
 * */
public class WindowsHandler {

	private JFrame f;
	private Menu menu;
	private CreateGamePanel mapChooser;
	private GamePanel gamePanel;
	private ConnectingView connecting;
	private GameLoop gl;
	private InfoPanel infoPanel;
	private Container current;

	private static WindowsHandler windowsHandler = null;

	public synchronized static WindowsHandler getWindowsHandler() {
		if (windowsHandler == null) {
			windowsHandler = new WindowsHandler();
		}
		return windowsHandler;
	}

	// inizializza e crea le varie istanze delle schermate
	// imposta il menu come schermata principale
	private WindowsHandler() {
		Resources.loadResources();
		f = new JFrame("SUPER BOMBERMAN");
		f.setUndecorated(false);
		f.getContentPane().setPreferredSize(new Dimension(Settings.WINDOWWIDTH, Settings.WINDOWHEIGHT));
		f.pack();
		f.setIconImage(Resources.iconWindow);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mapChooser = new CreateGamePanel();
		CreateGameController controller = new CreateGameController(mapChooser);
		mapChooser.addKeyListener(controller);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String message = "Sei sicuro di voler uscire dal gioco?";
				if (showConfirmationDialog("ESCI DAL GIOCO", message) == MyDialog.YES_OPTION) {
					if (Client.getClient().isConnected())
						Client.getClient().disconnect();
					System.exit(0);
				}
				super.windowClosing(e);
			}

		});
		infoPanel = new InfoPanel();
		infoPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					infoPanel.moveBar(-1);
					break;
				case KeyEvent.VK_DOWN:
					infoPanel.moveBar(1);
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (KeyEvent.VK_ESCAPE == e.getKeyCode())
					WindowsHandler.getWindowsHandler().removeInfoPanel();
			}
		});
		menu = new Menu(Settings.WINDOWWIDTH, Settings.WINDOWHEIGHT);
		menu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					String message = "Sei sicuro di voler uscire dal gioco?";
					if (showConfirmationDialog("ESCI DAL GIOCO", message) == MyDialog.YES_OPTION) {
						if (Client.getClient().isConnected())
							Client.getClient().disconnect();
						System.exit(0);
					}
				}
				super.keyReleased(e);
			}
		});
		setMenu();
	}

	//imposta la schermata dellla personalizzazione della mappa 
	public synchronized void setMapChooser() {
		SoundsHandler.getSoundsHandler().noSoundtrack();
		mapChooser.reposition();
		f.setContentPane(mapChooser);
		f.revalidate();
		f.pack();
		current = mapChooser;
		mapChooser.setFocusable(true);
		mapChooser.requestFocus();
		//Serve per mantenere la possibilità di muoversi con le freccie direzionali fra i selezionabili qualora cliccassi sulle componenti
		mapChooser.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				mapChooser.requestFocus();
			}
		});

	}
	//imposta il menu come schermata del content
	public synchronized void setMenu() {
		Client.reset();
		current = menu;
		menu.reset();
		f.setContentPane(menu);
		f.revalidate();
		f.repaint();
		SoundsHandler.getSoundsHandler().setMenuSoundtrack();
		menu.setFocusable(true);
		menu.requestFocus();
	}

	// permette al client di collegarsi, il thread del client adesso gestisce il cambio di scena
	// se un evento di uscita è registrato , allora viene interrotto per evitare che due thread modificano contemporaneamente (Controlle Connecting View )
	public synchronized void setConnectingView(boolean battleRoyale) {
		Client.reset();
		SoundsHandler.getSoundsHandler().noSoundtrack();
		Client.getClient().setBattleRoyale(battleRoyale);
		connecting = new ConnectingView(Client.getClient());
		f.setContentPane(connecting);
		f.revalidate();
		connecting.setFocusable(true);
		connecting.requestFocus();
		Client.getClient().start();
	}

	//prende in input le modalità di gioco e fa partire il gameLoop
	public synchronized void setGamePanel(boolean multi, boolean battleRoyale) {
		SoundsHandler.getSoundsHandler().setGameSoundtrack();
		current = gamePanel;
		gamePanel = new GamePanel();
		f.setContentPane(gamePanel);
		f.revalidate();
		PlayerController pc = new PlayerController(gamePanel, multi, battleRoyale, Settings.selectedMap, Client.getClient());
		gamePanel.setController(pc);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		gl = new GameLoop(pc);
		gl.start();
	}

	//imposta come content il pannello delle informazioni
	public void setInfoPanel() {
		infoPanel.reset();
		f.setContentPane(infoPanel);
		f.revalidate();
		infoPanel.setFocusable(true);
		infoPanel.requestFocus();

	}

	public void removeInfoPanel() {
		f.setContentPane(current);
		f.revalidate();
		current.requestFocus();
	}
	
	//pone il gioco a false , permettendo al gameloop di terminare l'esecuzione
	public void interruptGame() {
		if (gl != null) {
			gl.setRunning(false);
		}
	}
	
	
	//restituisce il JFrame 
	public JFrame getFrame() {
		return f;
	}

	public void showGlassPane(boolean visibile) {
		f.getGlassPane().setVisible(visibile);
	}

	public void resetMapChooser() {
		mapChooser.reset();
	}
	
	//fa vedere il messaggio di conferma tramite un MyDialog
	//resituisce l'opzione scelta MyDialog.YES_OPTION oppure MyDialog.NO_OPTION
	public int showConfirmationDialog(String title, String message) {
		Point p = new Point(
				f.getLocationOnScreen().x + Settings.WINDOWWIDTH / 2,
				f.getLocationOnScreen().y + Settings.WINDOWHEIGHT / 2);
		MyDialog dialog = new MyDialog(new Frame(), title, message, p);
		int confirmed = dialog.showAndWait();
		return confirmed;
	}

}
