package gioco.model;

import gioco.Settings;

public class Entity {
	protected int x;
	protected  int y;
	protected int width;
	protected int height;
	protected int direction;
	protected int speed;
	
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

	public int getY() {
		return y;
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
	
	public Entity(int x , int y) {
		this.x = x;
		this.y = y;
		this.speed = Settings.NORMALSPEED;
		this.height = Settings.BLOCKSIZEY-3;
		this.width = Settings.BLOCKSIZEX-3;
		direction = Settings.DOWN;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
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
	
	
	public int rightSide() {
		return x+width;
	}
	
	public int downSide() {
		return y+height;
	}
	
	public int leftBlock() {
		return (x)/Settings.BLOCKSIZEX;
	}
	public int rightBlock() {
		return rightSide()/Settings.BLOCKSIZEX;
	}
	
	public int upBlock() {
		return (y)/Settings.BLOCKSIZEY;
	}
	public int downBlock() {
		return downSide()/Settings.BLOCKSIZEY;
	}
	
	public int xcenterBlock() {
		return (x + rightSide()) / (2*Settings.BLOCKSIZEX);
	}
    
	public int ycenterBlock() {
		return (y + downSide()) / (2*Settings.BLOCKSIZEY);
	}
	
}
