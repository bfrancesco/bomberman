package gioco.gui;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import gioco.model.Player;
import gioco.utilities.Settings;

public class BombermanView {
	

	private Vector<Image> left;
	private Vector<Image> right;
	private Vector<Image> up;
	private Vector<Image> down;
	private Vector<Image> victory;
	private Vector<Image> dyingExplosion;
	private Vector<Image> dyingByEnemy;

	Image currentImage;
	int currentState;
	int index;
	
	public BombermanView() {
		left = new Vector<Image>();
		right = new Vector<Image>();
		up = new Vector<Image>();
		down = new Vector<Image>();
		victory = new Vector<Image>();
		dyingExplosion = new Vector<Image>();
		dyingByEnemy = new Vector<Image>();
		currentState = Player.IDLE_DOWN;
		try {

			for (int i = 0; i < 3; ++i) {
				left.add( ImageIO.read(getClass().getResourceAsStream("/gioco/resources/Walking/left/left_" + i + ".png")));
				right.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/Walking/right/right_" + i + ".png")));
				up.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/Walking/up/up_" + i + ".png")));
				down.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/Walking/down/down_" + i + ".png")));
			}
			for (int i = 0; i < 5; ++i) {
				dyingByEnemy.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/dyingByEnemy/dying_" + i + ".png")));
				dyingExplosion.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/dyingExplosion/dying_" + i + ".png")));
						
		}
		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("WHITE BOMBERMAN RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
		
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
				currentImage = down.get(index);
				currentState = state;
				break;
			case Player.IDLE_UP :
				index = 0;
				currentImage = up.get(index);
				currentState = state;
				break;
			case Player.IDLE_RIGHT :
				index = 0;
				currentImage = right.get(index);
				currentState = state;
				break;
			case Player.IDLE_LEFT :
				index = 0;
				currentImage = left.get(index);
				currentState = state;
				break;
			case Player.WALKING_DOWN :
				if(state == currentState)
					index =++index % (down.size()*3);
				else 
					index = 0;
				currentState = state;
				currentImage = down.get(index/3);
				break;
			case Player.WALKING_UP :
				if(state == currentState) 
					index = ++index % (up.size()*3);						
				else 
					index = 0;
				currentState = state;
				currentImage = up.get(index/3); 
				break;
			case Player.WALKING_RIGHT :
				if(state == currentState) 
					index = ++index % (right.size()*3);
				
				else 
					index = 0;
				currentState = state;
				currentImage = right.get(index/3); 
				break;
			case Player.WALKING_LEFT :
				if(state == currentState) 
					index = ++index % (left.size()*3);
				else 
				index = 0;
				currentState = state;
				currentImage = left.get(index/3);
				break;
			case Player.DYING_ENEMY : 
				if(state == currentState) {
					if(index!=(dyingByEnemy.size()-1)*3)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = dyingByEnemy.get(index/3);
				break;
			case Player.DYING_EXPLOSION : 
				if(state == currentState) {
					if(index!=(dyingExplosion.size()-1)*5)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = dyingExplosion.get(index/5);
			case Player.WINNING: 
		}
	}
}
