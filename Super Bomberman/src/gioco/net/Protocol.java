package gioco.net;
/*
 *Protocollo di comunicazione fra server e client
 *i rispettivi metodi servono ad appendere la keyword prima dei dati
 * per poter notificare al server il tipo di dato che si sta ricevendo
 */
 
public class Protocol {
	public static final String READY = "rd";
	public static final String MOVE = "mv";
	public static final String STATE = "st";
	public static final String PLAYER= "pl";
	public static final String ENEMY = "en";
	public static final String EXPLOSION = "ex";
	public static final String BOMB = "b";
	public static final String BOMBADDED = "ba";
	public static final String ENDCOMUNICATION = "END";
	public static final String DISCONNECTION = "D";
	public static final String KEEPALIVE = "K";
	public static final String POWERUP ="u";
	
	public static final String BATTLEROYALE = "br";
	public static final String MULTIPLAYER = "mp";
	

	
	public static final String state(int entityState) {
		return STATE+" "+entityState;
	}
	
	
	public static String player(String p ) {
		return PLAYER + " " + p ;
	}
	
	public static String enemy(String e ) {
		return ENEMY + " " +e ;
	}
	
	public static String bomb(String b ) {
		return BOMB + " " + b;
	}
	
	
	public static String explosion(String ex ) {
		return EXPLOSION + " "+ex;
	}
	
	public static String startingInfo(int player , int map, long time ) {
		return Integer.toString(player)+" "+map+ " "+Long.toString(time);
	}
	
	public static String powerup(String p) {
		return POWERUP+" " +p;
	}
	
}
