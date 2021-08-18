package gioco.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import gioco.model.Bomb;
import gioco.model.Enemy;
import gioco.model.Entity;
import gioco.model.Explosion;
import gioco.model.Gioco;
import gioco.model.Player;
import gioco.utilities.Settings;



public class Server {
	private ServerSocket server;
	private Vector<Room> rooms;

	
	public void startServer(String map) throws IOException {
		server = new ServerSocket(8000);
		rooms = new Vector<Room>();
		while(!Thread.currentThread().isInterrupted()) {
			for(int i = 0; i< rooms.size();++i) {
				if(rooms.get(i).getT().isInterrupted())
					rooms.remove(i);
			}
			if(rooms.size()<2)
				addConnections(); 
		}
		

		
	}
	
	private void addConnections() throws IOException {	
		if(server.isClosed()) {
			System.out.println("CHIUSO");
		}
		
		Socket player1;
		Socket player2;
		System.out.println("Waiting for player 1...");
		player1 = server.accept();
		System.out.println("Waiting for player 2...");
		player2 = server.accept();
		
		System.out.println("READY TO START!");
		Room room = new Room(player1, player2, "MAP1");
		rooms.add(room);
		
		//se ne volessi avere di più , allora faccio partire con almeno 2 e poi aggiungo una lista di altri giocatori connessi
		/*out1 = new PrintWriter(new BufferedOutputStream(player1.getOutputStream()), true);
		in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
		out2 = new PrintWriter(new BufferedOutputStream(player2.getOutputStream()), true);
		in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
		gioco = null;
		gioco = new Gioco(true , "Map1");
		writeMessage(Protocol.READY);
		out1.println(Settings.PLAYER1);
		out2.println(Settings.PLAYER2);
		gioco.inizia();*/
		
	}


/*	private void writeMessage(String message) {
		if (out1 != null)
			out1.println(message);
		if (out2 != null)
			out2.println(message);
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
			if(System.currentTimeMillis()-time >=5000 ) {
				resetServer();
			}
		}
	}
	

	private void readMessage(BufferedReader in) throws IOException {
		
		if (in != null ) {
			Player player;
			if(in==in1) 
				player = gioco.getPlayer1();
			else 
				player = gioco.getPlayer2();
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
		message.append(Protocol.player(gioco.getPlayer1().toString())+" ");
		message.append(Protocol.player(gioco.getPlayer2().toString())+" ");
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
	
	

	/*@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try{	
					gioco.next();
					writeInformations();									
					read();
					
			}catch (Exception e) {
				
			}
			
		}

	}*/
	
	public static void main(String[] args) {
		Server s = new Server();
		try {
			System.out.println("Starting server...");
			s.startServer("Map1");
			//Thread t = new Thread(s);
			//t.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
