package gioco.utilities;
/*
 * Contiene tutte le impostazioni dell'applicazione , rendendole facilmente accessibili e modificabili
 * */
public class Settings {
	public final static int NONE = -1;
	public final static int LEFT = 0;
	public final static int UP = 1;
	public final static int RIGHT = 2;
	public final static int DOWN = 3;


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
	public static int LOGICBLOCKSIZEX = 1800 / LOGICWIDTH;
	public static int LOGICBLOCKSIZEY = 1560 / LOGICHEIGHT;
	public static int LOGICNORMALSPEED = LOGICBLOCKSIZEY / 12;
	
	public static String HOST = "localhost";
	
	public static int MAPSNUMBER = 8;

	public static int EXPLOSIONTIME = 12;
	public static int BRICKEXPLOSIONTIME = 8;
	public static int BOMBTIME = 80;
	public static int ENEMYDYINGTIME = 21;

	public static int selectedMap;
	public static int selectedbomberman = Settings.WHITE;

	// Grafica
	public static int WINDOWHEIGHT = 750;
	public static int WINDOWWIDTH = 825;

	public static int BLOCKSIZEX = 30;
	public static int BLOCKSIZEY = 30;

	public static int HEIGHTSTAT = 45;
	public static int wMenuButton = 270;
	public static int hMenuButton = 70;
	public static int wMenuButtonSelected = 285;
	public static int hMenuButtonSelected = 80;

	public static int wCustomButtonSelected = 230;
	public static int hCustomButtonSelected = 60;
	public static int wCustomButton = 220;
	public static int hCustomButton = 50;

	public static int factor3d = BLOCKSIZEY/2;
	
	public static int iconHeight = 40;
	public static int iconWidth = 40;
	public static int iconHeightSelected = 50;
	public static int iconWidthSelected = 50;
	
	public static int iconButtonWidth = 50;
	public static int iconButtonHeight = 50;
	public static int iconButtonWidthSelected = 60;
	public static int iconButtonHeightSelected = 60;
	
	public static int wLogo=600;
	public static int hLogo=300;
	
	public static int wresultIcon = 450;
	public static int hresultIcon = 350;
	
	public static int imageShow = 300;
	public static int mapsImages = 200;
	public static int bobmermenImages = 120;
	
	public static int xInfoImage = 140;
	public static int yInfoImage = 140;
	
}
