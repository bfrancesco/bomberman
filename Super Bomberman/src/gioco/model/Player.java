package gioco.model;

import gioco.utilities.Settings;

public class Player extends Entity{
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
	
	private int lifes;
	private int state;
	private int bombs;
	private int radius;
	private int points;

	
	public Player(int x , int y) {
		super(x,y);
		direction = Settings.RIGHT;
		state = Player.IDLE_DOWN;
		lifes = 1;
		bombs = 1;
		radius = 2;
		width = Settings.BLOCKSIZEX*8/10;
		height = Settings.BLOCKSIZEY*9/10;
		points = 0;
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

	public int getLifes() {
		return lifes;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public void decreaseLifes() {
		--lifes;
	}



	public int getRadius() {
		return radius;
	}



	public void setRadius(int radius) {
		this.radius = radius;
	}



	public void setLifes(int lifes) {
		this.lifes = lifes;
	}
	
	
	
}
	

