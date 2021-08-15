package gioco.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Vector;

import gioco.gui.GameInterface;
import gioco.model.Bomb;
import gioco.model.Enemy;
import gioco.model.Enemy3;
import gioco.model.Explosion;
import gioco.model.Gioco;
import gioco.model.Player;
import gioco.net.Client;
import gioco.net.Protocol;
import gioco.utilities.Resources;

public class PlayerController extends KeyAdapter {
	private GameInterface panel;
	private Gioco gioco;
	private boolean multiplayer;
	private ArrayList<Integer> movements;
	private Client client;

	public PlayerController(GameInterface panel, boolean multi, String map) {
		super();
		Resources.loadResources();
		multiplayer = multi;
		gioco = new Gioco(multiplayer, map);
		if (multiplayer) {
			client = new Client();
			while (!client.isConnected())
				client.connect();
			while (client.readReady()) {
			}
		} else
			gioco.inizia();

		this.panel = panel;
		this.movements = new ArrayList<Integer>();
		movements.add(Player.IDLE_DOWN);

	}

	public void setClient(Client client) {
		this.client = client;
	}

	public GameInterface getPanel() {
		return panel;
	}

	public Gioco getGioco() {
		return gioco;
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
		// if (!game.isStarted())
		// return;
		Integer state;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
			if (!gioco.isGameOver()) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SHIFT:
				case KeyEvent.VK_SPACE:
					gioco.addBomb("player1");
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
				gioco.getPlayer1().setState(movements.get(movements.size() - 1));
				//if(multiplayer) {
				//	client.setAction(Protocol.state(gioco.getPlayer1().getState()));
					//System.out.println(Protocol.state(gioco.getPlayer1().getState()));
					//System.out.println("INVIATO LO STATO");
				//}
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gioco.isGameOver())
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
		gioco.getPlayer1().setState(state);
		//if(multiplayer)
			//client.setAction(Protocol.state(gioco.getPlayer1().getState()));
	}

	public boolean isMultiplayer() {
		return multiplayer;
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
		Vector<Explosion> explosionsUpdated = new Vector<Explosion>();
		String line = client.read();
		if(line == null) {
			System.out.println("ERROR DURING READING");
			return;
		}
		String content[] = line.split(" ");
		//if(content[0] == Protocol.CONNECTIONERROR)
			//show connection error dialog
				
		int i = 0;
		while (!content[i].equals(Protocol.ENDCOMUNICATION) || i<content.length-1) {
			if(content[i].equals(Protocol.PLAYER)) {
				int state = gioco.getPlayer1().getState();
				if(Integer.parseInt(content[i+4]) == Player.DYING_ENEMY || Integer.parseInt(content[i+4]) == Player.DYING_EXPLOSION || Integer.parseInt(content[i+4]) == Player.WINNING) {
					state = Integer.parseInt(content[i+4]);
					gioco.setGameOver(true);
				}
				if(Integer.parseInt(content[i+1]) == client.getOrderConnection())
					gioco.getPlayer1().update(Integer.parseInt(content[i+2]), Integer.parseInt(content[i+3]),
							state/*Integer.parseInt(content[i+4])*/);
				else 
					gioco.getPlayer2().update(Integer.parseInt(content[i+2]), Integer.parseInt(content[i+3]),
						Integer.parseInt(content[i+4]));
				i+=5;
			}
			else if (content[i].equals(Protocol.ENEMY)) {
				int id = Integer.parseInt(content[i+1]);
				for (Enemy e : gioco.getEnemies()) {
					if (e.getID() == id) {				
						if(e instanceof Enemy3) {
							((Enemy3) e).update(Integer.parseInt(content[i+2]), Integer.parseInt(content[i+3]),
									Integer.parseInt(content[i+4]) , Boolean.parseBoolean(content[i+5]), Integer.parseInt(content[i+6]),
									Integer.parseInt(content[i+7]) );
							i+=3;
						}
						else 
							e.update(Integer.parseInt(content[i+2]), Integer.parseInt(content[i+3]),
									Integer.parseInt(content[i+4]));
						i+=5;
						enemiesUpdated.add(e);
						break;
					}
				}
				
			}
			else if (content[i].equals(Protocol.BOMB)) {
				int x = Integer.parseInt(content[i+1]);
				int y= Integer.parseInt(content[i+2]);
				for(Bomb b : gioco.getBombs()) {
					if(b.getXCell() == x || b.getYCell() == y) {
						b.update(x, y, Integer.parseInt(content[i+3]));
						bombsUpdated.add(b);
						break;
					}
				}
				i+=4;
			}
			else if (content[i].equals(Protocol.EXPLOSION)) {
						explosionsUpdated.add(new Explosion(Integer.parseInt(content[i+1]), Integer.parseInt(content[i+2]),
								Integer.parseInt(content[i+3]) , Integer.parseInt(content[i+4]) , Integer.parseInt(content[i+5]) , gioco.getPlayer1()));
						i+=6;
			}		
		}
		gioco.setBombs(bombsUpdated);
		gioco.setEnemies(enemiesUpdated);
		gioco.setExplosions(explosionsUpdated);
		// leggo tempo
		// leggo blocchi distruttibili che devono essere rimossi
	}
	public void sendState() {
		client.sendMessage(Protocol.state(gioco.getPlayer1().getState()));
	}

}
