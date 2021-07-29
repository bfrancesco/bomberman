package gioco;

public class Settings {
	public final static int LEFT = 0;
	public final static int UP = 1;
	public final static int RIGHT = 2;
	public final static int DOWN = 3;
	
	public static final int TOTALCOLLISION = 4;
	public static final int RIGHTCOLLISION = 5;
	public static final int LEFTCOLLISION = 6;
	public static final int DOWNCOLLISION = 7;
	public static final int UPCOLLISION = 8;
	public static final int NOCOLLISION = 9;
	
	public final static int PLAYER1 = 10;
	public final static int PLAYER2 =11;
	public final static int ENEMY1 = 12;
	public final static int ENEMY2 = 13;
	public final static int ENEMY3 = 14;
	
	public static int BLOCKSIZEX = 600/13;
	public static int BLOCKSIZEY = 600/13;
	public static int NORMALSPEED = BLOCKSIZEX/9;
	
	public static int EXPLOSIONTIME = 10;
	public static int BOMBTIME = 65;
}
