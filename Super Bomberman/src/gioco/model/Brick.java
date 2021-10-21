package gioco.model;

import gioco.utilities.Settings;

/*
 * Ogni brick ha una cella di posizione e un tempo di esplosione
 * che viene decremenato, allo zero dovrebbe essere rimosso
 * */
public class Brick {

	private Cell cell;
	private int explosionTime;

	public Brick(int xCell, int yCell) {
		super();
		cell = new Cell(xCell, yCell);
		explosionTime = Settings.BRICKEXPLOSIONTIME;
	}

	public Cell getCell() {
		return cell;
	}
	

	public void setCell(Cell cell) {
		this.cell = cell;
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
		return this.cell.equals(tmp.cell);
	}
	
	public boolean equals(int xCell , int yCell) {
		return xCell == this.getCell().getxCell() && yCell == this.getCell().getyCell();
	}

}