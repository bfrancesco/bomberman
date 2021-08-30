package gioco.model;

import gioco.utilities.Settings;

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
		this.height = Settings.LOGICBLOCKSIZEY-3;
		this.width = Settings.LOGICBLOCKSIZEX-3;
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
		return (x)/Settings.LOGICBLOCKSIZEX;
	}
	public int rightBlock() {
		return rightSide()/Settings.LOGICBLOCKSIZEX;
	}
	
	public int upBlock() {
		return (y)/Settings.LOGICBLOCKSIZEY;
	}
	public int downBlock() {
		return downSide()/Settings.LOGICBLOCKSIZEY;
	}
	
	public int xcenterBlock() {
		return (x + rightSide()) / (2*Settings.LOGICBLOCKSIZEX);
	}
    
	public int ycenterBlock() {
		return (y + downSide()) / (2*Settings.LOGICBLOCKSIZEY);
	}
	
}
