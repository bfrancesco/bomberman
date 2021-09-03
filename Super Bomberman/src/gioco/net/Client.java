package gioco.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import gioco.WindowsHandler;
import gioco.model.Player;
import gioco.utilities.Settings;


public class Client implements Runnable{
	private Socket socket ;
	private PrintWriter out;
	private BufferedReader in;
	private int orderConnection;
	private boolean connected;
	private boolean exited;
	private boolean bombAdded;
	private boolean battle;
	private static Client client;
	private int map;
	private long startingTime;
	
	private Client() {
		bombAdded = false;
		this.battle = false;
		orderConnection = -1;
		exited = false;
	}
	
	public static Client getClient() {
		if(client == null)
			client = new Client();
		return client;
	}
	
	
	public int getMap() {
		return map;
	}

	public static void reset() {
		if(client != null)
			client.disconnect();
		client = null;
	}
	
	public int getOrderConnection() {
		return orderConnection;
	}

	public void  setOrderConnection( int i ) {
		 orderConnection = i;
	}

	//keep alive / heartbeat
	public boolean readReady() {
		if(!connected)
			return false;
		try {
			if(in == null)
				return false;
			String line = in.readLine();
			if(line == null)
				return false;
			if(line.equals(Protocol.READY))
			{
				if(in==null)
					return false;
				line= in.readLine();
				String info[] = line.split(" "); 
				orderConnection = Integer.parseInt(info[0]);
				map = Integer.parseInt(info[1]);
				startingTime = Long.parseLong(info[2]);
				if(battle)
					Settings.selectedbomberman = Settings.WHITE+(orderConnection-Settings.PLAYER1);
				return true;
				
			}
			if(line.equals(Protocol.KEEPALIVE)) {
				sendMessage(Protocol.KEEPALIVE);
			}
		} catch (IOException e) {
		}
		return false;
		
	}
	
	public void setBattleRoyale(boolean battleRoyale) {
		this.battle = battleRoyale;
	}


	
	
	public synchronized long getStartingTime() {
		return startingTime;
	}


	public synchronized boolean isConnected() {
		return connected;
	}



	public synchronized void  setConnected(boolean connected) {
		this.connected = connected;
	}



	public boolean connect () {
		try {
			socket = new Socket("localhost" , 8000);
			out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
			in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			setConnected(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println("Impossibile contattare il server");
			socket = null;
			out = null;
			in = null;
			setConnected(false);
		} 
		return connected;
	}
	
	
	public boolean ready() {
		try {
			return in.ready();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public void disconnect() {
		if(connected) {
			this.sendMessage(Protocol.DISCONNECTION);
		}
		socket = null;
		out = null;
		in = null;
		setConnected(false);
	}
	
	
	public void sendMessage(String message) {
		if(out != null) {
			out.println(message);
		}		
	}
		
	public String read() {
		try {
			if(in != null && in.ready()) {
				String line = in.readLine();
				//System.out.println(line);
				return line;
			}
		} catch (IOException e) {
			System.out.println("Errore in lettura dati dal server");
			e.printStackTrace();
			disconnect();
		}
		return null;
	}
	
	
 
	public synchronized boolean isBombAdded() {
		return bombAdded;
	}


	public synchronized void setBombAdded(boolean bombAdded) {
		this.bombAdded = bombAdded;
	}
	
	

public synchronized boolean isExited() {
		return exited;
	}

	public  synchronized void setExited(boolean exited) {
		this.exited = exited;
	}

@Override
public void run() {
		connect();
		if(exited)
			return;
		if(!connected) {
			WindowsHandler.getWindowsHandler().setMenu();	
			//attenzione 
			return;
		}
		if(battle)
			sendMessage(Protocol.BATTLEROYALE);
		else sendMessage(Protocol.MULTIPLAYER);
		while(!readReady());
		WindowsHandler.getWindowsHandler().setGamePanel(true, battle, this);
}

	
}
