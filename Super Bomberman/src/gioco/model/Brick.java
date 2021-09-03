package gioco.model;

import gioco.utilities.Settings;

public class Brick {

	private int xCell;
	private int yCell;
	private int explosionTime;

	public Brick(int xCell, int yCell) {
		super();
		this.xCell = xCell;
		this.yCell = yCell;
		explosionTime = Settings.BRICKEXPLOSIONTIME;
	}

	public int getxCell() {
		return xCell;
	}

	public void setxCell(int xCell) {
		this.xCell = xCell;
	}

	public int getyCell() {
		return yCell;
	}

	public void setyCell(int yCell) {
		this.yCell = yCell;
	}

	public int getExplosionTime() {
		return explosionTime;
	}

	public void decreaseExplosionTime() {
		explosionTime -= 1;
	}
	
	public boolean inExplosion() {
		return explosionTime<Settings.BRICKEXPLOSIONTIME;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if(obj == null)
			return false;
		if(obj.getClass()!=this.getClass())
			return false;
		Brick tmp = (Brick) obj;
		return tmp.xCell == this.xCell && tmp.yCell == this.yCell;
	}
	
	public boolean equals(int xCell , int yCell) {
		return xCell == this.xCell && yCell == this.yCell;
	}

}