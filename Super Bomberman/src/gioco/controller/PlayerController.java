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
import gioco.gui.ResultsPanel;
import gioco.model.Bomb;
import gioco.model.Enemy;
import gioco.model.Enemy3;
import gioco.model.Entity;
import gioco.model.Explosion;
import gioco.model.Gioco;
import gioco.model.Player;
import gioco.model.PowerUp;
import gioco.net.Client;
import gioco.net.Protocol;
import gioco.sound.SoundsHandler;
import gioco.utilities.Settings;

//controller che gestisce il giocatore e il gioco 
//Legge i dati dal gioco, per disegnarli sul GamePanel
//Premendo ESC , si seleziona il menu di pausa
// Con le frecce direzionali o WASD è possibile modificare lo stato del giocatore, gestito da un Vector di interi che registrano i movimenti
//quando il tasto viene rilasciato, viene impostato uno stato di IDLE precedentemente modificato, permettendo al giocatore di fermarsi nella posizione corretta
//L'elemento 0 deve sempre contenere uno stato IDLE
//Premendo SPACE o SHIFT viene aggiunta una bomba
//Nel caso del multiplayer gli appositi metodi permettono di ricevere ed inviare i dati al server    
public class PlayerController extends KeyAdapter {
	private GamePanel panel;
	private Gioco gioco;
	private boolean multiplayer;
	private boolean paused;
	private boolean battleRoyale;
	private Vector<Integer> movements;
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
		this.movements = new Vector<Integer>();
		movements.add(Player.IDLE_DOWN);	
		if(multiplayer)
			Settings.selectedbomberman = Settings.WHITE+(client.getOrderConnection()-Settings.PLAYER1);
		// permette di passare al menu di pausa
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
	//stabilisce la dimensione di ogni cella 
	public void setBlockSizes(int width, int height) {
		Settings.BLOCKSIZEX = width / gioco.getWidth();
		Settings.BLOCKSIZEY = height / gioco.getHeight();
		Settings.factor3d = Settings.BLOCKSIZEY / 5;
	}


	//escape per settare la pausa
	//se un pulsante che controlla  il movimento è stato rilasciato allora viene eliminato lo stato corrispondente dai movimenti e viene settato lo stato precedente
	//l'azione può scatenare il relativo suono . ES : rilasciare ESCAPE fa partire il suono del menu di pausa
	//viene aggiunta una bomba se possibile con SPACE e mutato il gioco con M
	public void keyReleased(KeyEvent e) {
		if (!gioco.isStarted())
			return;
		Integer state;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (paused) {
				setPaused(false);
				SoundsHandler.getSoundsHandler().resumeGameSoundtrack();
				WindowsHandler.getWindowsHandler().showGlassPane(false);
			} else {
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
				SoundsHandler.getSoundsHandler().stopSteps();
				SoundsHandler.getSoundsHandler().noSoundtrack();
				SoundsHandler.getSoundsHandler().pauseMenu();
				if(!gioco.isGameOver())
					setPaused(true);
				WindowsHandler.getWindowsHandler().getFrame().getGlassPane().setVisible(true);

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
			case KeyEvent.VK_M:
				SoundsHandler.getSoundsHandler().changeMusicMute();
				SoundsHandler.getSoundsHandler().changeEffectMute();
				SoundsHandler.getSoundsHandler().stopSteps();
				break;
			}
			if (multiplayer)
				gioco.getPlayer(client.getOrderConnection()).setState(movements.get(movements.size() - 1));
			else
				gioco.getPlayer(Settings.PLAYER1).setState(movements.get(movements.size() - 1));
		}
	}

	//se il gioco non è in pausa, allora è possibile muovere il giocatore
	//Lo stato viene aggiornato al movimento corrispondente al tasto premuto 
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

	public synchronized boolean isPaused() {
		return paused;
	}

	public synchronized void setPaused(boolean paused) {
		this.paused = paused;
		if (!paused)
			panel.requestFocus();
	}

	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}

	public Client getClient() {
		// TODO Auto-generated method stub
		return client;
	}

	//legge il segnale e aggiorna dal server
	//viene ricevuta la stringa , finchè non si arriva alla fine del messaggio oppure si legge END_COMUNICATION avviene la lettura
	//La lettura segue il protocollo , perciò una parola chiave identifica la tipologia di messaggio e a seguire ci sono i corrispettivi dati
	// i dati letti letti comportano un aggiornamento dei dati di gioco già esistenti
	//ES : Si legge BOMB con "a" "b" "c" "d" "e" interi 
	// BOMB indicherà una bomba con i seguenti dati abcd...
	//verrà creata una nuova bomba con i dati letti e posta in un vettore
	//in fine il vettore rimpiazzerà quello già esistente nel gioco, ottenendo le bombe aggiornate
	public void readAndUpdate() {
		if (!client.isConnected() || !client.ready()) {
			return;
		}
		Vector<Enemy> enemiesUpdated = new Vector<Enemy>();
		Vector<Bomb> bombsUpdated = new Vector<Bomb>();
		gioco.setCollisionExplosionEnemy(false);
		gioco.setCollisionEnemyPlayer(false);
		gioco.setCollisionExplosionPlayer(false);
		String line = client.read();
		if (line == null) {
			return;
		}
		String content[] = line.split(" ");
		int i = 0;
		while (!content[i].equals(Protocol.ENDCOMUNICATION) || i < content.length - 1) {
			if (content[i].equals(Protocol.PLAYER)) {
				//lo stato del giocatore controllato , se vivo , e quindi modificabile , non deve essere sovrascritto poichè lo stato è controllato dal PlayerController e non dal server
				Player p = gioco.getPlayer(Integer.parseInt(content[i + 1]));
				int state = gioco.getPlayer(client.getOrderConnection()).getState();
				int stateFromServer = Integer.parseInt(content[i + 4]);
				if (!p.isDead()) {
					if (stateFromServer == Player.DYING_ENEMY)
						gioco.setCollisionEnemyPlayer(true);
					else if (stateFromServer == Player.DYING_EXPLOSION)
						gioco.setCollisionExplosionPlayer(true);
				}
				if (gioco.isGameOver() || stateFromServer == Player.DYING_ENEMY
						|| stateFromServer == Player.DYING_EXPLOSION || stateFromServer == Player.WINNING) {
					state = stateFromServer;
					if (Integer.parseInt(content[i + 1]) == client.getOrderConnection()) {
						gioco.setGameOver(true);
					}

				}
				if (Integer.parseInt(content[i + 1]) == client.getOrderConnection())
					p.update(Integer.parseInt(content[i + 2]), Integer.parseInt(content[i + 3]), state,
							Integer.parseInt(content[i + 5]));
				else {
					p.update(Integer.parseInt(content[i + 2]), Integer.parseInt(content[i + 3]), stateFromServer,
							Integer.parseInt(content[i + 5]));
				}
				i += 6;
			} else if (content[i].equals(Protocol.ENEMY)) {
				//vengono letti i nemici (quelli di tipo 3 , portano più informazioni )
				int id = Integer.parseInt(content[i + 1]);
				for (Enemy e : gioco.getEnemies()) {
					if (e.getID() == id) {
						if (!e.isDead() && Integer.parseInt(content[i + 4]) == Entity.DYING_EXPLOSION) {
							gioco.setCollisionExplosionEnemy(true);
						}
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
						Integer.parseInt(content[i + 3]), Integer.parseInt(content[i + 4]),
						gioco.getPlayer(Integer.parseInt(content[i + 5]))));
				i += 6;
			}
		}
		gioco.setBombs(bombsUpdated);
		gioco.setEnemies(enemiesUpdated);
	}
	
	//viene notificato al pannello delle entità di aggiornare la grafica e disegnare i nuovi dati
	//vengono letti i cambiamenti del gioco e riprodotti i corrispettivi suoni
	public void render() {
		panel.getGiocoView().getEntitiesPanel().update();
		playAudio();
	}

	//si leggono i cambiamenti avvenuti rispetto allo stato precedente e vengono riprodotte i rispettivi suoni
	public void  playAudio() {
		if ((!multiplayer && gioco.getPlayers().get(0).isMoving())
				|| (multiplayer && gioco.getPlayer(client.getOrderConnection()).isMoving())) {
			SoundsHandler.getSoundsHandler().startSteps();
		} else
			SoundsHandler.getSoundsHandler().stopSteps();

		if (gioco.isCollisionPowerUp()) {
			SoundsHandler.getSoundsHandler().powerUp();
		}
		if (gioco.isBombExploded())
			SoundsHandler.getSoundsHandler().explosion();
		if (gioco.isBombIncrease()) {
			SoundsHandler.getSoundsHandler().placeBomb();
		}
		if (gioco.isCollisionExplosionEnemy())
			SoundsHandler.getSoundsHandler().dyingEnemy();
		if (gioco.isCollisionEnemyPlayer() || gioco.isCollisionExplosionPlayer())
			SoundsHandler.getSoundsHandler().bombermanDeath();
	}
	
	//metodo che permette di inviare le informazioni al server 
	//è necessario inviare lo stato del giocatore e  l'eventuale segnale di richiesta di una nuova bomba aggiunta
	//lo stato serve come "battito", ovvero segnala al server che il client è ancora connesso
	public void sendInfo() {
		if (client.isBombAdded()) {
			client.sendMessage(
					Protocol.BOMBADDED + " " + Protocol.state(gioco.getPlayer(client.getOrderConnection()).getState()));
			client.setBombAdded(false);
		} else
			client.sendMessage(Protocol.state(gioco.getPlayer(client.getOrderConnection()).getState()));
	}
	
	//metodo che permette di ricevere i powerUp prima di iniziare la partita, in maniera tale che siano uguali fra i vari client
	public void receivePowerUp() {
		if (!client.isConnected() || !client.ready()) {
			return;
		}
		Vector<PowerUp> powerups = new Vector<PowerUp>();
		String line = client.read();
		if (line == null) {
			System.out.println("ERROR DURING READING");
			return;
		}
		String content[] = line.split(" ");
		int i = 0;
		while (i < content.length) {
			if (content[i].equals(Protocol.POWERUP)) {
				powerups.add(new PowerUp(Integer.parseInt(content[i + 1]), Integer.parseInt(content[i + 2]),
						Integer.parseInt(content[i + 3])));
				i += 4;
			} else
				i++;
		}
		gioco.setPowerups(powerups);
	}
	
	//Imposta il resultPane come glassPane e ne fa visualizzare il risultato.
	//viene ricavato il risultato per il giocatore controllato tramite l'apposito metodo setResult(tipo player) e si legge il risultato tramite getResult
	//se il menu di pausa è stato visualizzato , allora viene rimosso per visualizzare il ResultPane
	public void showResults() {
		if(paused) {
			setPaused(false);
    		SoundsHandler.getSoundsHandler().resumeGameSoundtrack();
    		WindowsHandler.getWindowsHandler().showGlassPane(false);
		}
		if (!multiplayer)
			gioco.setResult(Settings.PLAYER1);
		else
			gioco.setResult(client.getOrderConnection());
		if(gioco.getResult()!=Gioco.VICTORY)
			SoundsHandler.getSoundsHandler().noSoundtrack();
		ResultsPanel panel = new ResultsPanel();
		WindowsHandler.getWindowsHandler().getFrame().setGlassPane(panel);
		ResultController rc = new ResultController(gioco, panel);
		panel.setController(rc);
		WindowsHandler.getWindowsHandler().showGlassPane(true);
		panel.requestFocus();
	}

}
