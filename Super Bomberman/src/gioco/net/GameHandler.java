package gioco.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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


public class GameHandler implements Runnable {

	private PrintWriter out1;
	private PrintWriter out2;
	private BufferedReader in1;
	private BufferedReader in2;
	private Gioco gioco;

	public GameHandler(Socket player1, Socket player2 , String map1) throws IOException {
		// RICORDA IL TRUE PER FARE L'AUTOFLUSH PER NON FARLO NOI OGNI VOLTA!
		out1 = new PrintWriter(new BufferedOutputStream(player1.getOutputStream()), true);
		in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
		out2 = new PrintWriter(new BufferedOutputStream(player2.getOutputStream()), true);
		in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
		gioco = new Gioco(true , map1);
		gioco.inizia();
		writeMessage(Protocol.READY);
		out1.println(1);
		out2.println(2);
		/*writeMessage(
				Protocol.player(gioco.getPlayer1().toString()));
		writeMessage(
				Protocol.player(gioco.getPlayer2().toString()));*/
		Thread t = new Thread(this);
		t.run();
	}

	private void writeMessage(String message) {
		if (out1 != null)
			out1.println(message);
		if (out2 != null)
			out2.println(message);
	}

	private void read() throws IOException {
		readMessage(in1);
		readMessage(in2);
	}
	
	/*private int parseLine(String line) {
		String res  [] = line.split(" ");
		if(res[0].equals(Protocol.STATE))
			return Integer.getInteger(res[1]);
		return -1;
	}*/

	private void readMessage(BufferedReader in) throws IOException {
		if (in != null && in.ready()) {
			Player player;
			if(in==in1) 
				player = gioco.getPlayer1();
			else 
				player = gioco.getPlayer2();
			int state = player.getState();
			String line = in.readLine();
			String res  [] = line.split(" ");
			if(res[0].equals(Protocol.STATE))
				 player.setState(Integer.getInteger(res[1]));
			
			int direction = Settings.NONE;
			switch(state) {
			case Entity.WALKING_DOWN : 
				direction = Settings.DOWN;
				break;
			case Entity.WALKING_UP : 
				direction = Settings.UP;
				break;
			case Entity.WALKING_RIGHT : 
				direction = Settings.RIGHT;
				break;
			case Entity.WALKING_LEFT : 
				direction = Settings.LEFT;
				break;
			}
			System.out.println("Ricevuto ");
			//gioco.movePlayer(player , direction);
		}
		else {
			System.out.println("WHAT");
		}
	}
	
	
	public synchronized void writeInformations() {
		StringBuffer message = new StringBuffer();
		message.append(Protocol.player(1  , gioco.getPlayer1().toString())+" ");
		message.append(Protocol.player(2  , gioco.getPlayer2().toString())+" ");
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
		System.out.println(message.toString());
		writeMessage(message.toString());
	}
	
	

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try{		
				//read();
				writeInformations();
				gioco.next();		
			}catch (Exception e) {
				out1 = null;
				out2 = null;
				return ;
			}
			
		}

	}
}
