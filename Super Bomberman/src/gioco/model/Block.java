package gioco.model;

/*
 * Indica la tipologia di blocco, presenta delle proprietà come breakable o powerUp
 * in base al suo tipo può cambiare stato rompendosi esplodendo 
 * */
public class Block {
	public final static int BRICK = 4;
	public final static int IRON = 5 ;
	public final static int BOMB = 6;
	public final static int FLOOR = 7;

	private int type;
	private boolean breakable;
	private int powerUp;
	
	public Block() {
		powerUp = PowerUp.EMPTY;
		type = Block.FLOOR;
		breakable = true;
	}
	
	public Block(int type ) {
		this.type = type ; 
		powerUp = PowerUp.EMPTY;
		if(type == Block.IRON) {
				breakable = false;
		}
		else breakable = true;
	}
	
	public void explode(){
		if(type == Block.BRICK ) {
			type = Block.FLOOR;
		}
	}
	
	public boolean hasPowerUp() {
		return powerUp!=PowerUp.EMPTY;
	}
	
	
	public boolean equals(Object obj) {
		if(this == obj)
				return true;
		if(obj == null)	
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		Block b = (Block) obj;
		return b.type == this.type;
	}

	public int getType() {
		return type;
	}

	public void setType(int tipo) {
		this.type = tipo;
		if(tipo == Block.BRICK)
				breakable = true;
		else breakable = false;
	}

	public boolean isBreakable() {
		return breakable;
	}
	
	public boolean isWalkable() {
		if(type == Block.FLOOR)
			return true;
		return false;
	}

	public int getPowerUp() {
		return powerUp;
	}
	
	

	public synchronized void setPowerUp(int powerUp) {
		this.powerUp = powerUp;
	}
	
	
}
