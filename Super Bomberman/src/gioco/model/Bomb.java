package gioco.model;

import gioco.Settings;

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

	public int getX() {
		return xCell;
	}

	public void setX(int x) {
		this.xCell = x;
	}
	
	

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getY() {
		return yCell;
	}

	public void setY(int y) {
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
		return b.getX() == this.getX() && b.getY() == this.getY();	
	}

}
