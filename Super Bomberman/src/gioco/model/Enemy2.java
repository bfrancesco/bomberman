package gioco.model;

import gioco.Settings;

public class Enemy2 extends Enemy {
	private int changement;
	boolean increasing;
	
	public Enemy2(int x, int y) {
		super(x, y);
		changement = 0;
		increasing = true;
		width = Settings.BLOCKSIZEX*2/3;
		height = Settings.BLOCKSIZEY*2/3;
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
