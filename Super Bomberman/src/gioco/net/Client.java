package gioco.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import gioco.model.Player;
import gioco.utilities.Settings;


public class Client {
	private Socket socket ;
	private PrintWriter out;
	private BufferedReader in;
	private int orderConnection;
	private boolean connected;
	private boolean bombAdded;
	
	public Client() {
		bombAdded = false;
		connect();
		orderConnection = -1;
	}
	
	
	
	public int getOrderConnection() {
		return orderConnection;
	}

	public void  settOrderConnection( int i ) {
		 orderConnection = i;
	}

	public boolean readReady() {
		if(!connected)
			return false;
		try {
			String line = in.readLine();
			if(line.equals(Protocol.READY))
			{
				line= in.readLine();
				orderConnection = Integer.parseInt(line);
				return true;
			}
		} catch (IOException e) {
		}
		return false;
		
	}
	
	


	public boolean isConnected() {
		return connected;
	}



	public void setConnected(boolean connected) {
		this.connected = connected;
	}



	public boolean connect () {
		try {
			socket = new Socket("localhost" , 8000);
			out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
			in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			connected = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println("Impossibile contattare il server");
			socket = null;
			out = null;
			in = null;
			connected = false;
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
		socket = null;
		out = null;
		in = null;
		connected = false;
	}
	
	
	public void sendMessage(String message) {
		if(out != null) {
			out.println(message);
		}
		
		else
			System.out.println("OUT IS NULL");
	}
		
	public String read() {
		try {
			if(in != null && in.ready()) {
				String line = in.readLine();
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



	
}
