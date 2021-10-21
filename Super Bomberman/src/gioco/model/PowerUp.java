package gioco.model;

/*
 * I powerUp hanno una cella di riferimento  e un tipo 
 * permettono di aumentare le prorpiretà di un giocatore : velocità , raggio e numero di bombe
 * */
public class PowerUp {
	
	public final static int SPEEDUP = 8;
	public final static int DOUBLEFIRE = 9;
	public final static int DOUBLEBOMB = 10;
	
	public final static int EMPTY = 11;
	public final static int TOTALPOWERUPS= 3;
	
	private Cell cell;
	private int type;
	
	

	public PowerUp(int xCell, int yCell, int type) {
		cell = new Cell(xCell, yCell);
		this.type = type;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj)
			return true;
		if(obj == null)
			return false;
		if(obj.getClass() != this.getClass())
			return false;
		PowerUp up = (PowerUp) obj;
		return cell.equals(up.getCell()) && this.getType() == up.getType();
	}
	
	@Override
	public String toString() {
		return Integer.toString(cell.getxCell())+" " +Integer.toString(cell.getyCell())+" "+Integer.toString(type);
	}
	
}
