package gioco.model;

import gioco.utilities.Settings;
/*
 * Identifica una esplosione che ha una cella , una durata , direzione , tipo e player che l'ha causata
 * una volta creata , esiste finchè la durata non raggiunge l'EXPLOSIONTIME
 * il tipo è compreso fra CENTRAL MIDDLE e LAST
 */
public class Explosion {
	public static final int CENTRAL= 13;
	public static final int MIDDLE = 14;
	public static final int LAST = 15;
	private Cell cell;
	private int  durata ; 
	private int type ;
	private int direction;
	private Player player ;
	public Explosion(int x , int y , int type , int direction , Player player) {
		cell = new Cell(x,y);
		this.type = type;
		this.direction = direction;
		this.durata = Settings.EXPLOSIONTIME;
		this.player = player ;
	}
	
	public Explosion(int x , int y , int type ,int durata ,  int direction , Player player) {
		cell = new Cell(x,y);
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
		return cell.getxCell();
	}
	public int getYCell() {
		return cell.getyCell();
	}
	public int getType() {
		return type;
	}
	
	public Cell getCell() {
		return cell;
	}
	
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public int getDirection() {
		return direction;
	}
	
	public void setDurata(int durata) {
		this.durata = durata;
	}
	
	//Facilita la comunicazione server-client
	@Override
	public String toString() {
		return cell.getxCell()+" " +cell.getyCell()+ " " +type+" " +durata+" "+direction;
	}
	
	
}
