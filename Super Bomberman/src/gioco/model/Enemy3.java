package gioco.model;

import java.util.Random;

import gioco.Settings;

public class Enemy3 extends Enemy {
	
	public static final int stateTime = 600;
	public boolean visible;
	public int unseenTime;

	public Enemy3(int x, int y) {
		super(x, y);
		visible = true;
		unseenTime = 0;
		width=Settings.BLOCKSIZEX*5/6;
		height = Settings.BLOCKSIZEY*5/6;
	}

	public void Teleport(int px1, int px2, int py1, int py2) {
		x = px1;
		y = py1;
		Random r = new Random();
		if (r.nextBoolean()) {
			x = px2;
			y = py2;
		}
	}
	
	public void Teleport(int bloccox, int bloccoy) {
		x = bloccox*Settings.BLOCKSIZEX;
		y = bloccoy*Settings.BLOCKSIZEY;
	}
	

	public void changeVisibility() {
		if(unseenTime == stateTime) {
			if(visible)
				visible=false;
			else
				visible=true;
			unseenTime=0;
		}
		else unseenTime++;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getUnseenTime() {
		return unseenTime;
	}
	

	
}
