package gioco.model;

import java.util.ArrayList;
import java.util.Random;

import gioco.Settings;

public class Enemy extends Entity{
	
	public Enemy(int x, int y) {
		super(x ,y);
		Random r = new Random();
		r.setSeed(System.currentTimeMillis()+x);
		int dir = r.nextInt(4);
		this.direction = dir;
		this.setSpeed(Settings.NORMALSPEED/2);
	}

	public void changeDirection(ArrayList<Integer> directions) {
		Random r = new Random();
		r.setSeed(System.currentTimeMillis()+x);
		int dir = r.nextInt(directions.size());
		this.direction = directions.get(dir);
	}
	
	public void move() {
		switch(this.direction) {
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
}
