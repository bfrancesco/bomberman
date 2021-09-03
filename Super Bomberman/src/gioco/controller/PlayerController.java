package gioco.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import gioco.WindowsHandler;
import gioco.gui.GamePanel;
import gioco.gui.PausePanel;
import gioco.model.Bomb;
import gioco.model.Enemy;
import gioco.model.Enemy3;
import gioco.model.Explosion;
import gioco.model.Gioco;
import gioco.model.Player;
import gioco.net.Client;
import gioco.net.Protocol;
import gioco.utilities.Settings;

public class PlayerController extends KeyAdapter {
	private GamePanel panel;
	private Gioco gioco;
	private boolean multiplayer;
	private boolean paused;
	private boolean battleRoyale;
	private ArrayList<Integer> movements;
	private Client client;

	public PlayerController(GamePanel panel, boolean multi, boolean battleRoyale, int map, Client client) {
		super();
		this.multiplayer = multi;
		this.battleRoyale = battleRoyale;
		paused = false;
		// il client ha già stabilito una connessione e ha già letto una mappa ,
		// altrimenti non potrei avere il controller
		this.client = client;
		if (multiplayer)
			map = client.getMap();
		gioco = new Gioco(multiplayer, battleRoyale, map);

		gioco.inizia();

		this.panel = panel;
		this.movements = new ArrayList<Integer>();
		movements.add(Player.IDLE_DOWN);
		// permette di avere uno sfondo offuscato quando si passa al menu di pausa
		PausePanel glassPane = new PausePanel(this);
		WindowsHandler.getWindowsHandler().getFrame().setGlassPane(glassPane);
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public GamePanel getPanel() {
		return panel;
	}

	public Gioco getGioco() {
		return gioco;
	}

	public boolean isBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(boolean battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

	public void setGioco(Gioco gioco) {
		this.gioco = gioco;
	}
	
	

	/*
	 * @Override public void keyTyped(KeyEvent e) { //if (!game.isStarted())
	 * //return; if (!gioco.isMultiplayer()) { switch (e.getKeyCode()) { case
	 * KeyEvent.VK_LEFT: gioco.movePlayer(gioco.getPlayer1(), Settings.LEFT); break;
	 * case KeyEvent.VK_RIGHT: gioco.movePlayer(gioco.getPlayer1(), Settings.RIGHT);
	 * break; case KeyEvent.VK_UP: gioco.movePlayer(gioco.getPlayer1(),
	 * Settings.UP); break; case KeyEvent.VK_DOWN:
	 * gioco.movePlayer(gioco.getPlayer1(), Settings.DOWN); break; } } }
	 */

	public void keyReleased(KeyEvent e) {
		if (!gioco.isStarted())
			return;
		Integer state;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(paused) {
				setPaused(false);
				WindowsHandler.getWindowsHandler().showGlassPane(false);
			}
			else if (!gioco.isGameOver()) {
				int lastmove = movements.get(0);
				switch (movements.get(movements.size() - 1)) {
				case Player.WALKING_LEFT:
					lastmove = Player.IDLE_LEFT;
					break;
				case Player.WALKING_RIGHT:
					lastmove = Player.IDLE_RIGHT;
					break;
				case Player.WALKING_UP:
					lastmove = Player.IDLE_UP;
					break;
				case Player.WALKING_DOWN:
					lastmove = Player.IDLE_DOWN;
					break;
				}
				movements.clear();
				movements.add(lastmove);

				setPaused(true);
				WindowsHandler.getWindowsHandler().getFrame().getGlassPane().setVisible(true);
				
			} else {
				if (multiplayer) {
					WindowsHandler.getWindowsHandler().setMenu();
				} else
					WindowsHandler.getWindowsHandler().setMapChooser();
			}
		}
		if (!paused && !gioco.isGameOver()) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
			case KeyEvent.VK_SPACE:

				if (multiplayer) {
					client.setBombAdded(true);
				} else
					gioco.addBomb(gioco.getPlayer(Settings.PLAYER1));
				break;
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				state = Player.WALKING_LEFT;
				movements.remove(state);
				movements.set(0, Player.IDLE_LEFT);
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				state = Player.WALKING_RIGHT;
				movements.remove(state);
				movements.set(0, Player.IDLE_RIGHT);
				break;
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				state = Player.WALKING_UP;
				movements.remove(state);
				movements.set(0, Player.IDLE_UP);
				break;
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				state = Player.WALKING_DOWN;
				movements.remove(state);
				movements.set(0, Player.IDLE_DOWN);
				break;

			}
			if (multiplayer)
				gioco.getPlayer(client.getOrderConnection()).setState(movements.get(movements.size() - 1));
			else
				gioco.getPlayer(Settings.PLAYER1).setState(movements.get(movements.size() - 1));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gioco.isGameOver() || paused)
			return;
		Integer state = movements.get(movements.size() - 1);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			state = Player.WALKING_LEFT;
			if (!movements.contains(Player.WALKING_LEFT))
				movements.add(Player.WALKING_LEFT);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			state = Player.WALKING_RIGHT;
			if (!movements.contains(Player.WALKING_RIGHT))
				movements.add(Player.WALKING_RIGHT);
			break;
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			state = Player.WALKING_UP;
			if (!movements.contains(Player.WALKING_UP))
				movements.add(Player.WALKING_UP);
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			state = Player.WALKING_DOWN;
			if (!movements.contains(Player.WALKING_DOWN))
				movements.add(Player.WALKING_DOWN);

			break;
		}
		if (!gioco.isGameOver()) {
			if (multiplayer)
				gioco.getPlayer(client.getOrderConnection()).setState(state);
			else
				gioco.getPlayer(Settings.PLAYER1).setState(state);
		}
	}

	public boolean isMultiplayer() {
		return multiplayer;
	}

	// paused può essere acceduto letto/scritto solo se nessun altro thread
	// (GameLoop) non sta leggendo
	public synchronized boolean isPaused() {
		return paused;
	}

	public synchronized void setPaused(boolean paused) {
		this.paused = paused;
		if(!paused)
			panel.requestFocus();
	}

	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}

	public Client getClient() {
		// TODO Auto-generated method stub
		return client;
	}

	public void readAndUpdate() {
		if (!client.isConnected() || !client.ready()) {
			return;
		}
		Vector<Enemy> enemiesUpdated = new Vector<Enemy>();
		Vector<Bomb> bombsUpdated = new Vector<Bomb>();
		String line = client.read();
		if (line == null) {
			System.out.println("ERROR DURING READING");
			return;
		}
		String content[] = line.split(" ");
		// if(content[0] == Protocol.CONNECTIONERROR)
		// show connection error dialog

		int i = 0;
		while (!content[i].equals(Protocol.ENDCOMUNICATION) || i < content.length - 1) {
			if (content[i].equals(Protocol.PLAYER)) {
				int state = gioco.getPlayer(client.getOrderConnection()).getState();
				if (gioco.isGameOver() || Integer.parseInt(content[i + 4]) == Player.DYING_ENEMY
						|| Integer.parseInt(content[i + 4]) == Player.DYING_EXPLOSION
						|| Integer.parseInt(content[i + 4]) == Player.WINNING) {
					state = Integer.parseInt(content[i + 4]);
					if (Integer.parseInt(content[i + 1]) == client.getOrderConnection()) {
						gioco.setGameOver(true);
					}

				}
				if (Integer.parseInt(content[i + 1]) == client.getOrderConnection())
					gioco.getPlayer(client.getOrderConnection()).update(Integer.parseInt(content[i + 2]),
							Integer.parseInt(content[i + 3]), state, Integer.parseInt(content[i + 5]));
				else {
					gioco.getPlayer(Integer.parseInt(content[i + 1])).update(Integer.parseInt(content[i + 2]),
							Integer.parseInt(content[i + 3]), Integer.parseInt(content[i + 4]),
							Integer.parseInt(content[i + 5]));
				}
				i += 6;
			} else if (content[i].equals(Protocol.ENEMY)) {
				int id = Integer.parseInt(content[i + 1]);
				for (Enemy e : gioco.getEnemies()) {
					if (e.getID() == id) {
						if (e instanceof Enemy3) {
							((Enemy3) e).update(Integer.parseInt(content[i + 2]), Integer.parseInt(content[i + 3]),
									Integer.parseInt(content[i + 4]), Boolean.parseBoolean(content[i + 5]),
									Integer.parseInt(content[i + 6]), Integer.parseInt(content[i + 7]));
							i += 3;
						} else
							e.update(Integer.parseInt(content[i + 2]), Integer.parseInt(content[i + 3]),
									Integer.parseInt(content[i + 4]));
						i += 5;
						enemiesUpdated.add(e);
						break;
					}
				}

			} else if (content[i].equals(Protocol.BOMB)) {
				bombsUpdated.add(new Bomb(Integer.parseInt(content[i + 1]), Integer.parseInt(content[i + 2]),
						Integer.parseInt(content[i + 3]), gioco.getPlayer(Integer.parseInt(content[i + 4]))));
				i += 5;
			}
		}
		gioco.setBombs(bombsUpdated);
		gioco.setEnemies(enemiesUpdated);
		// leggo tempo
	}

	public void sendInfo() {
		if (client.isBombAdded()) {
			client.sendMessage(
					Protocol.BOMBADDED + " " + Protocol.state(gioco.getPlayer(client.getOrderConnection()).getState()));
			client.setBombAdded(false);
		} else
			client.sendMessage(Protocol.state(gioco.getPlayer(client.getOrderConnection()).getState()));
	}

}
