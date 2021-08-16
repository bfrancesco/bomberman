package gioco.model;

import gioco.utilities.Settings;

public class Explosion {
	public static final int CENTRAL= 13;
	public static final int MIDDLE = 14;
	public static final int LAST = 15;
	private int xCell ; 
	private int yCell;
	private int  durata ; 
	private int type ;
	private int direction;
	private Player player ;
	public Explosion(int x , int y , int type , int direction , Player player) {
		this.xCell =x ;
		this.yCell=y;
		this.type = type;
		this.direction = direction;
		this.durata = Settings.EXPLOSIONTIME;
		this.player = player ;
	}
	
	public Explosion(int x , int y , int type ,int durata ,  int direction , Player player) {
		this.xCell =x ;
		this.yCell=y;
		this.type = type;
		this.durata = durata;
		this.direction = direction;
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
	public int getXCell() {
		return xCell;
	}
	public int getYCell() {
		return yCell;
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return xCell+" " +yCell+ " " +type+" " +durata+" "+direction;
	}
	
	
}
