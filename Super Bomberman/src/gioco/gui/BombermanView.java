package gioco.gui;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import gioco.model.Player;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class BombermanView {

	Image currentImage;
	int currentState;
	int index;
	int player;
	
	public BombermanView(int player) {

		currentState = Player.IDLE_DOWN;
		this.player = player;
		
	}
	
	public void setState(int state) {
		this.currentState = state;			
	}
	
	
	public Image getCurrentImage() {
		return currentImage;
	}


	public int getState() {
		return currentState;
	}

	public void update(int state) {
		switch (state) {
			case Player.IDLE_DOWN :
				index = 0;
				currentImage = Resources.downBomberman(player).get(index);
				currentState = state;
				break;
			case Player.IDLE_UP :
				index = 0;
				currentImage = Resources.upBomberman(player).get(index);
				currentState = state;
				break;
			case Player.IDLE_RIGHT :
				index = 0;
				currentImage =Resources.rightBomberman(player).get(index);
				currentState = state;
				break;
			case Player.IDLE_LEFT :
				index = 0;
				currentImage = Resources.leftBomberman(player).get(index);
				currentState = state;
				break;
			case Player.WALKING_DOWN :
				if(state == currentState)
					index =++index % (Resources.downBomberman(player).size()*3);
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.downBomberman(player).get(index/3);
				break;
			case Player.WALKING_UP :
				if(state == currentState) 
					index = ++index % (Resources.upBomberman(player).size()*3);						
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.upBomberman(player).get(index/3); 
				break;
			case Player.WALKING_RIGHT :
				if(state == currentState) 
					index = ++index % (Resources.rightBomberman(player).size()*3);
				
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.rightBomberman(player).get(index/3); 
				break;
			case Player.WALKING_LEFT :
				if(state == currentState) 
					index = ++index % (Resources.leftBomberman(player).size()*3);
				else 
				index = 0;
				currentState = state;
				currentImage = Resources.leftBomberman(player).get(index/3);
				break;
			case Player.DYING_ENEMY : 
				if(state == currentState) {
					if(index!=(Resources.dyingByEnemyBomberman(player).size()-1)*3)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.dyingByEnemyBomberman(player).get(index/3);
				break;
			case Player.DYING_EXPLOSION : 
				if(state == currentState) {
					if(index!=(Resources.dyingExplosionBomberman(player).size()-1)*5)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.dyingExplosionBomberman(player).get(index/5);
				break;
			case Player.WINNING: 
				if(state == currentState) {
					if(index!=(Resources.victoryBomberman(player).size()-1)*5)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.victoryBomberman(player).get(index/5);
				break;
		}
	}
}
