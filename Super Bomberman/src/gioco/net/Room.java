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

	private PrintWriter out1;
	private PrintWriter out2;
	private BufferedReader in1;
	private BufferedReader in2;
	private Gioco gioco;
	private Thread t;
	private boolean full;
	public Room(Vector<Socket> players , String map1) throws IOException {
		full = false;
		out1 = new PrintWriter(new BufferedOutputStream(players.get(0).getOutputStream()), true);
		in1 = new BufferedReader(new InputStreamReader(players.get(0).getInputStream()));
		out2 = new PrintWriter(new BufferedOutputStream(players.get(1).getOutputStream()), true);
		in2 = new BufferedReader(new InputStreamReader(players.get(1).getInputStream()));
		gioco = new Gioco(true , false ,  map1);
		writeMessage(Protocol.READY);
		out1.println(Settings.PLAYER1);
		out2.println(Settings.PLAYER2);
		gioco.inizia();	
		t = new Thread(this);
		t.start();

	}
	
	 public synchronized boolean isFull() {
		 return full;
	 }

	private void writeMessage(String message) {
		if (out1 != null)
			out1.println(message);
		if (out2 != null)
			out2.println(message);
	}

	
	
	public Thread getT() {
		return t;
	}

	private void read() throws IOException {
		boolean read2 = false;
		boolean read1 = false;
		long time = System.currentTimeMillis();
		while(!read1 || !read2) {
			if(!read1 && in1.ready()) {
				readMessage(in1);
				read1 = true;
			}
			if(!read2 && in2.ready()) {
				readMessage(in2);
				read2 = true;
			}
			//il ready implica serve a indicare che non è pronto alcun messaggio -> il client si è disconnesso
			if(System.currentTimeMillis()-time >=5000  && (!in1.ready() || !in2.ready())) {
				in1.close();
				in2.close();
				out1.close();
				out2.close();
				Thread.currentThread().interrupt();
			}
		}
	}
	

	private void readMessage(BufferedReader in) throws IOException {
		
		if (in != null ) {
			Player player;
			if(in==in1) 
				player = gioco.getPlayer(Settings.PLAYER1);
			else 
				player = gioco.getPlayer(Settings.PLAYER2);
			int state = player.getState();
			int i = 0;
			String line = in.readLine();
			String res  [] = line.split(" ");
			if(res[i].equals(Protocol.BOMBADDED) ) {
				gioco.addBomb(player);
				i+=1;
			}
				
			if(res[i].equals(Protocol.STATE)) {
				state = Integer.parseInt(res[i+1]);
				 player.setState(state);

			}
			
		}
	}
	
	
	public synchronized void writeInformations() {
		StringBuffer message = new StringBuffer();
		message.append(Protocol.player(gioco.getPlayer(Settings.PLAYER1).toString())+" ");
		message.append(Protocol.player(gioco.getPlayer(Settings.PLAYER2).toString())+" ");
		for(Enemy e : gioco.getEnemies()) {
			if(e!=null)
				message.append(Protocol.enemy(e.toString())+ " ");
		}
		for(Bomb b : gioco.getBombs()) {
			message.append(Protocol.bomb(b.toString())+" ");
		}
		for(Explosion e : gioco.getExplosions())
			message.append(Protocol.explosion(e.toString())+" ");
		
		message.append(Protocol.ENDCOMUNICATION);
		writeMessage(message.toString());
	}
	
	

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try{	
					gioco.next();
					writeInformations();									
					read();
					
			}catch (Exception e) {
				
			}		
		}
	}
}
