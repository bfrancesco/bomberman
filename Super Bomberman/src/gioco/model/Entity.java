package gioco.model;

import gioco.utilities.Settings;


/*
 * Classe madre  da cui ereditano nemici e player 
 * Ha una posizione logica espressa da x e y , un'altezza e larghezza
 * Possiede una direzione ,  una velocità e uno stato
 * Lo stato può assumere uno dei valori static concessi , lo stato può cambiare in base alla direzione e al gioco
 * Quando una entità muore, può entrare negli stati di DYING
 * altrimenti quando vince entra nello stato di WINNING
 */
public class Entity {
	
	
	public static final int IDLE_LEFT = 0;
	public static final int IDLE_RIGHT = 1;
	public static final int IDLE_UP = 2;
	public static final int IDLE_DOWN = 3;

	public static final int WALKING_LEFT = 4;
	public static final int WALKING_RIGHT = 5;
	public static final int WALKING_UP = 6;
	public static final int WALKING_DOWN = 7;

	public static final int DYING_EXPLOSION = 8;
	public static final int DYING_ENEMY = 9;
	public static final int WINNING= 10;
	
	
	protected int x;
	protected  int y;
	protected int width;
	protected int height;
	protected int direction;
	protected int speed;
	protected int state;

	
	public  int getState() {
		return state;
	}


	public  void setState(int state) {
		this.state = state;
	}

	//permette di ottenere il lato di sinistra dell'entità
	public int getX() {
		return x;
	}
		
	
	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}



	public void setX(int x) {
		this.x = x;
	}
	//permette di ottenere il lato di sopra dell'entità
	public int getY() {
		return y;
	}
	
	public boolean isMoving() {
		return state == Entity.WALKING_RIGHT || state == Entity.WALKING_LEFT || state == Entity.WALKING_UP || state == Entity.WALKING_DOWN;
	}

	public void update(int x , int y , int state) {
		this.x = x;
		this.y = y;
		this.state= state;
	}
	
	
	public void setWidth(int width) {
		this.width = width;
	}


	public void setHeight(int height) {
		this.height = height;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean isDead() {
		return state == Player.DYING_ENEMY || state == Player.DYING_EXPLOSION;
	} 
	
	public Entity(int x , int y) {
		this.x = x;
		this.y = y;
		this.speed = Settings.LOGICNORMALSPEED;
		this.height = Settings.LOGICBLOCKSIZEY-5;
		this.width = Settings.LOGICBLOCKSIZEX-5;
		direction = Settings.DOWN;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//indica se si scontra con una cella
	public boolean collisionCell(Cell cell) {
		return cell.getxCell() == xcenterBlock() && (cell.getyCell() == ycenterBlock())
				|| ((cell.getxCell() == rightBlock() || cell.getxCell() == leftBlock())
						&& (cell.getyCell() == upBlock() || cell.getyCell() == downBlock()));
		
	}
	
	public void move(int direction) {
		switch(direction) {
			case Settings.LEFT :
					x-=speed;
					break;
			case Settings.RIGHT:
					x+=speed;
					break;
			case Settings.DOWN:
					y+=speed;
					break;
			case Settings.UP:
					y-=speed;
					break;	
		}
	}
	//si muove con direzione e velocità settati dall'esterno
	public void move(int direction , int Externalspeed) {
		switch(direction) {
			case Settings.LEFT :
					x-=Externalspeed;
					break;
			case Settings.RIGHT:
					x+=Externalspeed;
					break;
			case Settings.DOWN:
					y+=Externalspeed;
					break;
			case Settings.UP:
					y-=Externalspeed;
					break;	
		}
	}
	
	//permette di ottenere il lato di destra dell'entità
	public int rightSide() {
		return x+width;
	}
	//permette di ottenere il lato di sotto dell'entità
	public int downSide() {
		return y+height;
	}
	
	//permette di ottenere la coordinata x del bloccoin cui si trova il lato di sinistra
	public int leftBlock() {
		return (x)/Settings.LOGICBLOCKSIZEX;
	}
	//permette di ottenere la coordinata x del blocco  in cui si trova il lato di destra
	public int rightBlock() {
		return rightSide()/Settings.LOGICBLOCKSIZEX;
	}
	//permette di ottenere la coordinata y del blocco in cui si trova il lato di sopra
	public int upBlock() {
		return (y)/Settings.LOGICBLOCKSIZEY;
	}
	//permette di ottenerela coordinata y del blocco in cui si trova il lato di sotto
	public int downBlock() {
		return downSide()/Settings.LOGICBLOCKSIZEY;
	}
	//permette di ottenere l'ascissa del blocco in cui si trova il centro
	public int xcenterBlock() {
		return (x + rightSide()) / (2*Settings.LOGICBLOCKSIZEX);
	}
    
	//permette di ottenere l'rdinata del blocco in cui si trova il centro
	public int ycenterBlock() {
		return (y + downSide()) / (2*Settings.LOGICBLOCKSIZEY);
	}
	
}
