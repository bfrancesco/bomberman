package gioco.model;

import gioco.utilities.Settings;

public class Enemy1 extends Enemy{
	public Enemy1(int x , int y , int id) {
			super(x ,y , id);
	}
	@Override
	protected int getType() {
		return 1;
	}
}
