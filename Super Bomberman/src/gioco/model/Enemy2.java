package gioco.model;

/*
 * Nemico di tipo due  , il nemico è soggetto a cambiamenti di velocità che può aumentare o diminuire , gestito dalla variabile increasing 
 * quando il cambiamento a raggiunto il CHANGEMENTTIME , allora cambia la velocità diminuendo o aumentando in base alla variabile incresing moltiplicando o dividendo per SPEEDFACTOR
  */

public class Enemy2 extends Enemy {
	private int changement;
	private boolean increasing;
	private static final int CHANGEMENTTIME = 150;
	private static final int SPEEDFACTOR = 2;
	public Enemy2(int x, int y,int id) {
		super(x, y,id);
		changement = 0;
		increasing = true;
	}
	

	private void changeSpeed(){
		if(changement==CHANGEMENTTIME) {
			if(increasing) {
				speed*=SPEEDFACTOR;
				increasing = false;
			}
			else {
				speed/=SPEEDFACTOR;
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
