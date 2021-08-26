package gioco.model;

public class Block {
	public final static int BRICK = 4;
	public final static int IRON = 5 ;
	public final static int BOMB = 6;
	public final static int FLOOR = 7;
	
	int type;
	boolean breakable;
	
	public Block() {
		
		type = Block.FLOOR;
		breakable = true;
	}
	
	public Block(int type ) {
		this.type = type ; 
		if(type == Block.IRON) {
				breakable = false;
		}
		else breakable = true;
	}
	
	public void exploded(){
		if(type == Block.BRICK ) {
			type = Block.FLOOR;
		}
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
	}

	public boolean isBreakable() {
		return breakable;
	}
	
	public boolean isWalkable() {
		if(type == Block.FLOOR)
			return true;
		return false;
	}
	
}
