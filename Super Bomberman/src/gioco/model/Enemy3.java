package gioco.model;

import java.util.Random;

import gioco.utilities.Settings;

public class Enemy3 extends Enemy {
	
	public static final int VISIBLETIME = 600;
	private boolean visible;
	private int unseenTime;
	private int randomVisibilityTime;
	

	public Enemy3(int x, int y, int id) {
		super(x, y , id);
		visible = true;
		unseenTime = 0;
		Random r = new Random();
		randomVisibilityTime = VISIBLETIME - r.nextInt(VISIBLETIME/2);
	}

	public void Teleport(int px1, int py1, int px2, int py2) {
		x = px1*Settings.LOGICBLOCKSIZEX;
		y = py1*Settings.LOGICBLOCKSIZEY;
		Random r = new Random();
		if (r.nextBoolean()) {
			x = px2*Settings.LOGICBLOCKSIZEX;
			y = py2*Settings.LOGICBLOCKSIZEY;
		}
	}
	
	public void Teleport(int bloccox, int bloccoy) {
		x = bloccox*Settings.LOGICBLOCKSIZEX;
		y = bloccoy*Settings.LOGICBLOCKSIZEY;
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
	
	@Override
	protected int getType() {
		return 3;
	}
	
	public void update(int x, int y, int state , boolean visible , int unseenTime , int visibilityTime) {
		// TODO Auto-generated method stub
		super.update(x, y, state);
		this.visible = visible;
		this.unseenTime = unseenTime;
		this.randomVisibilityTime = visibilityTime;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() +" " + visible +  " "+ unseenTime+" "+randomVisibilityTime;
	}

	
}
