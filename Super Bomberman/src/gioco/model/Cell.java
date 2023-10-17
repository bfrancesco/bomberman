package gioco.model;

//E' composto da una coppia di interi che identifica una posizione 
public class Cell {
	private int xCell;
	private int yCell;
	
	public Cell(int x , int y) {
		this.xCell = x;
		this.yCell = y;
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj==this)
			return true;
		if(obj == null)
			return false;
		if(obj.getClass() != this.getClass())
			return false;
		Cell tmp = (Cell) obj;
		return tmp.xCell == this.xCell && tmp.yCell == this.yCell;
		
	}
	
	
}
