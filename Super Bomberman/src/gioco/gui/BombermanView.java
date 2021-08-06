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
	
	public BombermanView() {
		Resources.loadBombermanImages();

		currentState = Player.IDLE_DOWN;
		int player = 1;
		
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
				currentImage = Resources.down.get(index);
				currentState = state;
				break;
			case Player.IDLE_UP :
				index = 0;
				currentImage = Resources.up.get(index);
				currentState = state;
				break;
			case Player.IDLE_RIGHT :
				index = 0;
				currentImage =Resources.right.get(index);
				currentState = state;
				break;
			case Player.IDLE_LEFT :
				index = 0;
				currentImage = Resources.left.get(index);
				currentState = state;
				break;
			case Player.WALKING_DOWN :
				if(state == currentState)
					index =++index % (Resources.down.size()*3);
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.down.get(index/3);
				break;
			case Player.WALKING_UP :
				if(state == currentState) 
					index = ++index % (Resources.up.size()*3);						
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.up.get(index/3); 
				break;
			case Player.WALKING_RIGHT :
				if(state == currentState) 
					index = ++index % (Resources.right.size()*3);
				
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.right.get(index/3); 
				break;
			case Player.WALKING_LEFT :
				if(state == currentState) 
					index = ++index % (Resources.left.size()*3);
				else 
				index = 0;
				currentState = state;
				currentImage = Resources.left.get(index/3);
				break;
			case Player.DYING_ENEMY : 
				if(state == currentState) {
					if(index!=(Resources.dyingByEnemy.size()-1)*3)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.dyingByEnemy.get(index/3);
				break;
			case Player.DYING_EXPLOSION : 
				if(state == currentState) {
					if(index!=(Resources.dyingExplosion.size()-1)*5)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.dyingExplosion.get(index/5);
				break;
			case Player.WINNING: 
				if(state == currentState) {
					if(index!=(Resources.victory.size()-1)*5)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.victory.get(index/5);
				break;
		}
	}
}
