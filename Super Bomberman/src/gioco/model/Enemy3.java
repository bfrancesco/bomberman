package gioco.model;

import java.util.Random;

import gioco.utilities.Settings;

public class Enemy3 extends Enemy {
	
	public static final int VISIBLETIME = 600;
	private boolean visible;
	private int unseenTime;
	private int randomVisibilityTime;
	

	public Enemy3(int x, int y) {
		super(x, y);
		visible = true;
		unseenTime = 0;
		Random r = new Random();
		randomVisibilityTime = VISIBLETIME - r.nextInt(VISIBLETIME/2);
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
		if(state == Entity.DYING_EXPLOSION)
			visible= true;
		if(unseenTime == randomVisibilityTime) {
			if(visible)
				visible=false;
			else
				visible=true;
			unseenTime=0;
			Random r = new Random();
			randomVisibilityTime = VISIBLETIME - r.nextInt(VISIBLETIME/3);
		}
		else unseenTime++;
	}
	
	
	
	public int getRandomVisibilityTime() {
		return randomVisibilityTime;
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
