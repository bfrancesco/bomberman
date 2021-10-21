package gioco.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import gioco.WindowsHandler;

/*
 * Si può avere un solo client 
 * permette di connettersi al server e di preparare la partita multiplayer una volta connesso
 * permette di comunicare con il server
 * */
public class Client implements Runnable{
	public static final int CONNECTING = 0;
	public static final int FAILED = 0;
	public static final int CONNECTED = 0;
	
	private Socket socket ;
	private PrintWriter out;
	private BufferedReader in;
	//l'ordine della connessione è usata per capire quale player è controllato dal corrente client e quindi i movimenti inviati sono validi solo per il player considerato
	private int orderConnection;
	private boolean connected;
	private boolean bombAdded;
	private boolean battle;
	private static Client client;
	private int map;
	private Thread t ;
	private long startingTime;
	
	private Client() {
		bombAdded = false;
		this.battle = false;
		orderConnection = -1;
	}
	
	public  static Client getClient() {
		if(client == null)
			client = new Client();
		return client;
	}
	
	
	public synchronized int getMap() {
		return map;
	}
	
	//inizializza il Thread e parte l'esecuzione
	public synchronized void start() {
		interrupt();
		t = new Thread(this);
		t.start();
		
	}
	
	
	//interrompe il client
	public void interrupt() {
		if(t!=null && t.isAlive())
			t.interrupt();
	}

	public static void reset() {
		if(client != null)
			client.disconnect();
		client = null;
	}
	//ottenere l'ordine della connessione
	public  int getOrderConnection() {
		return orderConnection;
	}

	public  void  setOrderConnection( int i ) {
		 orderConnection = i;
	}

	// legge il messaggio di pronto dal server oppure il segnale di KEEPALIVE al quale è necessario rispondere per notificarne la connessione ancora persistente
	//dopo il segnale di ready sono ricevute tutte le informazioni per iniziare la partita
	public  boolean readReady() {
		if(!connected) {
			if(t!=null && t.isAlive()) {
				t.interrupt();
			}
			return false;
		}
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

				return true;
				
			}
			if(line.equals(Protocol.KEEPALIVE)) {
				sendMessage(Protocol.KEEPALIVE);
			}
		} catch (IOException e) {
		}
		return false;
		
	}
	
	public  void setBattleRoyale(boolean battleRoyale) {
		this.battle = battleRoyale;
	}


	
	
	public  long getStartingTime() {
		return startingTime;
	}


	public  boolean isConnected() {
		return connected;
	}



	public  void  setConnected(boolean connected) {
		this.connected = connected;
	}


	//tenta di connettersi al server
	public  boolean connect () {
		try {
			socket = new Socket("localhost" , 8000);
			out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
			in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			setConnected(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println("Impossibile contattare il server");
			disconnect();
		} 
		return connected; 
	}
	
	
	public  boolean ready() {
		try {
			return in.ready();
		} catch (IOException e) {
			return false;
		}
	}
	
	public void disconnect() {	
			
		socket = null;
		out = null;
		in = null;
		setConnected(false);
	}
	
	//invia un messaggio 
	public  void sendMessage(String message) {
		if(out != null) {
			out.println(message);
		}		
	}
		
	//Legge dal server , qualora dovesse esserci un errore , allora si disconnette
	public  String read() {
		try {
			if(in != null && in.ready()) {
				String line = in.readLine();
				return line;
			}
		} catch (IOException e) {
			disconnect();
		}
		return null;
	}
	
	
 
	public  boolean isBombAdded() {
		return bombAdded;
	}


	public  void setBombAdded(boolean bombAdded) {
		this.bombAdded = bombAdded;
	}
	
/*
 * il metodo run del runnable fa si che il thread tenti di stabilire una connessione, 
 * se riuscita allora invia la modalità 
 * aspetta di ricevere il ready per far iniziare la partita
 * si noti che affincheè non ci siano errori , il client deve essere l'unico thread che può modificare l'interfaccia grafica
 * Qualora dovesse essere modifcata dall'esterno , allora è necessario interrompere il corrente thread per garantire l'accesso da un solo thread per volta
 * */
@Override
public void run() {
		connect();
		if(!isConnected()) {
			WindowsHandler.getWindowsHandler().setMenu();	
			return ;
		}
		if(battle)
			sendMessage(Protocol.BATTLEROYALE);
		else sendMessage(Protocol.MULTIPLAYER);
		while(!readReady() );
		WindowsHandler.getWindowsHandler().setGamePanel(true, battle);
}

	
}
