package gioco.net;

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
	
	public static String startingInfo(int player , String map ) {
		return Integer.toString(player)+" "+map;
	}
	/*public static int[] parsePosition(String line) {
		String[] res = line.split(" ");
		if(res.length != 3 || !res[0].equals(POSITION))
			return null;
		int[] ret = new int[3];
		ret[0] = Integer.parseInt(res[1]);
		ret[1] = Integer.parseInt(res[2]);
		return ret;
	}*/
	
}
