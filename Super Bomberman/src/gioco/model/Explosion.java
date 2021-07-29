package gioco.model;

import gioco.utilities.Settings;

public class Explosion {
	public static final int CENTRAL= 13;
	public static final int MIDDLE = 14;
	public static final int LAST = 15;
	private int x ; 
	private int y;
	private int  durata ; 
	int type ;
	int direction;
	private Player player ;
	public Explosion(int x , int y , int type , int direction , Player player) {
		this.x =x ;
		this.y=y;
		this.type = type;
		this.direction = direction;
		this.durata = Settings.EXPLOSIONTIME;
		this.player = player ;
	}
	public int getDurata() {
		return durata;
	}
	public void decreaseDurata() {
		--durata;
	}
	
	
	
	public Player getPlayer() {
		return player;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getType() {
		return type;
	}
	public int getDirection() {
		return direction;
	}
	
	public void setDurata(int durata) {
		this.durata = durata;
	}
	
	
	
}
