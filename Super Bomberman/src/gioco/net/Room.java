package gioco.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

import gioco.utilities.*;
import gioco.model.Bomb;
import gioco.model.Enemy;
import gioco.model.Gioco;
import gioco.model.Player;
import gioco.model.PowerUp;

/*
 * Gestisce lo svolgimento delle partite : 
 * Viene inviiato ai client il segnale di READY con le informazioni necessarie cui sono la mappa il tempo di inizio e l'ordine di connessione , ossia l'identificativo del player che si controlla
 * Un client perde la connessione se non invia segnali per più di CLOSINGTIME e il corrispettivo player è vivo
 * Una volta rimosso la room continua la sua esecuzione
 */
public class Room implements Runnable {

	private Vector<PrintWriter> out;
	private Vector<BufferedReader> in;
	private Gioco gioco;
	private Thread t;
	private long startTime;
	private static int  CLOSINGTIME = 1500;
	public Room(Vector<Socket> players) throws IOException {
		out = new Vector<PrintWriter>();
		in = new Vector<BufferedReader>();
		for (Socket player : players) {
			in.add(new BufferedReader(new InputStreamReader(player.getInputStream())));
			out.add(new PrintWriter(new BufferedOutputStream(player.getOutputStream()), true));
		}
		int map = new Random().nextInt(Settings.MAPSNUMBER) + 1;
		//map = 1;
		if (players.size() == 2)
			gioco = new Gioco(true, false, map);
		else if (players.size() == 5)
			gioco = new Gioco(true, true, map);
		else {
			return;
		}
		writeMessage(Protocol.READY);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < out.size(); ++i) {
			if (out != null) {
				out.get(i).println(Protocol.startingInfo(Settings.PLAYER1 + i, map, startTime));
			}
		}
		gioco.inizia();
		t = new Thread(this);
		t.start();

	}
//scrive a tutti i client
	private void writeMessage(String message) {
		for (int i = 0; i < out.size(); ++i) {
			if (out != null) {
				out.get(i).println(message);
			}
		}
	}

	public Thread getT() {
		return t;
	}

	//legge i messaggi dei client che valgono come HEARTBEAT, indicando che la connessione persistente,
	//qualora un client non dovesse essere pronto ,allora vengono letti quelli disponibili , se si disconnette o non invia messaggi ed è ancora vivo , allora viene disconnesso
	private void read() throws IOException {
		Vector<Boolean> read = new Vector<Boolean>();
		for (int i = 0; i < in.size(); ++i) {
			read.add(Boolean.FALSE);
		}

		long time = System.currentTimeMillis();
		int letti = 0;
		while (letti < in.size()) {
			for (int i = 0; i < in.size(); ++i) {
				if (read.get(i).equals(Boolean.FALSE)) {
					Player player = gioco.getPlayer(Settings.PLAYER1 + i);
					if(player == null) {
						System.out.println(i);
						t.interrupt();
					}
					if (player.isDead()) {
						letti += 1;
						read.set(i, Boolean.TRUE);
					} else if (in.get(i).ready()) {
						readMessage(i, player);
						read.set(i, Boolean.TRUE);
						letti += 1;
					}
					// il ready serve a indicare che non è pronto alcun messaggio -> se passa troppo
					// tempo , la comunicazione deve essere interrotta (fine del gioco) oppure viene rimosso il player
					if (System.currentTimeMillis() - time >= CLOSINGTIME  && !in.get(i).ready()) {
						if (gioco.isGameOver() && System.currentTimeMillis() - time >= CLOSINGTIME ) {
							//System.out.println(" CHIUSURA ");
							closeAll();
							Thread.currentThread().interrupt();
						} else if (!in.get(i).ready()) {
							player.setState(Player.DYING_ENEMY);
							letti += 1;
							read.set(i, Boolean.TRUE);
						}
					}
				}
			}
		}
	}

	private void closeAll() throws IOException {
		for (int i = 0; i < in.size(); ++i) {
			if (out.get(i) != null)
				out.get(i).close();
			if (in.get(i) != null)
				in.get(i).close();
		}

	}
	/*
	 * legge secondo il protocollo le informazioni dai client
	 * */
	private void readMessage(int index, Player player) throws IOException {
		if (player == null)
			return;
		int state = player.getState();
		int i = 0;
		String line = in.get(index).readLine();
		String res[] = line.split(" ");
		if (res[i] == Protocol.DISCONNECTION) {
			player.setState(Player.DYING_ENEMY);
			i += 1;
		}
		if (res[i].equals(Protocol.BOMBADDED)) {
			gioco.addBomb(player);
			i += 1;
		}

		if (res[i].equals(Protocol.STATE)) {
			state = Integer.parseInt(res[i + 1]);
			player.setState(state);

		}

	}
	/*
	 * Scrive le informazioni ai client secondo il protocollo 
	 * */
	public synchronized void writeInformations() {
		StringBuffer message = new StringBuffer();
		for (int i = 0; i < in.size(); ++i) {
			Player player = gioco.getPlayer(Settings.PLAYER1 + i);
			//errore nel gioco, l'evento non dovrebbe mai accadere
			if(player == null) {
				t.interrupt();
			}
			if (player != null)
				message.append(Protocol.player(player.toString()) + " ");
		}
		for (Enemy e : gioco.getEnemies()) {
			if (e != null)
				message.append(Protocol.enemy(e.toString()) + " ");
		}
		for (Bomb b : gioco.getBombs()) {
			message.append(Protocol.bomb(b.toString()) + " ");
		}
		message.append(Protocol.ENDCOMUNICATION);

		writeMessage(message.toString());
	}

	/*
	 * Il run regola il thread che gestisce la room : 
	 * esso invia all'inizio i powerup affinchè tutti i client siano uniformati , in seguito ciclicamente calcola il nuovo passo , scrive le informazioni nuove e legge dai client 
	 * */
	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();
		for (PowerUp p : gioco.getPowerups()) {
			sb.append(Protocol.powerup(p.toString()) + " ");
		}
		writeMessage(sb.toString());
		int remainingTime = gioco.getTime();
		while (!Thread.currentThread().isInterrupted()) {
			try {
				gioco.next();
				int walltime = ((Long) (System.currentTimeMillis() - startTime)).intValue() / 1000;
				remainingTime = gioco.getTime() - walltime;
				if (remainingTime <= 0 && gioco.isMultiplayer()) {
					gioco.timeOut();
				}
				writeInformations();
				read();
			} catch (Exception e) {
				break;
			}
		}
	}
}
