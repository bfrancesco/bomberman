package gioco.utilities;

public class Settings {
	public final static int NONE= -1;
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
	public final static int PLAYER2 = 11;
	public final static int PLAYER3 = 12;
	public final static int PLAYER4 = 13;
	public final static int PLAYER5 = 14;
	
	public final static int COLORS = 5;
	public final static int WHITE = 15;
	public final static int BLACK = 16;
	public final static int ORANGE = 17;
	public final static int BLUE = 18;
	public final static int GREEN = 19;
	
	
	
	public static int LOGICWIDTH = 15;
	public static int LOGICHEIGHT = 13;
	public static int LOGICBLOCKSIZEX = 1800/ LOGICWIDTH;
	public static int LOGICBLOCKSIZEY = 1560/ LOGICHEIGHT;
	public static int LOGICNORMALSPEED = LOGICBLOCKSIZEY/12; 
	
	
	public static int MAPSNUMBER = 8;
	
	//public static int WINDOWHEIGHT = 810;
	//public static int WINDOWWIDTH= 900;
	public static int WINDOWHEIGHT = 750;
	public static int WINDOWWIDTH= 825;
	
	public static int BLOCKSIZEX = WINDOWWIDTH/15;
	public static int BLOCKSIZEY = (WINDOWHEIGHT-40)/13;
	//public static int NORMALSPEED = BLOCKSIZEY/12;
	
	public static int EXPLOSIONTIME = 12;
	public static int BRICKEXPLOSIONTIME = 8;
	public static int BOMBTIME = 80;
	public static  int ENEMYDYINGTIME = 20;
	
	
	public static int selectedMap;
	public static int selectedbomberman = Settings.WHITE;
	
	
	
}
