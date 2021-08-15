package gioco.gui;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import gioco.model.Entity;
import gioco.model.Player;
import gioco.utilities.Resources;


public class EnemyView {


	private Image currentImage;
	private int currentState;
	private int index;
	private int type; 
	private int id;
	
	public  EnemyView(int type,int id) {
		this.type = type;
		currentState = Entity.IDLE_DOWN;
		currentImage = Resources.downEnemy1.get(0);
		this.id =id;
	}
	
	public void setState(int state) {
		this.currentState = state;			
	}
	
	
	
	public int getId() {
		return id;
	}


	public Image getCurrentImage() {
		return currentImage;
	}


	public int getState() {
		return currentState;
	}

	public void update(int state) {
		if(type == 1) {
		switch (state) {
			case Entity.IDLE_DOWN :
				index = 0;
				currentImage = Resources.downEnemy1.get(index);
				currentState = state;
				break;
			case Entity.WALKING_DOWN :
				if(state == currentState)
					index =++index % ( Resources.downEnemy1.size()*5);
				else 
					index = 0;
				currentState = state;
				currentImage =  Resources.downEnemy1.get(index/5);
				break;
			case Entity.WALKING_UP :
				if(state == currentState)
					index =++index % ( Resources.upEnemy1.size()*5);
				else 
					index = 0;
				currentState = state;
				currentImage =  Resources.upEnemy1.get(index/5);
				break;
			case Entity.WALKING_RIGHT :
				if(state == currentState) 
					index = ++index % (Resources.rightEnemy1.size()*5);
				
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.rightEnemy1.get(index/5); 
				break;
			case Entity.WALKING_LEFT :
				if(state == currentState)
					index =++index % ( Resources.leftEnemy1.size()*5);
				else 
					index = 0;
				currentState = state;
				currentImage =  Resources.leftEnemy1.get(index/5);
				break;
			default : 
				index = 0;
				currentImage = Resources.downEnemy1.get(index);
				currentState = state;
				break;
			case Entity.DYING_EXPLOSION :  
				if(state == currentState) {
					if(index!=(Resources.dyingExplosionEnemy1.size()-1)*7)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.dyingExplosionEnemy1.get(index/7);
				break;
			
		}
		
		} else if (type == 2) {
			switch (state) {
			case Entity.IDLE_DOWN :
				index = 1;
				currentImage = Resources.downEnemy2.get(index);
				currentState = state;
				break;
			case Entity.WALKING_DOWN :
				if(state == currentState)
					index =++index % ( Resources.downEnemy2.size()*7);
				else 
				index = 0;
				currentState = state;
				currentImage =  Resources.downEnemy2.get(index/7);
				break;
			case Entity.WALKING_UP :
				if(state == currentState)
					index =++index % ( Resources.upEnemy2.size()*7);
				else 
					index = 0;
				currentState = state;
				currentImage =  Resources.upEnemy2.get(index/7); 
				break;
			case Entity.WALKING_RIGHT :
				if(state == currentState) 
					index = ++index % (Resources.rightEnemy2.size()*7);
				
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.rightEnemy2.get(index/7); 
				break;
			case Entity.WALKING_LEFT :
				if(state == currentState)
					index =++index % ( Resources.leftEnemy2.size()*7);
				else 
					index = 0;
				currentState = state;
				currentImage =  Resources.leftEnemy2.get(index/7);
				break;
			case Entity.DYING_EXPLOSION : 
				if(state == currentState) {
					if(index!=(Resources.dyingExplosionEnemy2.size()-1)*7)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.dyingExplosionEnemy2.get(index/7);
				break;
			
		}
		} else if(type == 3) {
			switch (state) {
			case Entity.IDLE_DOWN :
				index = 1;
				currentImage = Resources.downEnemy3.get(index);
				currentState = state;
				break;
			case Entity.WALKING_DOWN :
				if(state == currentState)
					index =++index % ( Resources.downEnemy3.size()*5);
				else 
					index = 0;
				currentState = state;
				currentImage =  Resources.downEnemy3.get(index/5);
				break;
			case Entity.WALKING_UP :
				if(state == currentState)
					index =++index % ( Resources.upEnemy3.size()*5);
				else 
					index = 0;
				currentState = state;
				currentImage =  Resources.upEnemy3.get(index/5); 
				break;
			case Entity.WALKING_RIGHT :
				if(state == currentState) 
					index = ++index % (Resources.rightEnemy3.size()*5);
				
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.rightEnemy3.get(index/5); 
				break;
			case Entity.WALKING_LEFT :
				if(state == currentState)
					index =++index % ( Resources.leftEnemy3.size()*5);
				else 
					index = 0;
				currentState = state;
				currentImage =  Resources.leftEnemy3.get(index/5);
				break;
			case Entity.DYING_EXPLOSION : 
				if(state == currentState) {
					if(index!=(Resources.dyingExplosionEnemy3.size()-1)*5)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.dyingExplosionEnemy3.get(index/5);
				break;
			
		}
		}
	}

}
