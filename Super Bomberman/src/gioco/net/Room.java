package gioco.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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

	private Vector<PrintWriter> out;
	private Vector<BufferedReader> in;
	private Gioco gioco;
	private Thread t;

	public Room(Vector<Socket> players, String map1) throws IOException {
		out = new Vector<PrintWriter>();
		in = new Vector<BufferedReader>();
		for (Socket player : players) {
			out.add(new PrintWriter(new BufferedOutputStream(player.getOutputStream()), true));
			in.add(new BufferedReader(new InputStreamReader(player.getInputStream())));
		}
		if(players.size()==2)
			gioco = new Gioco(true, false, map1);
		else
			gioco = new Gioco(true, true, map1);
		writeMessage(Protocol.READY);
		for (int i = 0; i < out.size(); ++i) {
			if (out.get(i) != null)
				out.get(i).println(Settings.PLAYER1 + i);
		}
		gioco.inizia();
		t = new Thread(this);
		t.start();

	}

	private void writeMessage(String message) {
		for (int i = 0; i < out.size(); ++i) {
			if (out != null)
				out.get(i).println(message);
		}
	}

	public Thread getT() {
		return t;
	}

	private void read() throws IOException {
		Vector<Boolean> read = new Vector<Boolean>();
		for(int i= 0 ; i<in.size();++i) {
			read.add(Boolean.FALSE);
		}
		
		long time = System.currentTimeMillis();
		int letti = 0;
		while(letti<in.size()) {
			for(int i = 0; i<in.size();++i) {
				if(read.get(i).equals(Boolean.FALSE) && in.get(i).ready()) {
					Player player = gioco.getPlayer(Settings.PLAYER1+i);
					readMessage(i , player);
					read.set(i, Boolean.TRUE); 
					letti+=1;
				}
				//il ready implica serve a indicare che non è pronto alcun messaggio -> il client si è disconnesso
				if(System.currentTimeMillis()-time >=5000  && !in.get(i).ready()) {
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

	private void readMessage(int index , Player player) throws IOException {
			if(player == null || player.isDead())
				return ;
			int state = player.getState();
			int i = 0;
			String line = in.get(index).readLine();
			String res[] = line.split(" ");
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
		for(int i = 0;i<in.size();++i) {
			Player player = gioco.getPlayer(Settings.PLAYER1+i);
			if(player == null)
				System.out.println("COS "+i);
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
		writeMessage(message.toString());
		
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				//if(!gioco.isGameOver()) {
					gioco.next();
					writeInformations();
					read();
				//}
				/*else for(int i = 0;i<80;++i) {
					gioco.next();
					writeInformations();
					t.interrupt();
				}*/

			} catch (Exception e) {

			}
		}
	}
}
