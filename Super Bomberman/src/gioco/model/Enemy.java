package gioco.model;


import java.util.ArrayList;
import java.util.Random;

import gioco.utilities.Settings;

/*
 * Enemy � una estensione di entit� ed � classe padre di altre classi specializzati che sono gli enemy di vario tipo ,
 * ogni nemico ha un ID e un tempo di eliminazione, dopo del quale dovrebbe essere rimosso
 * */
public class Enemy extends Entity{
	
	protected int dyingTime;
	protected int ID;
	
	public Enemy(int x, int y , int id) {
		super(x ,y);
		dyingTime = Settings.ENEMYDYINGTIME;
		state = Entity.IDLE_DOWN;
		this.ID = id;
		Random r = new Random();
		r.setSeed(System.currentTimeMillis()+x);
		int dir = r.nextInt(4);
		this.direction = dir;
		this.setSpeed(Settings.LOGICNORMALSPEED/3*2);
	}

	public void changeDirection(ArrayList<Integer> directions) {
		if(directions.size()==0) {
			state = Entity.IDLE_DOWN;
			direction = Settings.NONE;
		}
		else {
			Random r = new Random();
			r.setSeed(System.currentTimeMillis()+ID*x*y);
			int dir = r.nextInt(directions.size());
			this.direction = directions.get(dir);
			switch(direction) {
				case Settings.DOWN:
					state = WALKING_DOWN;
					break;
				case Settings.RIGHT:
					state = WALKING_RIGHT;
					break;
				case Settings.LEFT:
					state = WALKING_LEFT;
					break;
				case Settings.UP:
					state = WALKING_UP;
					break;
			}
		}
	}
	
	
	
	public int getID() {
		return ID;
	}


	public void move() {
		super.move(this.direction);
	}
	
	
	public void decreasedyingTime() {
		dyingTime-=1;
	}

	public int getDyingTime() {
		return dyingTime;
	}

	public void setDyingTime(int dyingTime) {
		this.dyingTime = dyingTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(obj.getClass() != this.getClass())
			return false;
		Enemy e = (Enemy) obj;
		return e.ID == this.ID;
	}
	

	
	//facilita la comunicazione server - client mediante il protocollo
	@Override
	public String toString() {
		return ID+ " " +x+" " +y+" " +state ;
	}
	
	
	
}
