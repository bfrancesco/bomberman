package gioco.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

import gioco.model.Bomb;
import gioco.model.Enemy;
import gioco.model.Enemy1;
import gioco.model.Enemy2;
import gioco.model.Enemy3;
import gioco.model.Entity;
import gioco.model.Explosion;
import gioco.model.Gioco;
import gioco.model.Player;
import gioco.utilities.Settings;

public class Room implements Runnable {

	//private Vector<Sender> out;
	 private Vector<PrintWriter> out;
	private Vector<BufferedReader> in;
	private Gioco gioco;
	private Thread t;

	public Room(Vector<Socket> players) throws IOException {
		//out = new Vector<Sender>();
		out = new Vector<PrintWriter>();
		in = new Vector<BufferedReader>();
		for (Socket player : players) {
			//out.add(new Sender(new PrintWriter(new BufferedOutputStream(player.getOutputStream()), true)));
			in.add(new BufferedReader(new InputStreamReader(player.getInputStream())));
			out.add(new PrintWriter(new BufferedOutputStream(player.getOutputStream()), true));
		}
		
		String map = "Map"+(new Random().nextInt(3)+1);
		
		if (players.size() == 2)
			gioco = new Gioco(true, false, map);
		else
			gioco = new Gioco(true, true, map);
		writeMessage(Protocol.READY);
		for (int i = 0; i < out.size(); ++i) {
			if (out.get(i) != null)
				out.get(i).println(Protocol.startingInfo(Settings.PLAYER1 + i , map));
		}
		gioco.inizia();
		t = new Thread(this);
		t.start();

	}

	private void writeMessage(String message) {
		for (int i = 0; i < out.size(); ++i) {
			if (out != null) {
				out.get(i).println(message);
			}
		}
	}
/*
	private void writeMessageConcurrently(String message) {

		for (int i = 0; i < out.size(); ++i) {
			if (out != null) {
				out.get(i).setMessage(message);
				new Thread(out.get(i)).start();

			} else
				System.out.println("SONO NULLO");
		}
	}*/

	public Thread getT() {
		return t;
	}

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
					if (in.get(i).ready()) {
						Player player = gioco.getPlayer(Settings.PLAYER1 + i);
						readMessage(i, player);
						read.set(i, Boolean.TRUE);
						letti += 1;
					}
				}
				// il ready serve a indicare che non è pronto alcun messaggio -> se passa troppo
				// tempo , la comunicazione deve essere interrotta (fine del gioco) oppure è
				// stata interrotta
				if (System.currentTimeMillis() - time >= 5000 && !in.get(i).ready()) {
					System.out.println(" CHIUSURA ");
					closeAll();
					Thread.currentThread().interrupt();
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

	private void readMessage(int index, Player player) throws IOException {
		if (player == null || player.isDead())
			return;
		int state = player.getState();
		int i = 0;
		String line = in.get(index).readLine();
		System.out.println(line + " " + i);
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

	public synchronized void writeInformations() {
		StringBuffer message = new StringBuffer();
		for (int i = 0; i < in.size(); ++i) {
			Player player = gioco.getPlayer(Settings.PLAYER1 + i);
			if (player == null)
				System.out.println("COS " + i);
			else
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
		//writeMessageConcurrently(message.toString());
		writeMessage(message.toString());
//		boolean alive = true;
//		while (alive) {
//			alive = false;
//			for (Sender s : out) {
//				if (s.isAlive()) {
//					alive = true;
//					break;
//				}
//			}
//		}
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				gioco.next();
				writeInformations();
				read();
			} catch (Exception e) {

			}
		}
	}
}
