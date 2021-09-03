package gioco.model;

import gioco.utilities.Settings;

public class Enemy2 extends Enemy {
	private int changement;
	private boolean increasing;
	
	public Enemy2(int x, int y,int id) {
		super(x, y,id);
		changement = 0;
		increasing = true;
	}
	

	private void changeSpeed(){
		if(changement==150) {
			if(increasing) {
				speed*=2;
				increasing = false;
			}
			else {
				speed/=2;
				increasing = true;
			}
			changement = 0;
		}
		else changement++;
	}

	@Override
	public void move() {
		changeSpeed();
		super.move(direction);
	}
	
	@Override
	protected int getType() {
		return 2;
	}
	

}
