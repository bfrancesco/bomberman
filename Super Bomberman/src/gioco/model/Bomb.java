package gioco.model;

import gioco.utilities.Settings;

/*Classe che identifica una bomba , ha una cella di riferimento , un timer , un raggio e un Player che l'ha posizionata
 * Una volta posizionata 
 * ad ogni passo deve decrementare il timer
 * */
public class Bomb {
	private Cell cell;
	private int timer;
	private int radius;
	private Player player;

	public Bomb(int x, int y, int radius ,  Player player) {
		super();
		cell = new Cell(x , y);
		this.player = player;
		this.radius = radius;
		this.timer = Settings.BOMBTIME; 
	}
	
	public Bomb(int x, int y, int radius, int timer ,Player player) {
		super();
		this.player = player;
		this.radius = radius;
		cell = new Cell(x , y);
		this.timer = timer; 
	}

	
	
	public int getRadius() {
		return radius;
	}

	public Cell getCell() {
		return cell;
	}
	
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public int getXCell() {
		return cell.getxCell();
	}
	
	public int getYCell() {
		return cell.getyCell();
	}
	

	public Player getPlayer() {
		return player;
	}
	
	public void update(int x , int y , int durata) {
		cell.setxCell(x);
		cell.setyCell(y);
		timer = durata;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}


	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public void decreaseTimer() {
		--this.timer;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(obj.getClass() != this.getClass())
			return false;
		Bomb b = (Bomb) obj;
		return this.getCell().equals(b.getCell());
	}
	
	//utile per facilitare la comunicazione fra client e server
	@Override
	public String toString() {		
		return cell.getxCell()+" " +cell.getyCell()+ " " +radius +" "+timer+ " "+player.getType();
	}
	
}
