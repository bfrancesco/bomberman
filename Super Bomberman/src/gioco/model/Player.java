package gioco.model;

import java.util.Vector;

import gioco.utilities.Settings;

/*
 * Il player è una entità che è di diverso tipo, ha un numero di bombe a disposizione , ha un raggio dell'esplosione e dei punti 
 * Raggio , bombe e velocità possono essere aumentati dai poweup
 * */
public class Player extends Entity{
	
	private int bombs;
	private int radius;
	private int points;
	private int type;
	private Vector<Integer> powerups;
	
	public Player(int x , int y , int type) {
		super(x,y);
		powerups = new Vector<Integer>();
		direction = Settings.RIGHT;
		state = Player.IDLE_DOWN;
		bombs = 1;
		radius = 2;
		width = Settings.LOGICBLOCKSIZEX*8/10;
		points = 0;
		this.type =type;
	}

	
	
	public int getPoints() {
		return points;
	}



	public void increasePoints(int pointsPlayer) {
		this.points += pointsPlayer;
	}
	

	public int getBombs() {
		return bombs;
	}


	public void  decreaseBombs() {
		bombs--;
	}
	
	public void  increaseBombs() {
		bombs++;
	}



	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
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
	
	public boolean addPowerUp(int type) {
		switch (type) {
		case PowerUp.EMPTY:
			return false;
		case PowerUp.SPEEDUP:
			setSpeed(speed*5/4);
			break;
		case PowerUp.DOUBLEFIRE:
			increaseRadius();
			break;
		case PowerUp.DOUBLEBOMB:
			increaseBombs();
			break;
		}
		powerups.add(type);
		return true;
	}

	public int getRadius() {
		return radius;
	}


	public void update(int x , int y , int state , int points) {
		super.update(x, y, state);
		this.points = points;
	}


	public int getType() {
		return type;
	}


	public Vector<Integer> getPowerups() {
		return powerups;
	}


	public void setType(int type) {
		this.type = type;
	}



	public void increaseRadius() {
		this.radius = this.radius+ 1;
	}

	//semplifica la comunicazione client-server
	@Override
	public String toString() {
		return type + " " + x + " " + y+" "+state+" "+points;
	}
	
	
}
	

