package gioco.model;

import gioco.utilities.Settings;

public class Bomb {
	private int xCell;
	private int yCell;
	private int timer;
	private Player player;

	public Bomb(int x, int y, int radius , Player player) {
		super();
		this.player = player;
		this.xCell = x;
		this.yCell = y;
		this.timer = Settings.BOMBTIME; 
	}

	public int getXCell() {
		return xCell;
	}

	public void setXCell(int x) {
		this.xCell = x;
	}
	
	

	public Player getPlayer() {
		return player;
	}
	
	public void update(int x , int y , int durata) {
		xCell = x;
		yCell = y;
		timer = durata;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getYCell() {
		return yCell;
	}

	public void setYCell(int y) {
		this.yCell = y;
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
		return b.getXCell() == this.getXCell() && b.getYCell() == this.getYCell();	
	}
	
	@Override
	public String toString() {
		
		return xCell+" " +yCell+ " "+timer;
	}
	
}
