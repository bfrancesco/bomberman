package gioco.model;

import gioco.utilities.Settings;

public class Enemy2 extends Enemy {
	private int changement;
	boolean increasing;
	
	public Enemy2(int x, int y) {
		super(x, y);
		changement = 0;
		increasing = true;
	}
	

	private void changeSpeed(){
		if(changement==150) {
			if(increasing) {
				speed++;
				increasing = false;
			}
			else {
				speed--;
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
	

}
