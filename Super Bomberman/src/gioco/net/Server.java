package gioco.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
/*
 * Il server gestisce un la connessione da parte dei client
 * si mette in attesa di un connessione , un volta ricevuta una richiesta , legge la modalità e pone il client nell'apposito vettore : multiplaye o battleroyale
 * per ogni vettore che ha raggiunto la capienza massima , viene inviato un segnale di conferma KEEPALIVE , se non si ottiene risposta , allora il client è eliminato e si attende una nuova connessione,
 * Se tutti i client hanno confermato , allora viene fatta partire un nuova room con le partite
 * Il numero massimo di room ospitabili è variabile (in questo caso 2)
 * Se non c'è spazio nelle room , allora si attende che una partita termini per far iniziare la prossima
 * */
public class Server {
	private ServerSocket server;
	private Vector<Room> rooms;

	private Vector<Socket> multiplayerLobby;
	private Vector<Socket> battleRoyaleLobby;
	
	private int n_players_br= 5;
	private int n_players_multi= 2;
	private int n_rooms= 2;
	
	public void startServer() throws IOException {
		server = new ServerSocket(8000);
		rooms = new Vector<Room>();
		multiplayerLobby = new Vector<Socket>();
		battleRoyaleLobby = new Vector<Socket>();
		while(!Thread.currentThread().isInterrupted()) {
			for(int i = 0; i< rooms.size();++i) {
				if(rooms.get(i).getT().isInterrupted())
					rooms.remove(i);
			}
			if(rooms.size()<n_rooms) {
				addConnections();				
			}
		}
				 
	}

	//controlla che la lobby sia tutta connessa con KEEPALIVE, altrimenti elimina il client
	private void checkLobby(Vector<Socket> toBeChecked) throws IOException {
		Vector<Socket> toBeRemoved = new Vector<Socket>(toBeChecked);
		for(Socket s : toBeChecked) {
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
			PrintWriter out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()), true);
			long time = System.currentTimeMillis();
			out.println(Protocol.KEEPALIVE);
			while(System.currentTimeMillis()-time<=1000) {
				if(in.ready()) {
					String str = in.readLine();
					if(str.equals(Protocol.KEEPALIVE)) {
						toBeRemoved.remove(s);
					}
				}
			}
		}
		toBeChecked.removeAll(toBeRemoved);
	}
	
	//aggiunge una nuova connessione , se raggiunta la dimensione scelta per la modalità considerata, allora viene chiamato checkLobby
	//se nessun client si è disconnesso allora viene create una nuova Room
	private void addConnections() throws IOException {	
		if(!server.isClosed()) {	
		Socket player;
		player = server.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(player.getInputStream())); 
		String gameMode = in.readLine();
		System.out.println(gameMode);
		if(gameMode == null) {
			in.close();
			player.close();
			return;
		}
		if(gameMode.equals(Protocol.BATTLEROYALE)) {
			battleRoyaleLobby.add(player);
			System.out.println("Connesso");
			checkLobby(battleRoyaleLobby);
			if(battleRoyaleLobby.size() == n_players_br) {
				Room room = new Room(battleRoyaleLobby);
				rooms.add(room);
				battleRoyaleLobby.clear();
			}
		} 
		else if(gameMode.equals(Protocol.MULTIPLAYER)) {
			multiplayerLobby.add(player);
			checkLobby(multiplayerLobby);
			if(multiplayerLobby.size() == n_players_multi) {
				Room room = new Room(multiplayerLobby);
				rooms.add(room);
				multiplayerLobby.clear();
			}
		}
		}
	}


	public static void main(String[] args) {
		Server s = new Server();
		try {
			System.out.println("Starting server...");
			s.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
