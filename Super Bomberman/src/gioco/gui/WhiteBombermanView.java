package gioco.gui;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import gioco.Settings;
import gioco.model.Player;

public class WhiteBombermanView {
	

	private Vector<Image> left;
	private Vector<Image> right;
	private Vector<Image> up;
	private Vector<Image> down;
	private Vector<Image> victory;
	private Vector<Image> dyingExplotion;
	private Vector<Image> dyingEnemy;

	Image currentImage;
	int currentState;
	int index;
	
	public WhiteBombermanView() {
		left = new Vector<Image>();
		right = new Vector<Image>();
		up = new Vector<Image>();
		down = new Vector<Image>();
		victory = new Vector<Image>();
		dyingExplotion = new Vector<Image>();
		dyingEnemy = new Vector<Image>();
		currentState = Player.IDLE_DOWN;
		try {

			for (int i = 0; i < 3; ++i) {
				Image img = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/Walking/left/left_" + i + ".png"));
				left.add(img);
			}
			for (int i = 0; i < 3; ++i) {
				Image img = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/Walking/right/right_" + i + ".png"));
				right.add(img);
			}
			for (int i = 0; i < 3; ++i) {
				Image img = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/Walking/up/up_" + i + ".png"));
				up.add(img);
			}
			for (int i = 0; i < 3; ++i) {
				Image img = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/Walking/down/down_" + i + ".png"));
				down.add(img);

						
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
					index =++index % down.size();
				else 
					index = 0;
				currentState = state;
				currentImage = down.get(index);
				break;
			case Player.WALKING_UP :
				if(state == currentState) 
					index = ++index % up.size();						
				else 
					index = 0;
				currentState = state;
				currentImage = up.get(index); 
				break;
			case Player.WALKING_RIGHT :
				if(state == currentState) 
					index = ++index % right.size();
				
				else 
					index = 0;
				currentState = state;
				currentImage = right.get(index); 
				break;
			case Player.WALKING_LEFT :
				if(state == currentState) 
					index = ++index % left.size();
				else 
					index = 0;
				currentState = state;
				currentImage = left.get(index); 
				break;
		}
	}
}
