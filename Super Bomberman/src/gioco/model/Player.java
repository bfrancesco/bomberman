package gioco.model;

import gioco.utilities.Settings;

public class Player extends Entity{
	
	private int lifes;
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
	
