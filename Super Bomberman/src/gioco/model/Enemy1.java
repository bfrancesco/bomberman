package gioco.model;

import gioco.utilities.Settings;

public class Enemy1 extends Enemy{
	public Enemy1(int x , int y) {
			super(x ,y);
			width = Settings.BLOCKSIZEX*2/3;
			height = Settings.BLOCKSIZEY*2/3;
	}
}
