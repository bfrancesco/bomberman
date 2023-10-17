package gioco.model;

import java.util.Random;
import java.util.Vector;

import gioco.utilities.Settings;
/*
 * Nemico di tipo 3
 * Il nemico di tipo 3 può cambiare visibilità quando unseenTime raggiunge il VISIBLETIME
 * Il VISIBLETIME può variare fra un intervallo compreso fra il massimo e la metà
 * Una volta che torna ad essere visibile , è possibile teletrasportarlo su un player scelto casualmente da un vettore di player
 */
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
		r.setSeed(System.currentTimeMillis()+x+y);
		randomVisibilityTime = VISIBLETIME - r.nextInt(VISIBLETIME/2);
	}

	public void Teleport(Vector<Player> alivePlayers) {
		if(alivePlayers.size() == 0)
			return;
		Random r = new Random();
		r.setSeed(System.currentTimeMillis()+x+y);
		Player p = alivePlayers.get(r.nextInt(alivePlayers.size()));
		setX(p.xcenterBlock()*Settings.LOGICBLOCKSIZEX);
		setY(p.ycenterBlock()*Settings.LOGICBLOCKSIZEY);
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
			r.setSeed(System.currentTimeMillis()+x+y);
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
	
	
	public void update(int x, int y, int state , boolean visible , int unseenTime , int visibilityTime) {
		// TODO Auto-generated method stub
		super.update(x, y, state);
		this.visible = visible;
		this.unseenTime = unseenTime;
		this.randomVisibilityTime = visibilityTime;
	}
	
	@Override
	public String toString() {
		return super.toString() +" " + visible +  " "+ unseenTime+" "+randomVisibilityTime;
	}

	
}
